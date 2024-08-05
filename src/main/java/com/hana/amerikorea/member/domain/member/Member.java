package com.hana.amerikorea.member.domain.member;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Boolean gender;
    private String email;
    private String password;
    private int birthday;

    public Member(String name, Boolean gender, String email, String password, int birthday) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }
}
