package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO getUserById(int userId) {
        UserDTO testUser = new UserDTO(1,"Mark","Kaggle", "mark.kaggle@test.com", "1234567890");
        if(userId == testUser.getUserId())
            return testUser;
        return new UserDTO();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO(1,"Mark","Kaggle", "mark.kaggle@test.com", "1234567890"));
        users.add(new UserDTO(2,"Don","Toggle", "don.toggle@test.com", "9087654321"));
        return users;
    }
}
