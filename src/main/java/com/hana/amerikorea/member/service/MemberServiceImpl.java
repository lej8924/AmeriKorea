package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.dto.SignUpRequest;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    @Transactional(readOnly = true)
    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void updateMember(Member updatedMember) {
        // Retrieve the existing member from the database
        Optional<Member> existingMemberOpt = memberRepository.findById(updatedMember.getId());

        if (existingMemberOpt.isPresent()) {
            Member existingMember = existingMemberOpt.get();

            // Update fields, excluding email
            existingMember.setName(updatedMember.getName());
            existingMember.setGender(updatedMember.getGender());
            existingMember.setPassword(updatedMember.getPassword());
            existingMember.setBirthday(updatedMember.getBirthday());

            // Save the updated member back to the database
            memberRepository.save(existingMember);
        }
    }

    @Override
    public boolean isEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

}
