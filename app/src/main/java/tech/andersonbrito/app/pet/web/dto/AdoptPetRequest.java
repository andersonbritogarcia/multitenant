package tech.andersonbrito.app.pet.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.andersonbrito.app.pet.persistence.model.PetCategory;

public record AdoptPetRequest(@NotBlank String name, @NotNull PetCategory category) {
}
