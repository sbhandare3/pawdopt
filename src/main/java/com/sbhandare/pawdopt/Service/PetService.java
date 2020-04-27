package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.DTO.PetTypeDTO;

import java.util.List;
import java.util.Map;

public interface PetService {
    PetDTO getPetById(long id);
    PageDTO getAllPets(int page, Map<String, String> userInfo);
    List<PetDTO> getPetsByUsername(String username);
    List<PetDTO> getPetsByOrgId(long orgid);
    long savePet(PetDTO petDTO, long orgid);
    Map<String, String> getAllPetTypes();
}
