package com.weData.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weData.entity.WebToken;
import com.weData.repository.WebTokenRepository;
import com.weData.util.OTP;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/oauth")
public class WebTokenController {
    
    @Autowired
    private WebTokenRepository repository;

    @PostMapping("/token")
    public String saveToken(@RequestBody Map<String,String> map) {
        System.out.println(map.get("org_code"));
        System.out.println(map.get("token"));
        repository.save(WebToken.builder()
        .org_code(map.get("org_code").replaceAll("\"", ""))
        .web_token(map.get("token"))
        .build());
        return "redirect:/";
    }

    @GetMapping("/OTP")
    public String otp() throws Exception{
        String code = OTP.create();
        System.out.println("Created OTP : " + code);
        System.out.println("Verify OTP : " + OTP.verify(code));
        System.out.println("Verify OTP : " + OTP.verify(code+1));
        return "";
    }
    
}
