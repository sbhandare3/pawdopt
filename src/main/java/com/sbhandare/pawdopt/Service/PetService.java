package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PetDTO;

import java.util.List;

public interface PetService {
    public PetDTO getPetById(int id);
    public List<PetDTO> getAllPets();
    public List<PetDTO> getPetsByUserId(int userid);
    public void savePet(PetDTO petDTO, int orgid);
}
