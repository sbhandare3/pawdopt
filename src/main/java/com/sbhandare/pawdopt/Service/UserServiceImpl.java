package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.PageDTO;
import com.sbhandare.pawdopt.DTO.PaginationDTO;
import com.sbhandare.pawdopt.DTO.SecurityUserDTO;
import com.sbhandare.pawdopt.DTO.UserDTO;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.Model.Role;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.Repository.PetRepository;
import com.sbhandare.pawdopt.Repository.RoleRepository;
import com.sbhandare.pawdopt.Repository.SecurityUserRepository;
import com.sbhandare.pawdopt.Repository.UserRepository;
import com.sbhandare.pawdopt.Util.PawdoptConstantUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO getUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        SecurityUser securityUser = securityUserRepository.findByUsername(username);
        if(securityUser != null)
            return modelMapper.map(securityUser.getUser(),UserDTO.class);
        return null;
    }

    @Override
    public PageDTO getAllUsers(int page){
        Pageable pageWithRecords = PageRequest.of(page, PawdoptConstantUtil.DEFAULT_PAGE_RESULTS, Sort.by(
                "userid"));
        Page<User> userPageList = userRepository.findAll(pageWithRecords);
        List<UserDTO> userList = null;
        if (userPageList != null && userPageList.getContent() != null && !userPageList.getContent().isEmpty()) {
            userList = userPageList.get()
                    .map(users -> modelMapper.map(users, UserDTO.class))
                    .collect(Collectors.toList());

            PaginationDTO paginationDTO = new PaginationDTO();
            paginationDTO.setCurrentPage(userPageList.getNumber());
            paginationDTO.setResultsPerPage(userPageList.getNumberOfElements());
            paginationDTO.setTotalResults(userPageList.getTotalElements());

            PageDTO pageDTO = new PageDTO(userList, paginationDTO);
            return pageDTO;
        }
        return null;
    }

    @Override
    public long saveNewUser(UserDTO userDTO, SecurityUserDTO securityUserDTO) {
        User user = modelMapper.map(userDTO, User.class);
        SecurityUser securityUser = mapToSecurityUser(securityUserDTO);
        user.setSecurityUser(securityUser);
        securityUser.setUser(user);
        User savedUser = userRepository.save(user);
        if(savedUser != null)
            return savedUser.getUserId();
        return PawdoptConstantUtil.NO_SUCCESS;
    }

    private SecurityUser mapToSecurityUser(SecurityUserDTO securityUserDTO){
        Set<Role> userRole = new HashSet<>();
        userRole.add(roleRepository.findByRoleName(PawdoptConstantUtil.ROLE_USER));
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(securityUserDTO.getUsername());
        securityUser.setPassword(securityUserDTO.getPassword());
        securityUser.setRoles(userRole);
        return securityUser;
    }

    @Override
    public long saveUserFavorite(String username, long petid) {
        SecurityUser securityUser = securityUserRepository.findByUsername(username);
        Optional<Pet> optionalPet = petRepository.findById(petid);
        if(securityUser != null && optionalPet.isPresent()) {
            User user = securityUser.getUser();
            Pet pet = optionalPet.get();
            if(user.getLikedPets()==null && pet.getLikedUsers()==null){
                user.setLikedPets(new HashSet<>(Collections.singletonList(pet)));
                pet.setLikedUsers(new HashSet<>(Collections.singletonList(user)));
                User savedUser = userRepository.save(user);
                return savedUser.getUserId();
            }else if(user.getLikedPets()!=null && pet.getLikedUsers()==null){
                user.getLikedPets().add(pet);
                pet.setLikedUsers(new HashSet<>(Collections.singletonList(user)));
                User savedUser = userRepository.save(user);
                return savedUser.getUserId();
            }else if(user.getLikedPets()==null && pet.getLikedUsers()!=null){
                user.setLikedPets(new HashSet<>(Collections.singletonList(pet)));
                pet.getLikedUsers().add(user);
                User savedUser = userRepository.save(user);
                return savedUser.getUserId();
            }else{
                user.getLikedPets().add(pet);
                pet.getLikedUsers().add(user);
                User savedUser = userRepository.save(user);
                return savedUser.getUserId();
            }
        }
        return PawdoptConstantUtil.NO_SUCCESS;
    }
}