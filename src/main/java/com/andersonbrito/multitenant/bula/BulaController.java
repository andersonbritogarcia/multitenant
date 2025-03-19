package com.andersonbrito.multitenant.bula;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bulas")
public class BulaController {

    private final BulaRepository repository;

    public BulaController(BulaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    List<Bula> listAll() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BulaResponse create(@RequestBody BulaCreateRequest request) {
        var bula = request.toEntity();
        return new BulaResponse(repository.save(bula));
    }
}
