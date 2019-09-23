package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.Model.Address;
import com.sbhandare.pawdopt.Model.Organization;
import com.sbhandare.pawdopt.Repository.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDTO getOrganizationById(int id) {
        Optional<Organization> optionalOrganization = organizationRepository.findById(id);
        OrganizationDTO organizationDTO = null;
        if(optionalOrganization.isPresent()){
            organizationDTO = modelMapper.map(optionalOrganization.get(),OrganizationDTO.class);
        }
        return organizationDTO;
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizationList = organizationRepository.findAll();
        return organizationList
                .stream()
                .map(orgs -> modelMapper.map(orgs, OrganizationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void saveOrganization(OrganizationDTO organizationDTO) {
        Organization organization = modelMapper.map(organizationDTO, Organization.class);
        Address address = organization.getAddress();
        address.setOrganization(organization);
        organizationRepository.save(organization);
    }
}
