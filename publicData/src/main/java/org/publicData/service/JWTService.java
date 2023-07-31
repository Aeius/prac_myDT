package org.publicData.service;

import java.util.Date;

import org.publicData.entity.SubToken;
import org.publicData.repository.SubTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTService {

    @Autowired
    private SubTokenRepository subTokenRepository;

    Algorithm algorithm = Algorithm.HMAC256("D!Jsk4qw3ej");

    // 토큰 생성
    public String createToken(String scope){
        String org_code = "ZADEV0000";
        String token = JWT.create()
        .withIssuer("subAPI")
        .withClaim("org_code", org_code)
        .withClaim("scope", scope)
        .withExpiresAt(new Date(System.currentTimeMillis() + 100*60))
        .sign(algorithm);
        subTokenRepository.save(SubToken.builder().org_code(org_code).sub_token(token).build());
        return token;
    }

    // 디코딩
    public DecodedJWT decodeToken(String encodeToken) throws TokenExpiredException {
        JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer("subAPI").build();
        DecodedJWT decodedJWT = jwtVerifier.verify(encodeToken);
        return decodedJWT;
    }

    // 유효 토큰 검증
    public boolean validateToken(String org_code, String giveToken) {
        SubToken token = subTokenRepository.HasToken(org_code, giveToken);
        // 해당하는 토큰 없는 경우
        if(token == null) return false;

        // 토큰 유효기간 만료된 경우
        try {
            decodeToken(giveToken);
        } catch (TokenExpiredException e) {
            return false;
        }
        return true;
    }

}
