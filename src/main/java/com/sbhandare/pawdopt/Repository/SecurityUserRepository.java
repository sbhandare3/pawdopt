package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.Model.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityUserRepository extends JpaRepository<SecurityUser,Integer> {
    SecurityUser findByUsername(String username);
}