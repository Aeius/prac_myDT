package org.publicData.controller;

import java.util.Map;

import org.publicData.service.ClientService;
import org.publicData.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String token(@RequestParam String org_code, @RequestParam String scope) {
        DecodedJWT decodedJWT = jwtService.decodeToken(jwtService.createToken(org_code, scope));
        return decodedJWT.getToken();
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
