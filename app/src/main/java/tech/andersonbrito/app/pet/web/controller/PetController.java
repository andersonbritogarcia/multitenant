package tech.andersonbrito.app.pet.web.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.andersonbrito.app.pet.core.PetService;
import tech.andersonbrito.app.pet.web.dto.AdoptPetRequest;
import tech.andersonbrito.app.pet.web.dto.PetResponse;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    PetResponse adoptPet(@RequestBody @Valid AdoptPetRequest adoptPetRequest) {
        var pet = petService.adoptPet(adoptPetRequest.name(), adoptPetRequest.category());
        return new PetResponse(pet);
    }

    @GetMapping
    List<PetResponse> getAllPets() {
        return petService.getAllPets()
                         .stream()
                         .map(PetResponse::new)
                         .toList();
    }
}
