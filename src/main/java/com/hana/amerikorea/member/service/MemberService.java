package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.member.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void saveMember(SignUpRequest signUpRequest) {
        Member member = new Member();
        member.setName(signUpRequest.getName());
        member.setGender(signUpRequest.getGender());
        member.setEmail(signUpRequest.getEmail());
        member.setPassword(signUpRequest.getPassword());
        member.setBirthday(signUpRequest.getBirthday());
        memberRepository.save(member);
    }
}
