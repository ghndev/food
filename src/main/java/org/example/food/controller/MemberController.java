package org.example.food.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.food.dto.MemberNameEditRequest;
import org.example.food.dto.NotificationResponse;
import org.example.food.security.PrincipalDetails;
import org.example.food.service.MemberService;
import org.example.food.service.NotificationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final NotificationService notificationService;

    @GetMapping
    public String home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails != null) {
            boolean hasUnreadNotifications = notificationService.hasUnreadNotifications(principalDetails.getMember().getId());
            model.addAttribute("notification", hasUnreadNotifications);
        }
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("name", principalDetails.getName());
        model.addAttribute("image", principalDetails.getMember().getImageUrl());
        model.addAttribute("memberNameEditRequest", new MemberNameEditRequest());
        return "profile";
    }

    @PostMapping("/profile")
    public String editName(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid @ModelAttribute MemberNameEditRequest memberNameEditRequest, BindingResult bindingResult, Model model) {
        String memberName = memberNameEditRequest.getMemberName();
        if (memberService.existsMemberByName(memberName)) {
            bindingResult.rejectValue("memberName", "nameAlreadyExist", "이미 존재하는 닉네임입니다");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("name", principalDetails.getName());
            model.addAttribute("image", principalDetails.getMember().getImageUrl());
            return "profile";
        }

        memberService.updateMemberName(principalDetails.getMember().getId(), memberName);
        return "redirect:/profile";
    }

    @ResponseBody
    @PostMapping("/profile/upload")
    public String updateProfileImage(@AuthenticationPrincipal PrincipalDetails principalDetails,@RequestParam("image") MultipartFile image) throws IOException {
        memberService.updateMemberImage(principalDetails.getMember().getId(), image);
        return "redirect:/profile";
    }

    //test
    @ResponseBody
    @GetMapping("/login-test")
    public String testLogin(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getMember().getName()+", "+principalDetails.getName();
    }

    @GetMapping("/terms")
    public String terms() {
        return "terms";
    }

}
