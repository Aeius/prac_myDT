package com.single_sign_on.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.single_sign_on.service.SignOnService;

@RestController
@RequestMapping("/user")
public class SignOnController {

    @Autowired
    SignOnService signOnService;

    @PostMapping("/sign")
    public String sign(@RequestBody Map<String,String> data) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        return signOnService.createCI("key",data);
    }

    @PostMapping("/verify")
    public boolean verify(@RequestBody Map<String,String> data){
        return signOnService.verify(data);
    }
}
