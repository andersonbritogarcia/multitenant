package tech.andersonbrito.app.pet.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.andersonbrito.app.pet.persistence.model.Pet;
import tech.andersonbrito.app.pet.persistence.model.PetCategory;
import tech.andersonbrito.app.pet.persistence.repository.PetRepository;

import java.util.List;

@Service
public class PetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetService.class);

    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "pets", keyGenerator = "tenantWithoutParamsKeyGenerator")
    @Transactional
    public Pet adoptPet(String name, PetCategory category) {
        LOGGER.info("Adopting pet {}", name);
        return repository.save(new Pet(name, category));
    }

    @Cacheable(value = "pets", keyGenerator = "tenantWithoutParamsKeyGenerator")
    @Transactional(readOnly = true)
    public List<Pet> getAllPets() {
        LOGGER.info("Getting all pets");
        return repository.findAll();
    }
}
