package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.member.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public void insertMember(SignUpRequest signUpRequest) {
        Member member = new Member(
                signUpRequest.getName(),
                signUpRequest.getGender(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getBirthday()
        );
        this.memberRepository.save(member);

    }
}
