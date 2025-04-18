package tech.andersonbrito.app.pet.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Pet {

    @Id
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PetCategory category;

    public Pet() {
    }

    public Pet(String name, PetCategory category) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetCategory getCategory() {
        return category;
    }

    public void setCategory(PetCategory category) {
        this.category = category;
    }
}
