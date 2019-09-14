package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.DTO.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<UserDTO, Integer> {
}
