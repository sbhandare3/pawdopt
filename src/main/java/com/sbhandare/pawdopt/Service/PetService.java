package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PetDTO;

import java.util.List;

public interface PetService {
    PetDTO getPetById(int id);
    List<PetDTO> getAllPets();
    List<PetDTO> getPetsByUserId(int userid);
    List<PetDTO> getPetsByOrgId(int orgid);
    int savePet(PetDTO petDTO, int orgid);
}
