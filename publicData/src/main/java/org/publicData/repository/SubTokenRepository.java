package org.publicData.repository;

import org.publicData.entity.SubToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTokenRepository extends JpaRepository<SubToken, Long>{
    @Query(value = "select * from sub_token where org_code=:org_code and sub_token=:token", nativeQuery=true)
    SubToken HasToken(@Param("org_code")String org_code, @Param("token")String token);
}
