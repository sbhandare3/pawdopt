package com.sbhandare.pawdopt.Controller;

import com.sbhandare.pawdopt.Service.External.PetfinderProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/syncPetfinder")
public class PetfinderController {

    @Autowired
    private PetfinderProcessor petfinderProcessor;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Map<String, String> getPetfinderData(){
        petfinderProcessor.executePetfinderAPI();

        Map<String, String> response = new HashMap<>();
        response.put("Petfinder Service", "completed");
        return response;
    }
}
