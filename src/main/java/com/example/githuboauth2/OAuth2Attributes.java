package com.example.githuboauth2;

import com.example.githuboauth2.exception.OAuth2RegistrationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Slf4j
@Getter
public class OAuth2Attributes { // 소셜 로그인 정보가 담기는 class

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String oauthId;
    private final String nickname;
    private final String email;
    private final String picture;
    private final Provider provider;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes, String nameAttributeKey, String oauthId, String nickname, String email, String picture, Provider provider){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.oauthId = oauthId;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
    }

    // 여러 개의 소셜 로그인일 경우, 이 메소드를 통해 각각에 맞는 Provider 메소드를 동작 시킴
    @SneakyThrows
    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        log.info("userNameAttributeName = {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userNameAttributeName)); // 어떤 데이터든 Spring 서버 로그에서 JSON 형태로 볼 수 있다.
        log.info("attributes = {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(attributes));

        String registrationIdToLower = registrationId.toLowerCase();

        if(registrationIdToLower == "github"){
            return ofGithub(userNameAttributeName, attributes);
        }
        else {
            throw new OAuth2RegistrationException("해당 소셜 로그인은 현재 지원하지 않습니다.");
        }

        /*switch (registrationIdToLower){
            case "github": return ofGithub(userNameAttributeName, attributes);
            default: throw new OAuth2RegistrationException("해당 소셜 로그인은 현재 지원하지 않습니다.");
        }*/
    }

    // Github 사용자 정보 저장 메소드
    private static OAuth2Attributes ofGithub(String userNameAttributeName, Map<String, Object> attributes){
        String nickname = ObjectUtils.isEmpty(attributes.get("name")) ? "login" : "name"; // 이름이 없다면 로그인, 있다면 이름 데이터 넣어줌

        return OAuth2Attributes.builder()
                .oauthId(attributes.get(userNameAttributeName).toString())
                .nickname((String) attributes.get(nickname))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .provider(Provider.GITHUB)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
}
