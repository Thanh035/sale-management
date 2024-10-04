package com.example.myapp.domain.entities;

import com.example.myapp.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
@Table(name = "tbl_user")
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;
    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;
    @Size(max = 100)
    @Column(name = "fullname", length = 100)
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
    @JoinTable(name = "tbl_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")})
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<LoginHistory> loginHistories = new ArrayList<>();

    public User(String login, String fullName, String email, boolean activated, String phoneNumber, String password,
                Set<Role> roles) {
        this.login = login;
        this.fullname = fullName;
        this.email = email;
        this.activated = activated;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = roles;
    }

    public User(String login, String fullName, String email, boolean activated, String phoneNumber, String password) {
        this.login = login;
        this.fullname = fullName;
        this.email = email;
        this.activated = activated;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}
