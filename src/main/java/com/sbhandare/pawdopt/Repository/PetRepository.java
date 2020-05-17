package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.Model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@Transactional
public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {

    @Modifying
    @Query(value = "update pet set latitude = :lat , longitude = :lon where petid = :id", nativeQuery = true)
    void updateLatLonForPet(@Param("id") long id, @Param("lat") BigDecimal lat, @Param("lon") BigDecimal lon);

    @Query(value = "select pet.* from pet, pet_type where pet.pettypeid = pet_type.pettypeid and pet_type.type_code = :type", nativeQuery = true)
    Page<Pet> findByPetType(@Param("type") String type, Pageable pageable);

    @Query(value = "select pet.* " +
            "from pet " +
            "where (3959 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(latitude)))) < :dist " +
            "order by (3959 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(latitude))))", nativeQuery = true)
    Page<Pet> findByLocation(@Param("dist") int dist, @Param("lat") BigDecimal lat, @Param("lon") BigDecimal lon, Pageable pageable);

    @Query(value = "select pet.* " +
            "from pet, pet_type " +
            "where (3959 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(latitude)))) < :dist " +
            "and pet.pettypeid = pet_type.pettypeid " +
            "and pet_type.type_code = :type " +
            "order by (3959 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(latitude))))", nativeQuery = true)
    Page<Pet> findByTypeLocation(@Param("dist") int dist, @Param("lat") BigDecimal lat, @Param("lon") BigDecimal lon, @Param("type") String type, Pageable pageable);
}