package tech.andersonbrito.app.pet.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.andersonbrito.app.pet.persistence.model.Pet;

import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {
}
