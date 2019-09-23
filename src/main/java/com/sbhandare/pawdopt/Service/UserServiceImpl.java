package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PetDTO;
import com.sbhandare.pawdopt.DTO.UserDTO;
import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO getUserById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        UserDTO userDTO = null;

        if(optionalUser.isPresent())
            userDTO = modelMapper.map(optionalUser.get(),UserDTO.class);

        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
    }

}