package com.single_sign_on.service;

import com.single_sign_on.domain.SignOn;
import com.single_sign_on.repositroy.SignOnRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class SignOnService {

    @Autowired
    private SignOnRepository signOnRepository;
    public SignOnService(SignOnRepository signOnRepository) {
        this.signOnRepository = signOnRepository;
    }

    private String Algorithms = "HmacSHA256";
    public String createCI(String key, Map<String,String> data) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        //1. SecretKeySpec 클래스를 사용한 키 생성
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("utf-8"), Algorithms);

        //2. 지정된  MAC 알고리즘을 구현하는 Mac 객체를 작성합니다.
        Mac hasher = Mac.getInstance(Algorithms);

        //3. 키를 사용해 이 Mac 객체를 초기화
        hasher.init(secretKey);
        String hmacData = data.get("name")+data.get("num")+data.get("num2");

        //3. 암호화 하려는 데이터의 바이트의 배열을 처리해 MAC 조작을 종료
        byte[] hash = hasher.doFinal(hmacData.getBytes());
        signOnRepository.save(SignOn.builder().name(data.get("name")).CI(Base64.encodeBase64String(hash)).build());

        //4. Base 64 Encode to String
        return Base64.encodeBase64String(hash);
    }

}
