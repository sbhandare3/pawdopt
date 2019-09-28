package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.OrganizationDTO;

import java.util.List;

public interface OrganizationService {
    OrganizationDTO getOrganizationById(int id);
    List<OrganizationDTO> getAllOrganizations();
    void saveOrganization(OrganizationDTO organizationDTO);
}
