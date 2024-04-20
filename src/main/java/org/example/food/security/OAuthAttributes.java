package org.example.food.security;

import lombok.Builder;
import lombok.Getter;
import org.example.food.domain.Member;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String provider;
    private String providerId;
    private String email;
    private String name;
    private String picture;

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        // 다른 소셜 로그인 추가시 if문 추가
//        if (registrationId.equals("facebook")) {
//        }
        return ofGoogle(attributes);
    }

    private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .provider("google")
                .providerId(String.valueOf(attributes.get("sub")))
                .email(String.valueOf(attributes.get("email")))
                .name(String.valueOf(attributes.get("name")))
                .picture(String.valueOf(attributes.get("picture")))
                .attributes(attributes)
                .build();
    }

}
