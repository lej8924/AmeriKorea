package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;


public interface MemberService {

    void insertMember(SignUpRequest signUpRequest);
   // Member findMemberById(Long id);

    boolean updateMember(Member updatedMember);

    boolean isEmailDuplicate(String email);

    Member findMemberByEmail(String email);

    boolean checkPassword(String email, String password);
}
