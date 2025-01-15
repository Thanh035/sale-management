package com.example.demo.auth;


import com.example.demo.constants.RolesConstants;
import com.example.demo.domain.entities.Group;
import com.example.demo.domain.entities.User;
import com.example.demo.exception.EmailAlreadyUsedException;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.utils.SecurityUtils.AUTHORITIES_KEY;
import static com.example.demo.utils.SecurityUtils.JWT_ALGORITHM;

@Service
public class AuthenticationService {
    private final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final JwtEncoder jwtEncoder;

    @Value("${myapp.security.authentication.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${myapp.security.authentication.jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final GroupRepository groupRepository;
    public AuthenticationService(
            JwtEncoder jwtEncoder,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            GroupRepository groupRepository
    ) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.groupRepository = groupRepository;
    }

    public User registerUser(UserRegistrationRequest request) {
        userRepository
                .findOneByEmailIgnoreCase(request.email())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonDeletedUser(existingUser);
                    if (!removed) {
                        throw new EmailAlreadyUsedException();
                    }
                });
        String encryptedPassword = passwordEncoder.encode(request.password());
        Set<Group> groups = new HashSet<>();
        groupRepository.findByGroupName(RolesConstants.USER).ifPresent(groups::add);
        String userName = request.email().toLowerCase();

        // add
        User newUser = new User(
                request.fullName(),
                request.email().toLowerCase(),
                userName,
                encryptedPassword,
                groups);

        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public String login(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.username(),
                authenticationRequest.password()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return this.createToken(authentication, authenticationRequest.rememberMe());
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
        }

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    private boolean removeNonDeletedUser(User existingUser) {
        if (!existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }
}


