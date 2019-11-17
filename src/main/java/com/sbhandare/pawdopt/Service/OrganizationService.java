package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.DTO.PageDTO;

public interface OrganizationService {
    OrganizationDTO getOrganizationById(int id);
    PageDTO getAllOrganizations();
    int saveOrganization(OrganizationDTO organizationDTO);
}
