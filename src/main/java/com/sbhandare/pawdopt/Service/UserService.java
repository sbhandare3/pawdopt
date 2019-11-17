package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserById(int id);
    List<UserDTO> getAllUsers();
    int saveUser(UserDTO userDTO);
}