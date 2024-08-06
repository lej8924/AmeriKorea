package com.hana.amerikorea.login.domain;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name="login_tbl")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;
}
