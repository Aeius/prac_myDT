package org.publicData.service;

import java.util.HashMap;
import java.util.Map;

import org.publicData.entity.Client;
import org.publicData.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // 사업자, 기관코드 생성
    public Map<String, String> createClient(String org_code) {
        String client_id = RandomString.make(10);
        String client_password = RandomString.make(10);
        Map<String, String> map = new HashMap<String, String>();
        map.put("org_code", org_code);
        map.put("client_id", client_id);
        map.put("client_password", client_password);
        clientRepository.save(Client.builder().org_code(org_code).client_id(client_id).client_password(client_password).build());
        return map;
    }

    // 사업자, 기관코드 검증
    public boolean verifyClient(String org_code, String id, String password){
        if(clientRepository.verify(org_code, id, password) == null) return false;
        return true;
    }

    // 정보 주체 조회
    public boolean informationSubject(String CI){

        return true;
    }

    // 전송요구내역 검증
    public boolean transRequest(String CI){

        return true;
    }

    
}
