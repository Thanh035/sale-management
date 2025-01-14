package com.example.demo.controllers.admin;

import com.example.demo.constants.RolesConstants;
import com.example.demo.domain.dto.admin.user.UserCreateDTO;
import com.example.demo.domain.dto.admin.user.UserDTO;
import com.example.demo.domain.dto.admin.user.UserDetailDTO;
import com.example.demo.domain.dto.admin.user.UserUpdateDTO;
import com.example.demo.services.UserService;
import com.example.demo.utils.HeaderUtil;
import com.example.demo.utils.PaginationUtil;
import com.example.demo.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/admin")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections
            .unmodifiableList(Arrays.asList("id", "fullname"));

    private final UserService userService;

    @Value("${application.name}")
    private String applicationName;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public ResponseEntity<List<UserDTO>> getAllUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable,
                                                     @RequestParam(name = "filter", required = false) String filter) {
        log.debug("REST request to get all User for an admin");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<UserDTO> page = userService.getAllManagedUsers(filter, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public ResponseEntity<UserDetailDTO> getUser(@PathVariable Long userId) {
        log.debug("REST request to get User : {}", userId);
        return ResponseUtil.wrapOrNotFound(userService.getUserWithRolesById(userId));
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserCreateDTO request) throws URISyntaxException {
        log.debug("REST request to save User : {}", request);
        UserDTO newUser = userService.createUser(request);
        return ResponseEntity.created(new URI("/api/admin/users/" + newUser.getEmail()))
                .headers(HeaderUtil.createAlert(applicationName, "userManagement.created", newUser.getEmail()))
                .body(newUser);
    }

    @PutMapping("/users/{userId}")
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserUpdateDTO updateRequest,
                                              @PathVariable("userId") Long userId) {
        log.debug("REST request to update Collection : {}", updateRequest);
        Optional<UserDTO> updatedUser = userService.updateUser(updateRequest, userId);
        return ResponseUtil.wrapOrNotFound(updatedUser,
                HeaderUtil.createAlert(applicationName, "userManagement.updated", updateRequest.getFullname()));
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.debug("REST request to delete User: {}", userId);
        userService.deleteUserById(userId);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createAlert(applicationName, "userManagement.deleted", userId.toString())).build();
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    @PostMapping(value = "users/{userId}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public void uploadUserImage(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file) {
        userService.uploadUserProfileImage(userId, file);
    }

    @GetMapping(value = "users/{userId}/profile-image", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public byte[] getUserProfileImage(@PathVariable("userId") Long userId) {
        return userService.getUserProfileImage(userId);
    }

    @DeleteMapping(value = "users/{userId}/profile-image", produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public void deleteUserProfileImage(@PathVariable("userId") Long userId) {
        userService.deleteUserProfileImage(userId);
    }

    @PatchMapping(value = "users/{userId}")
    @PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
    public void activateUser(@Valid @RequestBody Boolean isActivate, @PathVariable("userId") Long userId) {
        userService.activateUser(userId, isActivate);
    }

}
