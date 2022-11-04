package com.example.githuboauth2.handler;

import com.example.githuboauth2.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler { // 성공 핸들러

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, SecurityException{
        log.info("성공");
        String[] path = httpServletRequest.getRequestURI().split("/");
        Provider provider = Provider.valueOf(path[path.length - 1].toUpperCase());
        String oauthId = authentication.getName();

        // 성공 시, /social 경로로 parameter에 provider & oauthId 를 보낸다.
        String uri = UriComponentsBuilder.fromUriString("http://localhost:80/social")
                .queryParam("provider", provider)
                .queryParam("oauthId", oauthId)
                .build().toUriString();
        httpServletResponse.sendRedirect(uri);
    }
}
