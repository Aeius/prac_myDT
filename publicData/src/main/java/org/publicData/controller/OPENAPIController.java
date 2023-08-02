package org.publicData.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.publicData.service.JWTService;
import org.publicData.service.OPENAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;

@RestController
@RequestMapping("/openapi")
class OPENAPIController {
    
    @Autowired
    private JWTService jwtService;

    @Autowired
    private OPENAPIService openapiService;

    @PostMapping("/first")
    public String openApi_1(HttpServletRequest request, @RequestBody Map<String, String> map) throws IOException{
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        
        if(!jwtService.validateToken(map.get("org_code"), token)) return "{\"error\" : \"유효하지 않은 토큰입니다.\"}";

        DecodedJWT decodedJWT = jwtService.decodeToken(token);
        String scope = decodedJWT.getClaim("scope").toString().replaceAll("\"", "");
        if(!scope.equals("first")) return "{\"error\" : \"유효하지 않은 scope 범위입니다.\"}";

        String xmlData = openapiService.apicall("1");
        String[] params = xmlData.split("\\|");
        String xml = params[0];
        String hmac = params[1];
        if(!hmac.equals(openapiService.HmacEncode(xml))) return "{\"error\" : \"변조된 값이 감지되었습니다.\"}";
        return openapiService.xmlToJson(xml);
    }

    @PostMapping("/second")
    public String open_api2(HttpServletRequest request, @RequestBody Map<String, String> map) throws IOException{
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        
        if(!jwtService.validateToken(map.get("org_code"), token)) return "{\"error\" : \"유효하지 않은 토큰입니다.\"}";

        DecodedJWT decodedJWT = jwtService.decodeToken(token);
        String scope = decodedJWT.getClaim("scope").toString().replaceAll("\"", "");
        if(!scope.equals("second")) return "{\"error\" : \"유효하지 않은 scope 범위입니다.\"}";

        String xmlData = openapiService.apicall("2");
        String[] params = xmlData.split("\\|");
        String xml = params[0];
        String hmac = params[1];
        if(!hmac.equals(openapiService.HmacEncode(xml))) return "{\"error\" : \"변조된 값이 감지되었습니다.\"}";
        return openapiService.xmlToJson(xml);
    }

}