package com.sbhandare.pawdopt.Controller;

import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.Service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/allorganizations", method = RequestMethod.GET)
    public List<OrganizationDTO> getAllPets() {
        return organizationService.getAllOrganizations();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrganizationDTO getOrganization(@PathVariable(value = "id") int orgid)
            throws Exception {
        return organizationService.getOrganizationById(orgid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Map<String, Boolean> createOrganization(@Valid @RequestBody OrganizationDTO organizationDTO) {
        organizationService.saveOrganization(organizationDTO);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Organization Created", Boolean.TRUE);
        return response;
    }
}
