package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserById(int userId);
    List<UserDTO> getAllUsers();
}