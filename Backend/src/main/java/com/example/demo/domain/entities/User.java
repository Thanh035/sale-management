package com.example.demo.domain.entities;

import com.example.demo.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "user_id")
    private Long id;
    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false, name = "user_name")
    private String login;
    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;
    @Size(max = 100)
    @Column(name = "full_name", length = 100)
    private String fullname;
    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true, nullable = false)
    private String email;
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 9, max = 11)
    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Size(max = 256)
    @Column(name = "profile_image_id", length = 256)
    private String profileImageId;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = {
            @JoinColumn(name = "group_id", referencedColumnName = "group_id")})
    @BatchSize(size = 20)
    private Set<Group> groups = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<LoginHistory> loginHistories = new ArrayList<>();

    public User(String login, String fullName, String email, boolean activated, String phoneNumber, String password,
                Set<Group> groups) {
        this.login = login;
        this.fullname = fullName;
        this.email = email;
        this.activated = activated;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.groups = groups;
    }

    public User(String login, String fullName, String email, boolean activated, String phoneNumber, String password) {
        this.login = login;
        this.fullname = fullName;
        this.email = email;
        this.activated = activated;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String fullName, String email, String userName, String password, Set<Group> groups) {
        this.fullname = fullName;
        this.email = email;
        this.login = userName;
        this.password = password;
        this.groups = groups;
    }

}
