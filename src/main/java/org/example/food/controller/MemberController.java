package org.example.food.controller;

import org.example.food.security.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //test
    @ResponseBody
    @GetMapping("/login-test")
    public String testLogin(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getMember().getName()+", "+principalDetails.getName();
    }
}
