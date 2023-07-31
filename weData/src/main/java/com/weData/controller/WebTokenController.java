package com.weData.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weData.entity.WebToken;
import com.weData.repository.WebTokenRepository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/oauth")
public class WebTokenController {
    
    @Autowired
    private WebTokenRepository repository;

    @PostMapping("/token")
    public String saveToken(@RequestBody Map<String,String> payload) {
        repository.save(WebToken.builder()
        .org_code(payload.get("org_code").replaceAll("\"", ""))
        .web_token(payload.get("token"))
        .build());
        return "redirect:/";
    }
    
}
