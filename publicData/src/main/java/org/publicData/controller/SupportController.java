package org.publicData.controller;

import org.publicData.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@RestController
@RequestMapping("/oauth")
public class SupportController {

    @Autowired
    private JWTService jwtService;

    @GetMapping("/token")
    public String token(@RequestParam String scope) {
        DecodedJWT decodedJWT = jwtService.decodeToken(jwtService.createToken(scope));
        Claim claim = decodedJWT.getClaim("org_code");
        StringBuffer buffer = new StringBuffer();
        buffer.append(claim.toString());
        buffer.append("|" + decodedJWT.getToken());
        return buffer.toString();
    }
    
}
