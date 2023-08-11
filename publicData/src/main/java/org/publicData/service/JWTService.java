package org.publicData.service;

import java.util.Date;

import org.publicData.TokenEnum;
import org.publicData.entity.SubToken;
import org.publicData.repository.SubTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTService {

    @Autowired
    private SubTokenRepository subTokenRepository;

    Algorithm algorithm = Algorithm.HMAC256("D!Jsk4qw3ej");

    // 토큰 생성
    public String createToken(String org_code, String scope){
        String token = JWT.create()
        .withIssuer(TokenEnum.ISSUER) // iss
        .withAudience(TokenEnum.AUDIENCE) // aud
        .withJWTId(TokenEnum.JWTID) // jti
        .withClaim("scope", scope)
        .withExpiresAt(new Date(System.currentTimeMillis() + 100*60))
        .sign(algorithm);
        subTokenRepository.save(SubToken.builder().org_code(org_code).sub_token(token).build());
        return token;
    }

    // 토큰 중복 체크
    public boolean isdupToken(String org_code){
        SubToken token = subTokenRepository.HasTokenByOrgCode(org_code);
        if(token == null) return false;
        try {
            decodeToken(token.getSub_token());
        } catch (TokenExpiredException e) {
            return false;
        }
        return true;
    }

    // 토큰 갱신
    public String modifyToken(String org_code, String scope){
        SubToken entity = subTokenRepository.HasTokenByOrgCode(org_code);
        String newToken = createToken(org_code, scope);
        entity.setSub_token(newToken);
        return newToken;
    }

    // 토큰 폐기
    public void deleteToken(String org_code) {
        subTokenRepository.deleteByOrgCode(org_code);
    }

    // 디코딩
    public DecodedJWT decodeToken(String encodeToken) throws TokenExpiredException {
        JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(TokenEnum.ISSUER).build();
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
