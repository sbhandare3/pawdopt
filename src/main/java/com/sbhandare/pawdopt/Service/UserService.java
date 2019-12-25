package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.DTO.SecurityUserDTO;
import com.sbhandare.pawdopt.DTO.UserDTO;

public interface UserService {
    UserDTO getUserById(long id);
    UserDTO getUserByUsername(String username);
    PageDTO getAllUsers(int page);
    long saveNewUser(UserDTO userDTO, SecurityUserDTO securityUserDTO);
    long saveUserFavorite(String username, long petid);
}