package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void insertMember(SignUpRequest signUpRequest) {
        // 비밀번호를 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassword());

        Member member = new Member(
                signUpRequest.getName(),
                signUpRequest.getGender(),
                signUpRequest.getEmail(),
                encodedPassword,  // 암호화된 비밀번호를 사용
                signUpRequest.getBirthday()
        );
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public boolean updateMember(Member updatedMember) {
        Optional<Member> existingMemberOpt = memberRepository.findByEmail(updatedMember.getEmail());

        if (existingMemberOpt.isPresent()) {
            Member existingMember = existingMemberOpt.get();

            // 다른 필드들 수정
            existingMember.setName(updatedMember.getName());
            existingMember.setGender(updatedMember.getGender());
            existingMember.setBirthday(updatedMember.getBirthday());

            // 비밀번호도 수정 (null이 아닌 경우에만)
            if (updatedMember.getPassword() != null && !updatedMember.getPassword().isEmpty()) {
                existingMember.setPassword(bCryptPasswordEncoder.encode(updatedMember.getPassword()));
            }

            // 변경된 회원 정보 저장
            memberRepository.save(existingMember);

            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean isEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Member not found."));
    }

    @Override
    public boolean checkPassword(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Member not found."));


        return bCryptPasswordEncoder.matches(password, member.getPassword());
    }

}
