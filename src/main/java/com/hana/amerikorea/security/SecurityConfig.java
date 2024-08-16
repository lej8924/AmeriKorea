package com.hana.amerikorea.security;

import com.hana.amerikorea.member.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final LoginService loginService;

    @Autowired
    public SecurityConfig(LoginService loginService) {
        this.loginService = loginService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/member/**", "/api/asset/**", "/api/portfolio/**","/getAssetData").permitAll()
                        .requestMatchers("/js/**", "/css/**", "/img/**","/fonts/**").permitAll()
                        //.requestMatchers("/member/profile").authenticated()  // /member/profile에 대해 인증 요구
                        .anyRequest().authenticated()  // 그 외의 모든 요청에 대해 인증 요구
                )
                .formLogin(form -> form
                        .loginPage("/member/sign-in")
                        .usernameParameter("userEmail")    // 커스텀 파라미터로 사용자 이메일 사용
                        .passwordParameter("userPassword") // 커스텀 파라미터로 사용자 비밀번호 사용
                        .defaultSuccessUrl("/api/portfolio", true) // 로그인 성공 후 리다이렉트 URL
                        .failureUrl("/member/sign-in?error=true") // 로그인 실패 후 리다이렉트 URL
                        .successHandler(authenticationSuccessHandler()) // 로그인 성공 시 커스텀 핸들러
                        .failureHandler(authenticationFailureHandler()) // 로그인 실패 시 커스텀 핸들러
                )
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/member/sign-in")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .csrf(AbstractHttpConfigurer::disable);


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(loginService)
                .passwordEncoder(bCryptPasswordEncoder());

        return authManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // 로그인 성공 시 처리 로직
            response.sendRedirect("/api/portfolio");
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            // 로그인 실패 시 처리 로직
            response.sendRedirect("/member/sign-in?error=true");
        };
    }
}
