package com.hana.amerikorea.member.repository;

import com.hana.amerikorea.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndName(String email, String name);
    boolean existsByEmail(String memId);
}
