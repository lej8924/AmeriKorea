package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;

import java.util.Optional;


public interface MemberService {

    void insertMember(SignUpRequest signUpRequest);
    Member findMemberById(Long id);

    void updateMember(Member member);

    boolean isEmailDuplicate(String email);

    //  boolean checkIdExists(String memId);
}
