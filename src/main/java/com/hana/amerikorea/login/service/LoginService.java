package com.hana.amerikorea.login.service;

import com.hana.amerikorea.login.domain.Login;
import com.hana.amerikorea.login.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Login authenticate(String email, String password) {
        return loginRepository.findByEmail(email)
                .filter(login -> login.getPassword().equals(password))
                .orElse(null);
    }
}
