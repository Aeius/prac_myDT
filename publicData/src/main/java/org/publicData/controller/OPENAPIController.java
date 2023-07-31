package org.publicData.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.json.XML;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.publicData.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;

@RestController
@RequestMapping("/openapi")
class OPENAPIController {
    
    @Autowired
    private JWTService service;

    @GetMapping("/first")
    public String openApi_1(HttpServletRequest request) throws IOException{
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String org_code = request.getHeader("OrgCode");
        if(!service.validateToken(org_code, token)) return "{\"error\" : \"유효하지 않은 토큰입니다.\"}";

        DecodedJWT decodedJWT = service.decodeToken(token);
        String scope = decodedJWT.getClaim("scope").toString().replaceAll("\"", "");
        if(!scope.equals("first")) return "{\"error\" : \"유효하지 않은 scope 범위입니다.\"}";

        String xmlData = apicall("1");
        String[] params = xmlData.split("\\|");
        String xml = params[0];
        String hmac = params[1];
        if(!hmac.equals(HmacEncode(xml))) return "{\"error\" : \"변조된 값이 감지되었습니다.\"}";
        return xmlToJson(xml);
    }

    @GetMapping("/second")
    public String open_api2(HttpServletRequest request) throws IOException{
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String org_code = request.getHeader("OrgCode");
        if(!service.validateToken(org_code, token)) return "{\"error\" : \"유효하지 않은 토큰입니다.\"}";

        DecodedJWT decodedJWT = service.decodeToken(token);
        String scope = decodedJWT.getClaim("scope").toString().replaceAll("\"", "");
        if(!scope.equals("second")) return "{\"error\" : \"유효하지 않은 scope 범위입니다.\"}";

        String xmlData = apicall("2");
        String[] params = xmlData.split("\\|");
        String xml = params[0];
        String hmac = params[1];
        if(!hmac.equals(HmacEncode(xml))) return "{\"error\" : \"변조된 값이 감지되었습니다.\"}";
        return xmlToJson(xml);
    }

    // 부산 건설 공사 현황 Api 호출
    public String apicall(String page) throws IOException{
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/BusanCnstrWorkInfoService/getCnstrWorkInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=535bueR76aSZ3feMbvx7/mi7l1lrPiL4u53cgVXxNMbb05MNzCKcTZRR/Dikq0H3cqCdQbze7jVuStm3yJ5ZcQ=="); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(page, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        //urlBuilder.append("&" + URLEncoder.encode("cnstrcNm","UTF-8") + "=" + URLEncoder.encode("레이저가공기술산업화지원센터 건립공사", "UTF-8")); /*공사명*/
        //urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*JSON방식으로 호출 시 파라미터 resultType=json 입력*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json; charset=UTF-8");
        System.out.println("Response code: " + conn.getResponseCode());
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        inputStream = (InputStream) conn.getContent();
        inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(inputStreamReader);
        } else {
            rd = new BufferedReader(inputStreamReader);
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        sb.append("|" + HmacEncode(sb.toString()));
        return sb.toString();
    }

    // 결과값 Hmac 암호화
    public String HmacEncode(String data){
        String algorithm = "HmacSHA512";
        String secretKey = "SECERTKEY99";
        try {
            Mac haser = Mac.getInstance(algorithm);
            haser.init(new SecretKeySpec(secretKey.getBytes("utf-8"), algorithm));
            byte[] hash = haser.doFinal(data.getBytes());
            return Base64.encodeBase64String(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // XML to JSON
    public String xmlToJson(String xmlData){
        JSONObject jObject = XML.toJSONObject(xmlData);
        return jObject.toString();
    }
}