package com.hana.amerikorea.member.domain.init;

import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MemberInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberInitializer(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 더미 데이터 삽입
        Member member1 = new Member("John Doe", true, "john.doe@example.com", "password123", LocalDate.of(1985, 5, 15));
        Member member2 = new Member("Jane Smith", false, "jane.smith@example.com", "password123", LocalDate.of(1990, 7, 21));
        Member member3 = new Member("Michael Brown", true, "michael.brown@example.com", "password123", LocalDate.of(1988, 2, 10));
        Member member4 = new Member("Emily Davis", false, "emily.davis@example.com", "password123", LocalDate.of(1992, 12, 5));
        Member member5 = new Member("David Wilson", true, "david.wilson@example.com", "password123", LocalDate.of(1987, 4, 25));
        Member member6 = new Member("test", true, "test@test.com", "test", LocalDate.of(2001, 12, 23));


        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        memberRepository.save(member6);
    }
}
