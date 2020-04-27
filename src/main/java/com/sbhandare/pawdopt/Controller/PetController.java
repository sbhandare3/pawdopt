package com.sbhandare.pawdopt.Controller;

import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.Service.PetService;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object getAllPets(@RequestBody (required = false) Map<String, String> userInfo,
                             @RequestParam(value = "orgid", required = false) Long orgid,
                             @RequestParam(value = "username", required = false) String username,
                             @RequestParam(value = "type", required = false) String type,
                             @RequestParam(value = "location", required = false) String location,
                             @RequestParam(value = "gender", required = false) String gender,
                             @RequestParam(value = "size", required = false) String size,
                             @RequestParam(value = "age", required = false) String age,
                             @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        if (orgid != null && username == null) {
            List<PetDTO> allPetsByOrgId = petService.getPetsByOrgId(orgid);
            if (allPetsByOrgId == null || allPetsByOrgId.isEmpty())
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            return allPetsByOrgId;
        } else if (orgid == null && username != null) {
            List<PetDTO> allPetsByUser = petService.getPetsByUsername(username);
            if (allPetsByUser == null || allPetsByUser.isEmpty())
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            return allPetsByUser;
        }
        PageDTO petPageDTO = petService.getAllPets(page, userInfo);
        if (petPageDTO == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return petPageDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getPet(@PathVariable(value = "id") long petid) {
        PetDTO petDTO = petService.getPetById(petid);
        if (petDTO == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return petDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object createPet(@Valid @RequestBody PetDTO petDTO, @RequestParam(name = "orgid") long orgid) {
        long petid = petService.savePet(petDTO,orgid);
        if(petid == PawdoptConstantUtil.NO_SUCCESS)
            return new ResponseEntity<Void>(HttpStatus.SERVICE_UNAVAILABLE);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
