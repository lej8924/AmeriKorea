package com.hana.amerikorea.member.service;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailService {

    private final MemberRepository memberRepository;
    private JavaMailSender mailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MailService(MemberRepository memberRepository ,JavaMailSender mailSender) {
        this.memberRepository = memberRepository;
        this.mailSender = mailSender;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public boolean checkEmailandName(String email, String name) {
        // 이메일과 이름이 모두 일치하는 회원이 존재하는지 확인
        return memberRepository.existsByEmailAndName(email, name);
    }

    public String getTmpPassword(){
        Random random = new Random();
        int tempPassword = random.nextInt(100000); // 0 이상 100000 미만의 정수 숫자 난수 발생
        return Integer.toString(tempPassword); // 정수 숫자 비번을 문자열로 변경

    } //임시비번 생성

    @Transactional
    public void updatePassword(String tmpPassword, String email) {

        Member member =memberRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        String encodedPassword = bCryptPasswordEncoder.encode(tmpPassword);

        member.setPassword(encodedPassword);
        memberRepository.save(member);
    }// 임시비밀번호 업데이트

    public void sendEmail(String tmpPassword, String email) {
        String title = "임시 비밀번호 발급";
        String message = "귀하의 임시 비밀번호는 다음과 같습니다: " + tmpPassword+"\n 개인정보페이지에서 비밀번호를 재설정하세요.";
        String senderEmail="chrissy030718@gmail.com";
        // 메일 생성
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(title);
        mailMessage.setText(message);
        mailMessage.setFrom(senderEmail);
        mailMessage.setReplyTo(senderEmail);

        try {
            mailSender.send(mailMessage);
            System.out.println("이메일 전송 성공");
        } catch (Exception e) {
            System.err.println("이메일 전송 실패: " + e.getMessage());

        }

    }



}
