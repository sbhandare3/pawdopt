package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.*;
import com.sbhandare.pawdopt.Model.*;
import com.sbhandare.pawdopt.Repository.*;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    @Autowired
    private LocationService locationService;

    @Override
    public PetDTO getPetById(long id, Map<String, String> userInfo) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        PetDTO petDTO = optionalPet.map(pet -> modelMapper.map(pet, PetDTO.class)).orElse(null);
        if(petDTO!=null && userInfo!=null && !userInfo.isEmpty()){
            Set<User> likedUsers = optionalPet.get().getLikedUsers();
            String username = userInfo.get("username");
            for(User likedUser : likedUsers){
                if(StringUtils.equals(likedUser.getSecurityUser().getUsername(),username))
                    petDTO.setCurrentUserFav(true);
            }
        }
        return petDTO;
    }

    @Override
    public PageDTO getAllPets(int page, Map<String, String> userInfo) {
        Pageable pageWithRecords = PageRequest.of(page, PawdoptConstantUtil.DEFAULT_PAGE_RESULTS, Sort.by(
                "petid"));
        Page<Pet> petPageList = petRepository.findAll(pageWithRecords);
        List<PetDTO> petDTOList = null;
        if (petPageList != null && petPageList.getContent() != null && !petPageList.getContent().isEmpty()) {
            petDTOList = petPageList.get()
                    .map(pets -> modelMapper.map(pets, PetDTO.class))
                    .collect(Collectors.toList());

            if(userInfo!=null && !userInfo.isEmpty()) {
                List<Pet> petList = petPageList.get().collect(Collectors.toList());

                Iterator<Pet> it1 = petList.iterator();
                Iterator<PetDTO> it2 = petDTOList.iterator();
                String username = userInfo.get("username");

                while (it1.hasNext() && it2.hasNext()) {
                    Pet pet = it1.next();
                    Set<User> likedUserSet = pet.getLikedUsers();
                    PetDTO petDTO = it2.next();

                   if ((pet.getPetid() == petDTO.getPetid()) && (likedUserSet != null && !likedUserSet.isEmpty())) {
                        for (User user : likedUserSet) {
                            if (StringUtils.equals(user.getSecurityUser().getUsername(),username)) {
                                petDTO.setCurrentUserFav(true);
                                break;
                            }
                        }
                   }
                }
            }

            PaginationDTO paginationDTO = new PaginationDTO();
            paginationDTO.setCurrentPage(petPageList.getNumber());
            paginationDTO.setResultsPerPage(petPageList.getNumberOfElements());
            paginationDTO.setTotalResults(petPageList.getTotalElements());

            PageDTO pageDTO = new PageDTO(petDTOList, paginationDTO);
            return pageDTO;
        }
        return null;
    }

    @Override
    public PageDTO getPetsByFilter(int page, Map<String, String> userInfo, String type, String location) {
        Pageable pageWithRecords = PageRequest.of(page, PawdoptConstantUtil.DEFAULT_PAGE_RESULTS, Sort.by(
                "petid"));
        Page<Pet> petPageList = null;
        if(StringUtils.isEmpty(type) && StringUtils.isEmpty(location)) {
            // both are empty - find all
            petPageList = petRepository.findAll(pageWithRecords);
        }
        else if(!StringUtils.isEmpty(type) && StringUtils.isEmpty(location)) {
            // only type is passed
            petPageList = petRepository.findByPetType(type, pageWithRecords);
        }
        else if(StringUtils.isEmpty(type) && !StringUtils.isEmpty(location)){
            // only location is passed
            String[] latLonDist = location.split(",");
            petPageList = petRepository.findByLocation(Integer.parseInt(latLonDist[0]),
                                                        new BigDecimal(latLonDist[1]),
                                                        new BigDecimal(latLonDist[2]),
                                                        pageWithRecords);

        }else{
            // both are not empty
            String[] latLonDist = location.split(",");
            petPageList = petRepository.findByTypeLocation(Integer.parseInt(latLonDist[0]),
                                                            new BigDecimal(latLonDist[1]),
                                                            new BigDecimal(latLonDist[2]),
                                                            type,
                                                            pageWithRecords);
        }
        if (petPageList != null && petPageList.getContent() != null && !petPageList.getContent().isEmpty()) {
            List<PetDTO> petDTOList = petPageList.get()
                                    .map(pets -> modelMapper.map(pets, PetDTO.class))
                                    .collect(Collectors.toList());
            if(!petDTOList.isEmpty()) {
                if (userInfo != null && !userInfo.isEmpty()) {
                    List<Pet> petList = petPageList.get().collect(Collectors.toList());

                    Iterator<Pet> it1 = petList.iterator();
                    Iterator<PetDTO> it2 = petDTOList.iterator();
                    String username = userInfo.get("username");

                    while (it1.hasNext() && it2.hasNext()) {
                        Pet pet = it1.next();
                        Set<User> likedUserSet = pet.getLikedUsers();
                        PetDTO petDTO = it2.next();

                        if ((pet.getPetid() == petDTO.getPetid()) && (likedUserSet != null && !likedUserSet.isEmpty())) {
                            for (User user : likedUserSet) {
                                if (StringUtils.equals(user.getSecurityUser().getUsername(), username)) {
                                    petDTO.setCurrentUserFav(true);
                                    break;
                                }
                            }
                        }
                    }
                }

                PaginationDTO paginationDTO = new PaginationDTO();
                paginationDTO.setCurrentPage(petPageList.getNumber());
                paginationDTO.setResultsPerPage(petPageList.getNumberOfElements());
                paginationDTO.setTotalResults(petPageList.getTotalElements());

                PageDTO pageDTO = new PageDTO(petDTOList, paginationDTO);
                return pageDTO;
            }
        }
        return null;
    }

    @Override
    public List<PetDTO> getPetsByUsername(String username) {
        SecurityUser securityUser = securityUserRepository.findByUsername(username);
        if (securityUser != null) {
            Set<Pet> petSet = securityUser.getUser().getLikedPets();
            if (petSet != null && !petSet.isEmpty()) {
                List<PetDTO> petDTOList = petSet
                                            .stream()
                                            .map(pet -> modelMapper.map(pet, PetDTO.class))
                                            .collect(Collectors.toList());

                if(!petDTOList.isEmpty()) {
                    for (PetDTO petDTO : petDTOList) {
                        petDTO.setCurrentUserFav(true);
                    }
                }
                return petDTOList;
            }

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

    @Override
    public void updateLatLongForExistingPets() {
        List<Pet> petList = (List<Pet>) petRepository.findAll();
        List<PetDTO> petDTOList = petList
                                .stream()
                                .map(pet -> modelMapper.map(pet, PetDTO.class))
                                .collect(Collectors.toList());
        if(!petDTOList.isEmpty()){
            for(PetDTO petDTO : petDTOList){
                GeoPoint point = locationService.getLatLongFromAddress(petDTO.getOrganizationDTO().getAddressDTO());
                petRepository.updateLatLonForPet(petDTO.getPetid(),point.getLattitude(),point.getLongitude());
                //System.out.println("updated : "+petDTO.getPetid()+" "+point.getLattitude()+" "+point.getLongitude());
            }
        }
    }
}
