package org.example.food.security;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Member;
import org.example.food.domain.Role;
import org.example.food.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId ,oAuth2User.getAttributes());
        Member member = findOrCreateMember(attributes);

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }

    private Member findOrCreateMember(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .orElse(Member.builder()
                        .email(attributes.getEmail())
                        .name(attributes.getName())
//                        .imageUrl(attributes.getPicture()) 구글 이미지 사용 안함
                        .provider(attributes.getProvider())
                        .providerId(attributes.getProviderId())
                        .role(Role.ROLE_USER)
                        .build());

        return memberRepository.save(member);
    }
}
