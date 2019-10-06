package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.Model.Organization;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.PetType;
import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.Repository.OrganizationRepository;
import com.sbhandare.pawdopt.Repository.PetRepository;
import com.sbhandare.pawdopt.Repository.PetTypeRepository;
import com.sbhandare.pawdopt.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private OrganizationRepository organizationRepository;
    @Autowired
    private PetTypeRepository petTypeRepository;

    @Override
    public PetDTO getPetById(int id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        PetDTO petDTO = null;
        if(optionalPet.isPresent()){
            petDTO = modelMapper.map(optionalPet.get(),PetDTO.class);
        }
        return petDTO;
    }

    @Override
    public List<PetDTO> getAllPets(){
        List<Pet> petList = petRepository.findAll();
        return petList
                .stream()
                .map(pet -> modelMapper.map(pet, PetDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PetDTO> getPetsByUserId(int userid) {
        Optional<User> optionalUser = userRepository.findById(userid);
        if(optionalUser.isPresent()){
            Set<Pet> petList = optionalUser.get().getLikedPets();
            return petList.stream().map(pet -> modelMapper.map(pet, PetDTO.class)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void savePet(PetDTO petDTO, int orgid) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Optional<Organization> optionalOrganization = organizationRepository.findById(orgid);
        String petTypeCode = petDTO.getTypeCode();
        PetType petType = petTypeRepository.findByTypeCode(petTypeCode);
        if(optionalOrganization.isPresent() && petType != null){
            pet.setPetType(petType);
            pet.setOrganization(optionalOrganization.get());
            petRepository.save(pet);
        }
    }

    @Override
    public List<PetDTO> getPetsByOrgId(int orgid) {
        Optional<Organization> optionalOrganization = organizationRepository.findById(orgid);
        if(optionalOrganization.isPresent()) {
            Set<Pet> petByOrgList = optionalOrganization.get().getPets();
            if (petByOrgList != null && !petByOrgList.isEmpty()) {
                return petByOrgList.stream().map(pet -> modelMapper.map(pet, PetDTO.class)).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
