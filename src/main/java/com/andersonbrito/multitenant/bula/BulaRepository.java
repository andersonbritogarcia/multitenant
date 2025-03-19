package com.andersonbrito.multitenant.bula;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BulaRepository extends JpaRepository<Bula, UUID> {}
