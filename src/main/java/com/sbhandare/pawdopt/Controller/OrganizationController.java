package com.sbhandare.pawdopt.Controller;

import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.Service.OrganizationService;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getAllOrganizations(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        PageDTO orgPageDTO = organizationService.getAllOrganizations(page);
        if(orgPageDTO == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return orgPageDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getOrganization(@PathVariable(value = "id") int orgid) {
        OrganizationDTO organizationDTO = organizationService.getOrganizationById(orgid);
        if (organizationDTO == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return organizationDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object createOrganization(@Valid @RequestBody OrganizationDTO organizationDTO) {
        int newOrgId = organizationService.saveOrganization(organizationDTO);
        if(newOrgId == PawdoptConstantUtil.NO_SUCCESS)
            return new ResponseEntity<Void>(HttpStatus.SERVICE_UNAVAILABLE);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
