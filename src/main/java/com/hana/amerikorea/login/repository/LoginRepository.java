package com.hana.amerikorea.login.repository;

import com.hana.amerikorea.login.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, String> {
    Optional<Login> findByEmail(String email);
}
