package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.DTO.SecurityUserDTO;
import com.sbhandare.pawdopt.DTO.UserDTO;

public interface UserService {
    UserDTO getUserById(int id);
    UserDTO getUserByUsername(String username);
    PageDTO getAllUsers(int page);
    int saveNewUser(UserDTO userDTO, SecurityUserDTO securityUserDTO);
    int saveUserFavorite(String username, int petid);
}