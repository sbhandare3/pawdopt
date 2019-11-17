package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.UserDTO;
import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.Repository.UserRepository;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
        return optionalUser.map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    @Override
    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        if(users != null && !users.isEmpty())
        return users
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return Collections.emptyList();
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.saveAndFlush(user);
        if(savedUser != null)
            return savedUser.getUserId();
        return PawdoptConstantUtil.NO_SUCCESS;
    }
}