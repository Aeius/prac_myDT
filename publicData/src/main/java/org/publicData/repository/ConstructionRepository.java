package org.publicData.repository;

import org.publicData.entity.Construction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionRepository extends JpaRepository<Construction, String>{
    
}
