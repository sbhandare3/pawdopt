package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.DTO.UserDTO;

import java.util.List;

public interface UserService {
    public UserDTO getUserById(int id);
    public List<UserDTO> getAllUsers();
    public void saveUser(UserDTO userDTO);
}