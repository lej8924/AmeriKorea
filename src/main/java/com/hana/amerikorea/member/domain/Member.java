package com.hana.amerikorea.member.domain;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean gender;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private LocalDate birthday;

    public Member(String name, Boolean gender, String email, String password, LocalDate birthday) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }
}
