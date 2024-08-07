package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void insertMember(SignUpRequest signUpRequest) {
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
    public boolean emailCheck(SignUpRequest signUpRequest) {
        boolean emailExists = memberRepository.existsByEmail(signUpRequest.getEmail());
        if (emailExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists").hasBody();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Email is available").hasBody();
        }
    }
}
