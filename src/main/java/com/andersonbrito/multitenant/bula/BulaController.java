package com.andersonbrito.multitenant.bula;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulas")
public class BulaController {

    private static final Logger LOG = LoggerFactory.getLogger(BulaController.class);
    private final BulaRepository repository;

    public BulaController(BulaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Bula> listAll() {
        LOG.info("Returning all instruments");
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BulaResponse create(@RequestBody BulaCreateRequest request) {
        var bula = request.toEntity();
        return new BulaResponse(repository.save(bula));
    }
}
