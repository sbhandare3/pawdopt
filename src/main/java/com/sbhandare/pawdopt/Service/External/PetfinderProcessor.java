package com.sbhandare.pawdopt.Service.External;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetfinderProcessor {
    @Autowired
    private PetfinderService petfinderService;

    public void executePetfinderAPI(){
        petfinderService.getPetfinderData();
    }
}
