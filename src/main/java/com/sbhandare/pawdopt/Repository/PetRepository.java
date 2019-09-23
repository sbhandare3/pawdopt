package com.sbhandare.pawdopt.Repository;

import com.sbhandare.pawdopt.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PetRepository extends JpaRepository<Pet, Integer> {
}
