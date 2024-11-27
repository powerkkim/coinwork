package com.coinwork.base.acommon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/kakao/login")
    public String kakaoLogin() {

            return "redirect:/";
    }
}
