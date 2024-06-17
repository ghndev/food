package org.example.food.initializer;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Member;
import org.example.food.domain.Role;
import org.example.food.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!memberRepository.existsByEmail("admin")) {
            Member member = Member.builder()
                    .email("admin")
                    .name("admin")
                    .role(Role.ROLE_ADMIN)
                    .password(passwordEncoder.encode("1234"))
                    .build();

            memberRepository.save(member);
        }

        if (!memberRepository.existsByEmail("user")) {
            Member member = Member.builder()
                    .email("user")
                    .name("user")
                    .role(Role.ROLE_USER)
                    .password(passwordEncoder.encode("1234"))
                    .build();

            memberRepository.save(member);
        }
    }
}
