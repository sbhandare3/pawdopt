package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.DTO.PetDTO;

import java.util.List;

public interface PetService {
    PetDTO getPetById(int id);
    PageDTO getAllPets(int page);
    List<PetDTO> getPetsByUsername(String username);
    List<PetDTO> getPetsByOrgId(int orgid);
    int savePet(PetDTO petDTO, int orgid);
}
