package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Member;
import org.example.food.repository.MemberRepository;
import org.example.food.security.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateMemberName(Long memberId, String newName) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        Member updatedMember = member.updateName(newName);
        updateUserDetails(updatedMember);
    }

    private static void updateUserDetails(Member updatedMember) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        principalDetails.setMember(updatedMember);
    }

    public boolean existsMemberByName(String memberName) {
        return memberRepository.existsByName(memberName);
    }
}
