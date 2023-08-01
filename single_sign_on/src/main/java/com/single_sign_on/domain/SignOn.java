package com.single_sign_on.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class SignOn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "CI", length = 1000)
    private String CI;

    @Builder
    public SignOn( String name, String CI) {
        this.name = name;
        this.CI = CI;
    }
}
