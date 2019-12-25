package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.DTO.PageDTO;

public interface OrganizationService {
    OrganizationDTO getOrganizationById(long id);
    PageDTO getAllOrganizations(int page);
    long saveOrganization(OrganizationDTO organizationDTO);
}
