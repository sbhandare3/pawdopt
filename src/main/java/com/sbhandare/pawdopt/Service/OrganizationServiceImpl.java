package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.OrganizationDTO;
import com.sbhandare.pawdopt.DTO.PaginationDTO;
import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.Model.Address;
import com.sbhandare.pawdopt.Model.Organization;
import com.sbhandare.pawdopt.Repository.OrganizationRepository;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return optionalOrganization.map(organization -> modelMapper.map(organization, OrganizationDTO.class)).orElse(null);
    }

    @Override
    public PageDTO getAllOrganizations() {
        Pageable pageWithRecords = PageRequest.of(0, PawdoptConstantUtil.DEFAULT_PAGE_RESULTS, Sort.by(
                "organizationid"));
        Page<Organization> organizationPageList = organizationRepository.findAll(pageWithRecords);
        List<OrganizationDTO> orgList = null;
        if (organizationPageList != null && organizationPageList.getContent() != null && !organizationPageList.getContent().isEmpty()) {
            orgList = organizationPageList.get()
                    .map(orgs -> modelMapper.map(orgs, OrganizationDTO.class))
                    .collect(Collectors.toList());

            PaginationDTO paginationDTO = new PaginationDTO();
            paginationDTO.setCurrentPage(organizationPageList.getNumber());
            paginationDTO.setResultsPerPage(organizationPageList.getNumberOfElements());
            //paginationDTO.setNextPage();
            //paginationDTO.setPrevPage();

            PageDTO pageDTO = new PageDTO(orgList, paginationDTO);
            return pageDTO;
        }
        return null;
    }

    @Override
    public int saveOrganization(OrganizationDTO organizationDTO) {
        if (organizationDTO.getPetfinderCode() == null
                || (organizationDTO.getPetfinderCode() != null && organizationRepository.findByPetfinderCode(organizationDTO.getPetfinderCode()) == null)) {
            Organization organization = modelMapper.map(organizationDTO, Organization.class);
            Address address = organization.getAddress();
            address.setOrganization(organization);
            //Organization savedOrg = organizationRepository.saveAndFlush(organization);
            Organization savedOrg = organizationRepository.save(organization);
            if (savedOrg != null)
                return savedOrg.getOrganizationid();
        }
        return PawdoptConstantUtil.NO_SUCCESS;
    }
}
