package com.sbhandare.pawdopt.Controller;

import com.sbhandare.pawdopt.DTO.UserDTO;
import com.sbhandare.pawdopt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public UserDTO getUserProfile(@RequestParam(value = "uid", required = true) int userId){
        UserDTO user = userService.getUserById(userId);
        return user; // how to return null json object? find out
    }

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public List<UserDTO> getAllUserProfiles(){
        List<UserDTO> allUserProfiles = userService.getAllUsers();
        return allUserProfiles;
    }
}
