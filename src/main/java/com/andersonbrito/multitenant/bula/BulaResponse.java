package com.andersonbrito.multitenant.bula;

import java.util.UUID;

public record BulaResponse(UUID id, String descricao, String contraIndicacoes) {

    public BulaResponse(Bula bula) {
        this(bula.getId(), bula.getDescricao(), bula.getContraIndicacoes());
    }
}
