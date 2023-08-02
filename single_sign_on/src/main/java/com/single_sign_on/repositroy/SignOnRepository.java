package com.single_sign_on.repositroy;

import com.single_sign_on.domain.SignOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SignOnRepository extends JpaRepository<SignOn, String> {
    @Query(value = "select * from sign_on where ci = :ci and name = :name", nativeQuery = true)
    SignOn verify(@Param("ci")String ci, @Param("name")String name);
}
