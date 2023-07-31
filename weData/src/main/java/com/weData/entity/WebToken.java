package com.weData.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class WebToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "orgCode", length = 100)
    private String org_code;
    @Column(name = "webToken", length = 1000)
    private String web_token;

    @Builder
    public WebToken(String org_code, String web_token){
        this.org_code = org_code;
        this.web_token = web_token;    
    }
}
