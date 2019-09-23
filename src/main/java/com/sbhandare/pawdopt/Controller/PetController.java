package com.sbhandare.pawdopt.Controller;

import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @RequestMapping(value = "/allpets", method = RequestMethod.GET)
    public List<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PetDTO getPet(@PathVariable(value = "id") int petid)
            throws Exception {
        return petService.getPetById(petid);
    }

    @RequestMapping(value = "/userlikes", method = RequestMethod.GET)
    public List<PetDTO> getUserLikes(@RequestParam(value="userid") int userid){
        return petService.getPetsByUserId(userid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Map<String, Boolean> createPet(@Valid @RequestBody PetDTO petDTO, @RequestParam(name = "orgid") int id){
        petService.savePet(petDTO, id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Pet Created", Boolean.TRUE);
        return response;
    }
}
