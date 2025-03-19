package com.andersonbrito.multitenant.bula;

import com.fasterxml.uuid.Generators;

public record BulaCreateRequest(String descricao, String contraIndicacoes) {

    public Bula toEntity() {
        var bula = new Bula();
        bula.setId(Generators.timeBasedEpochGenerator().generate());
        bula.setDescricao(descricao);
        bula.setContraIndicacoes(contraIndicacoes);

        return bula;
    }
}
