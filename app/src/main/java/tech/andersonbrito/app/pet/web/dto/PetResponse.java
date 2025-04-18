package tech.andersonbrito.app.pet.web.dto;

import tech.andersonbrito.app.pet.persistence.model.Pet;
import tech.andersonbrito.app.pet.persistence.model.PetCategory;

import java.util.UUID;

public record PetResponse(UUID id, String name, PetCategory category) {

    public PetResponse(Pet pet) {
        this(pet.getId(), pet.getName(), pet.getCategory());
    }
}
