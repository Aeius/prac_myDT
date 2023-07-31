package org.publicData.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "orgCode", length = 100)
    private String org_code;
    @Column(name = "subToken", length = 1000)
    private String sub_token;

    @Builder
    public SubToken(String org_code, String sub_token){
        this.org_code = org_code;
        this.sub_token = sub_token;    
    }
    
}
