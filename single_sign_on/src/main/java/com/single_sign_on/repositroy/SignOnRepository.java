package com.single_sign_on.repositroy;

import com.single_sign_on.domain.SignOn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignOnRepository extends JpaRepository<SignOn, String> {
}
