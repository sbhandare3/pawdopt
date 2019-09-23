package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.Model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
