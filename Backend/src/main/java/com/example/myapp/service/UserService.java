package com.example.myapp.service;

import com.example.myapp.constant.RolesConstants;
import com.example.myapp.dto.UserRegistrationRequest;
import com.example.myapp.dto.admin.account.AccountDTO;
import com.example.myapp.dto.admin.account.AccountUpdateDTO;
import com.example.myapp.dto.admin.account.SecurityInfoDTO;
import com.example.myapp.dto.admin.user.UserCreateDTO;
import com.example.myapp.dto.admin.user.UserDTO;
import com.example.myapp.dto.admin.user.UserDetailDTO;
import com.example.myapp.dto.admin.user.UserUpdateDTO;
import com.example.myapp.exception.DuplicateResourceException;
import com.example.myapp.exception.RequestValidationException;
import com.example.myapp.exception.ResourceNotFoundException;
import com.example.myapp.mapper.SecurityInfoMapper;
import com.example.myapp.mapper.UserDTOMapper;
import com.example.myapp.model.LoginHistory;
import com.example.myapp.model.Role;
import com.example.myapp.model.User;
import com.example.myapp.repository.LoginHistoryRepository;
import com.example.myapp.repository.RoleRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.util.RandomUtil;
import com.example.myapp.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserDTOMapper userDTOMapper;

    private final SecurityInfoMapper securityInfoMapper;
    private final MailService mailService;
    private final StorageService storageService;
    private final CacheManager cacheManager;
    private final HttpServletRequest request;

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(String filter, Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
            return userRepository.findAllWithFilter(filter, pageable).map(userDTOMapper);
        } else {
            return userRepository.findAll(pageable).map(userDTOMapper);
        }
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(userDTOMapper);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserWithRolesByLogin(String login) {
        return userRepository.findOneWithRolesByLogin(login).map(userDTOMapper);
    }

    @Transactional(readOnly = true)
    public Optional<UserDetailDTO> getUserWithRolesById(Long userId) {
        checkIfUserExistsOrThrow(userId);
        return userRepository.findOneWithRolesById(userId).map(userDTOMapper::toUserDetailDTO);
    }

    @Transactional(readOnly = true)
    public AccountDTO getUserFromAuthentication() {
        return SecurityUtil.getCurrentUserLogin().flatMap(userRepository::findOneWithRolesByLogin)
                .map(userDTOMapper::toAccountDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User could not be found"));
    }

    @Transactional(readOnly = true)
    public List<String> getRoles() {
        return roleRepository.findAll().stream().map(Role::getCode).collect(Collectors.toList());
    }

    public UserDTO createUser(UserCreateDTO newUserRequest) {
        // check if email exists
        checkIfEmailAlreadyOrThrow(newUserRequest.getEmail());

        // add
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        User user = new User();
        user.setPassword(encryptedPassword);
        user.setActivated(true);
        user.setLogin(newUserRequest.getEmail());
        user.setEmail(newUserRequest.getEmail());
        user.setFullname(newUserRequest.getFullname());
//		user.setActivationKey(RandomUtil.generateActivationKey());
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        userRepository.save(user);

        mailService.sendCreationEmail(user);
        log.debug("Created Information for User: {}", user);
        return userDTOMapper.apply(user);
    }

    public Optional<UserDTO> updateUser(UserUpdateDTO updateRequest, Long userId) {
        checkIfUserExistsOrThrow(userId);
        return Optional.of(userRepository.findById(userId)).filter(Optional::isPresent).map(Optional::get).map(user -> {
            BeanUtils.copyProperties(updateRequest, user);

            if (updateRequest.getRoles() != null) {
                Set<Role> managedRoles = user.getRoles();
                managedRoles.clear();
                updateRequest.getRoles().stream().map(roleRepository::findOneByCode).filter(Optional::isPresent)
                        .map(Optional::get).forEach(managedRoles::add);
            }

            this.clearUserCaches(user);
            log.debug("Changed Information for User: {}", user);
            return user;
        }).map(userDTOMapper);
    }

    public void deleteUserById(Long userId) {
        checkIfUserExistsOrThrow(userId);
        userRepository.findById(userId).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void updateUser(AccountUpdateDTO request) {
        SecurityUtil.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
            user.setFullname(request.getFullname());
            user.setPhoneNumber(request.getPhoneNumber());
            this.clearUserCaches(user);
            log.debug("Changed Information for User: {}", user);
        });
    }

    public User registerUser(UserRegistrationRequest userRegistrationRequest) {
        if (isPasswordLengthInvalid(userRegistrationRequest.getPassword())) {
            throw new RequestValidationException("Incorrect password");
        }

        userRepository.findOneByLogin(userRegistrationRequest.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new DuplicateResourceException("Login name already used!");
            }
        });

        userRepository.findOneByEmailIgnoreCase(userRegistrationRequest.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new DuplicateResourceException("Email is already in use!");
            }
        });

        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(userRegistrationRequest.getPassword());
        newUser.setLogin(userRegistrationRequest.getLogin().toLowerCase());

        newUser.setPassword(encryptedPassword);
        newUser.setEmail(userRegistrationRequest.getEmail().toLowerCase());
        newUser.setFullname(userRegistrationRequest.getFullname());

        newUser.setActivated(false);

        newUser.setActivationKey(RandomUtil.generateActivationKey());

        Set<Role> roles = new HashSet<>();
        roleRepository.findOneByCode(RolesConstants.USER).ifPresent(roles::add);
        newUser.setRoles(roles);
        userRepository.save(newUser);
        mailService.sendActivationEmail(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtil
                .getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
//                        throw new InvalidPasswordException();
                        throw new RequestValidationException("Incorect password");
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    this.clearUserCaches(user);
                    log.debug("Changed password for User: {}", user);
                });
    }

    @Transactional
    public void updateLoginInfo() {
        SecurityUtil.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
            LoginHistory loginHistory = new LoginHistory();
            loginHistory.setSignInBrowser(request.getHeader("User-Agent"));
            loginHistory.setSignInIp(request.getRemoteAddr());
            loginHistory.setSignInAt(Instant.now());
            loginHistory.setUser(user);
            loginHistoryRepository.save(loginHistory);
            log.debug("login info for User: {}", user);
        });
    }

    public void requestPasswordReset(String email) {
        userRepository
                .findOneByEmailIgnoreCase(email)
                .filter(User::isActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    return user;
                })
                .ifPresentOrElse(
                        user -> mailService.sendPasswordResetMail(user),
                        () -> log.warn("Password reset requested for non-existing email: {}", email)
                );


    }

    public void completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        if (isPasswordLengthInvalid(newPassword)) {
            throw new RequestValidationException("Incorrect password");
        }
        userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS))).map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    user.setActivated(true);
                    return user;
                }).orElseThrow(() -> new ResourceNotFoundException("No user was found for this reset key"));
    }

    public UserDTO activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key).map(user -> {
            // activate given user for the registration key.
            user.setActivated(true);
            user.setActivationKey(null);
            log.debug("Activated user: {}", user);
            return userDTOMapper.apply(user);
        }).orElseThrow(() -> new ResourceNotFoundException("No user was found for this activation key"));
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
                Instant.now().minus(3, ChronoUnit.DAYS)).forEach(user -> {
            log.debug("Deleting not activated user {}", user.getEmail());
            userRepository.delete(user);
        });
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (StringUtils.isEmpty(password) || password.length() < UserRegistrationRequest.PASSWORD_MIN_LENGTH
                || password.length() > UserRegistrationRequest.PASSWORD_MAX_LENGTH);
    }

    private void checkIfLoginAlreadyOrThrow(String login) {
        if (userRepository.findOneByLogin(login.toLowerCase()).isPresent()) {
            throw new DuplicateResourceException("Login name already used!");
        }
    }

    private void checkIfEmailAlreadyOrThrow(String email) {
        if (userRepository.findOneByEmailIgnoreCase(email).isPresent()) {
            throw new DuplicateResourceException("Email này đã được sử dụng");
        }
    }

    private void checkIfUserExistsOrThrow(Long userId) {
        if (!userRepository.existsUserById(userId)) {
            throw new ResourceNotFoundException(String.format("user with id [%s] not found", userId));
        }
    }

    public void uploadUserProfileImage(Long userId, MultipartFile file) {
        checkIfUserExistsOrThrow(userId);
        String profileImageId = UUID.randomUUID().toString();
        try {
            storageService.putObject("point-of-sale-management.appspot.com",
                    String.format("profile-images/%s/%s", userId, profileImageId), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("failed to upload profile image", e);
        }
        userRepository.updateProfileImageId(profileImageId, userId);
    }

    public void uploadUserProfileImage(MultipartFile file) {
        SecurityUtil.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
            String profileImageId = UUID.randomUUID().toString();
            try {
                byte[] fileBytes = file.getBytes();
                storageService.putObject("point-of-sale-management.appspot.com",
                        String.format("profile-images/%s/%s", user.getId(), profileImageId), fileBytes);
                userRepository.updateProfileImageId(profileImageId, user.getId());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile image", e);
            }
        });
    }

    public byte[] getUserProfileImage(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("user with id [%s] not found", userId)));

        if (StringUtils.isBlank(user.getProfileImageId())) {
            throw new ResourceNotFoundException(
                    String.format("user with id [%s] profile picture image not found", userId));
        }

        byte[] profileImage = storageService.getObject("point-of-sale-management.appspot.com",
                String.format("profile-images/%s/%s", userId, user.getProfileImageId()));
        return profileImage;
    }

    public byte[] getUserProfileImage() {
        return SecurityUtil.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).map(user -> {
            if (StringUtils.isBlank(user.getProfileImageId())) {
                throw new ResourceNotFoundException(
                        String.format("User with id [%s] does not have a profile picture.", user.getId()));
            }

            byte[] profileImage = storageService.getObject("point-of-sale-management.appspot.com",
                    String.format("p" + "rofile-images/%s/%s", user.getId(), user.getProfileImageId()));

            return profileImage;
        }).orElseThrow(() -> new ResourceNotFoundException("Current user not found."));
    }

    public void deleteUserProfileImage(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("user with id [%s] not found", userId)));

        if (StringUtils.isBlank(user.getProfileImageId())) {
            throw new ResourceNotFoundException(String.format("user with id [%s] user image not found", userId));
        }

        storageService.deleteObject("point-of-sale-management.appspot.com",
                String.format("user-images/%s/%s", userId, user.getProfileImageId()));
        userRepository.updateProfileImageId(null, userId);
    }

    public void activateUser(Long userId, Boolean isActivate) {
        checkIfUserExistsOrThrow(userId);

        userRepository.findById(userId).ifPresent(user -> {
            user.setActivated(isActivate);
            log.debug("Changed activate for User: {}", user.isActivated());
        });
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getId() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_ID_CACHE)).evict(user.getId());
        }
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }

    @Transactional(readOnly = true)
    public SecurityInfoDTO getSecurityInfoFromAuthentication() {
        return SecurityUtil.getCurrentUserLogin().flatMap(userRepository::findOneWithRolesByLogin)
                .map(securityInfoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("User could not be found"));
    }
}
