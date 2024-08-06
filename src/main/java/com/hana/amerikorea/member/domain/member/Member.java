package com.hana.amerikorea.member.domain.member;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.NotFound;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
