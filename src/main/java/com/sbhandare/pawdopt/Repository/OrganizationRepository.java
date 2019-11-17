package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.Model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Integer> {
    @Query(value = "select * from organization o where o.petfinder_code = :code", nativeQuery = true)
    Organization findByPetfinderCode(@Param("code") String code);
}
