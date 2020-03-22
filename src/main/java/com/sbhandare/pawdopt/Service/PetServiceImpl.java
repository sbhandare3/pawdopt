package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.DTO.PaginationDTO;
import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.DTO.PetTypeDTO;
import com.sbhandare.pawdopt.Model.*;
import com.sbhandare.pawdopt.Repository.*;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PetServiceImpl implements PetService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityUserRepository securityUserRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private PetTypeRepository petTypeRepository;

    @Override
    public PetDTO getPetById(long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        return optionalPet.map(pet -> modelMapper.map(pet, PetDTO.class)).orElse(null);
    }

    @Override
    public PageDTO getAllPets(int page) {
        Pageable pageWithRecords = PageRequest.of(page, PawdoptConstantUtil.DEFAULT_PAGE_RESULTS, Sort.by(
                "petid"));
        Page<Pet> petPageList = petRepository.findAll(pageWithRecords);
        List<PetDTO> petList = null;
        if (petPageList != null && petPageList.getContent() != null && !petPageList.getContent().isEmpty()) {
            petList = petPageList.get()
                    .map(pets -> modelMapper.map(pets, PetDTO.class))
                    .collect(Collectors.toList());

            PaginationDTO paginationDTO = new PaginationDTO();
            paginationDTO.setCurrentPage(petPageList.getNumber());
            paginationDTO.setResultsPerPage(petPageList.getNumberOfElements());
            paginationDTO.setTotalResults(petPageList.getTotalElements());

            PageDTO pageDTO = new PageDTO(petList, paginationDTO);
            return pageDTO;
        }
        return null;
    }

    @Override
    public List<PetDTO> getPetsByUsername(String username) {
        SecurityUser securityUser = securityUserRepository.findByUsername(username);
        if (securityUser != null) {
            Set<Pet> petList = securityUser.getUser().getLikedPets();
            if (petList != null && !petList.isEmpty())
                return petList
                        .stream()
                        .map(pet -> modelMapper.map(pet, PetDTO.class))
                        .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<PetDTO> getPetsByOrgId(long orgid) {
        Optional<Organization> optionalOrganization = organizationRepository.findById(orgid);
        if (optionalOrganization.isPresent()) {
            Set<Pet> petByOrgList = optionalOrganization.get().getPets();
            if (petByOrgList != null && !petByOrgList.isEmpty()) {
                return petByOrgList
                        .stream()
                        .map(pet -> modelMapper.map(pet, PetDTO.class))
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public long savePet(PetDTO petDTO, long orgid) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Optional<Organization> optionalOrganization = organizationRepository.findById(orgid);
        String petTypeCode = petDTO.getTypeCode();
        PetType petType = petTypeRepository.findByTypeCode(petTypeCode);
        if (optionalOrganization.isPresent() && petType != null) {
            pet.setPetType(petType);
            pet.setOrganization(optionalOrganization.get());
            Pet savedPet = petRepository.save(pet);
            if (savedPet != null)
                return savedPet.getPetid();
        }
        return PawdoptConstantUtil.NO_SUCCESS;
    }

    @Override
    public Map<String, String> getAllPetTypes() {
        List<PetType> allPetTypes = petTypeRepository.findAll();
        if(!allPetTypes.isEmpty()){
            return allPetTypes
                    .stream()
                    .collect(Collectors.toMap(petType -> petType.getPetTypeDesc().toLowerCase(), PetType::getPetTypeCode));
        }
        return Collections.emptyMap();
    }
}
