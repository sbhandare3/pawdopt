package com.sbhandare.pawdopt.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbhandare.pawdopt.Config.AES;
import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.DTO.SecurityUserDTO;
import com.sbhandare.pawdopt.DTO.UserDTO;
import com.sbhandare.pawdopt.Service.SecurityService;
import com.sbhandare.pawdopt.Service.UserService;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        PageDTO userPageDTO = userService.getAllUsers(page);
        if(userPageDTO == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return userPageDTO;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Object getUser(@PathVariable(value = "username") String username){
        UserDTO userDTO = userService.getUserByUsername(username);
        if(userDTO == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return userDTO;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object createUser(@Valid @RequestBody Map<String,Object> userObj) {
        UserDTO userDTO = mapper.convertValue((Map) userObj.get("user"),UserDTO.class);
        SecurityUserDTO securityUserDTO = mapper.convertValue((Map) userObj.get("securityUser"),SecurityUserDTO.class);
        long uid = userService.saveNewUser(userDTO, securityUserDTO);

        if(uid == PawdoptConstantUtil.NO_SUCCESS)
            return new ResponseEntity<Void>(HttpStatus.SERVICE_UNAVAILABLE);
        String response = securityService.loginAfterRegister(securityUserDTO.getUsername(),securityUserDTO.getPassword());
        if(StringUtils.equals(response,PawdoptConstantUtil.AUTO_LOGIN_FAILED))
            return new ResponseEntity<>(PawdoptConstantUtil.AUTO_LOGIN_FAILED,HttpStatus.CREATED);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.POST)
    public Object addUserFavorite(@PathVariable(value = "username") String username,
                                  @RequestParam(value = "petid", required = false) long petid){
        long uid = userService.saveUserFavorite(username, petid);
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
