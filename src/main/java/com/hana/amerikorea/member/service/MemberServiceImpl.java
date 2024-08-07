package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void insertMember(SignUpRequest signUpRequest) {

        // 비밀번호 일치 여부 확인
        if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordCheck())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // 이메일 중복 확인
        if (isEmailDuplicate(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Member member = new Member(
                signUpRequest.getName(),
                signUpRequest.getGender(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getBirthday()
        );
        memberRepository.save(member);
    }

    @Override
    public boolean isEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }
}
