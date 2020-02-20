package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.DTO.PageDTO;

import java.util.List;

public interface OrganizationService {
    OrganizationDTO getOrganizationById(long id);
    PageDTO getAllOrganizations(int page);
    List<OrganizationDTO> getAllOrganizations();
    long saveOrganization(OrganizationDTO organizationDTO);
}
