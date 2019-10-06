package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.Model.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface PetTypeRepository extends JpaRepository<PetType,Integer> {
    @Query(value = "select * from pet_type pt where pt.type_code = :code", nativeQuery = true)
    PetType findByTypeCode(@Param("code") String code);
}
