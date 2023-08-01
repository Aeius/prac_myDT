package com.single_sign_on.controller;

import com.single_sign_on.service.SignOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class SignOnController {

    @Autowired
    SignOnService signOnService;

    @PostMapping("/user/sign")
    public String sign(@RequestBody Map<String,String> data) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        return signOnService.createCI("key",data);
    }
}
