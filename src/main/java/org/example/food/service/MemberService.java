package org.example.food.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    void updateMemberName(Long memberId, String newName);

    boolean existsMemberByName(String memberName);

    void updateMemberImage(Long memberId, MultipartFile image) throws IOException;
}
