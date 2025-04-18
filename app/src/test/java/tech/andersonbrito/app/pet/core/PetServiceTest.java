package tech.andersonbrito.app.pet.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.andersonbrito.app.pet.persistence.model.Pet;
import tech.andersonbrito.app.pet.persistence.model.PetCategory;
import tech.andersonbrito.app.pet.persistence.repository.PetRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;
    @Mock
    private PetRepository repository;

    private final String name = "Nayla";

    @Test
    void shouldAdoptPet() {
        var pet = new Pet(name, PetCategory.CAT);
        when(repository.save(any(Pet.class))).thenReturn(pet);

        var result = petService.adoptPet(name, PetCategory.CAT);

        assertEquals(name, result.getName());
        assertEquals(PetCategory.CAT, result.getCategory());
    }

    @Test
    void shouldRetrieveAllPets() {
        var pet = new Pet(name, PetCategory.CAT);
        when(repository.findAll()).thenReturn(List.of(pet));

        var result = petService.getAllPets();

        assertEquals(1, result.size());
        assertEquals(name, result.getFirst().getName());
        assertEquals(PetCategory.CAT, result.getFirst().getCategory());
    }
}