package com.sbhandare.pawdopt.Controller;

import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.DTO.UserDTO;
import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.Repository.UserRepository;
import com.sbhandare.pawdopt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDTO getUser(@PathVariable(value = "id") int userid)
            throws Exception {
        return userService.getUserById(userid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Map<String, Boolean> createUser(@Valid @RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        Map<String,Boolean> response = new HashMap<>();
        response.put("User Created", Boolean.TRUE);
        return response;
    }

    /*
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public Map<String, Boolean> deleteUser(@PathVariable(value="id") int userid) throws Exception {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new Exception("User " + userid + " not found"));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    */
}
