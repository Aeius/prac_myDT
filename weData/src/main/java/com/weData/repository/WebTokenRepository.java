package com.weData.repository;

import com.weData.entity.WebToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebTokenRepository extends JpaRepository<WebToken, Long>{
    
}
