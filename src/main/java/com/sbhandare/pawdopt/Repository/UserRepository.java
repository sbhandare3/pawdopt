package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
}
