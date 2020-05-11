package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.Model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
    @Query(value = "select pet.* from pet, pet_type where pet.pettypeid = pet_type.pettypeid and pet_type.type_code = :type", nativeQuery = true)
    Page<Pet> findByPetType(@Param("type") String type, Pageable pageable);

    /*
    @Query(value = "select pet.* from pet, pet_type where pet.pettypeid = pet_type.pettypeid and pet_type.type_code = :type", nativeQuery = true)
    Page<Pet> findByLocation(@Param("dist") String dist, @Param("lat") BigDecimal lat, @Param("lon") BigDecimal lon, Pageable pageable);
    */
}