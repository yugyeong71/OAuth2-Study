package com.example.githuboauth2.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler { // 실패 핸들러

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    // 실패 시, 로그인 페이지로 리다이렉트
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException{
        log.info("실패!");
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/login");
    }

}
