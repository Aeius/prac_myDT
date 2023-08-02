package com.weData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weData.entity.WebToken;

@Repository
public interface WebTokenRepository extends JpaRepository<WebToken, Long>{
    
}
