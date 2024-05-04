package org.example.food.service;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Member;
import org.example.food.repository.MemberRepository;
import org.example.food.security.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Override
    @Transactional
    public void updateMemberName(Long memberId, String newName) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        Member updatedMember = member.updateName(newName);
        updateUserDetails(updatedMember);
    }

    @Override
    public boolean existsMemberByName(String memberName) {
        return memberRepository.existsByName(memberName);
    }

    @Override
    @Transactional
    public void updateMemberImage(Long memberId, MultipartFile image) throws IOException {
        String filename = s3Service.upload(image, "images");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        member.updateImageUrl(filename);
        updateUserDetails(member);
    }

    private static void updateUserDetails(Member updatedMember) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        principalDetails.setMember(updatedMember);
    }
}
