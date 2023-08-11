package org.publicData.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.publicData.service.ClientService;
import org.publicData.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;

@RestController
@RequestMapping("/oauth")
public class SupportController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/token")
    public Map<String, String> token(@RequestParam String org_code, @RequestParam String scope) {
        Map<String, String> map = new HashMap<String, String>();
        if(jwtService.isdupToken(org_code)){
            map.put("result", "error");
            map.put("msg", "이미 토큰이 생성 되어있습니다.");
        } else {
            DecodedJWT decodedJWT = jwtService.decodeToken(jwtService.createToken(org_code, scope));
            map.put("result","success");
            map.put("token", decodedJWT.getToken());
            map.put("msg", "토큰 생성 성공!");
        }
        return map;
    }

    @PutMapping("/token")
    public Map<String, String> updateToken(@RequestBody Map<String, String> map){
        Map<String, String> result = new HashMap<String, String>();
        String newToken = jwtService.modifyToken(map.get("org_code"), map.get("scope"));
        result.put("token", newToken);
        result.put("msg", "토큰이 갱신 되었습니다.");
        return result;
    }

    @PostMapping("/client")
    public Map<String, String> client(@RequestBody Map<String, String> map){
        return clientService.createClient(map.get("org_code"));
    }

    @GetMapping("/client")
    public boolean verify(@RequestParam String org_code, @RequestParam String client_id, @RequestParam String client_password){
        return clientService.verifyClient(org_code, client_id, client_password);
    }
    
}
