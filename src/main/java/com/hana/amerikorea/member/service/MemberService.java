package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;



public interface MemberService {


    void insertMember(SignUpRequest signUpRequest);
    boolean isEmailDuplicate(String email);
}
