package org.publicData.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "orgCode")
    private String org_code;
    
    @Column(name = "clientId")
    private String client_id;

    @Column(name = "clientPassword")
    private String client_password;


    @Builder
    public Client(String client_id, String client_password, String org_code){
        this.org_code = org_code;
        this.client_id = client_id;
        this.client_password = client_password;
    }

}
