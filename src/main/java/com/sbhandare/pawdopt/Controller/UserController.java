package com.sbhandare.pawdopt.Controller;

import com.sbhandare.pawdopt.DTO.UserDTO;
import com.sbhandare.pawdopt.Repository.UserRepository;
import com.sbhandare.pawdopt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UserDTO getUser(@PathVariable(value = "id") int userid)
            throws Exception {
        UserDTO user = userRepository.findById(userid)
                .orElseThrow(() -> new Exception("User: " + userid + " not found"));
        return user;
    }
}
