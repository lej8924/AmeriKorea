package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hana.amerikorea.member.dto.SignUpRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findMemberByEmailAndName(String email, String name) {
        return memberRepository.findByEmailAndName(email, name).orElse(null);
    } //임시비번발급을 위해 이메일과 비번받아옴

    @Transactional
    public void updatePassword(Member member) {
        memberRepository.save(member);
    } // 임시비밀번호 업데이트


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));


        // UserDetails로 변환 없이 바로 반환
        return member;
    }

}