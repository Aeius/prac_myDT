package org.publicData.repository;

import org.publicData.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>{
    @Query(value = "select * from client where org_code=:org_code and client_id=:client_id and client_password=:client_password", nativeQuery = true)
    Client verify(@Param("org_code")String org_code, @Param("client_id")String client_id, @Param("client_password")String client_password);
}
