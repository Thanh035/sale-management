package com.example.demo.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "tbl_login_history")
public class LoginHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sign_in_at")
    private Instant SignInAt;

    @Column(name = "sign_in_browser")
    private String signInBrowser;

    @Column(name = "sign_in_ip")
    private String signInIp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
