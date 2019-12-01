package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String role);
}
