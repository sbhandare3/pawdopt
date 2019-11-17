package com.sbhandare.pawdopt.Controller;

import com.sbhandare.pawdopt.DTO.UserDTO;
import com.sbhandare.pawdopt.Service.UserService;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        if(userList == null || userList.isEmpty())
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return userList;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getUser(@PathVariable(value = "id") int uid){
        UserDTO userDTO = userService.getUserById(uid);
        if(userDTO == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return userDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object createUser(@Valid @RequestBody UserDTO userDTO) {
        int uid = userService.saveUser(userDTO);
        if(uid == PawdoptConstantUtil.NO_SUCCESS)
            return new ResponseEntity<Void>(HttpStatus.SERVICE_UNAVAILABLE);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
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
