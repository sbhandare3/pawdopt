package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.OrganizationDTO;

import java.util.List;

public interface OrganizationService {
    public OrganizationDTO getOrganizationById(int id);
    public List<OrganizationDTO> getAllOrganizations();
    public void saveOrganization(OrganizationDTO organizationDTO);
}
