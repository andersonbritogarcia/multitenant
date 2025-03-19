package com.andersonbrito.multitenant.bula;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Bula {

    @Id
    private UUID id;
    private String descricao;
    private String contraIndicacoes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContraIndicacoes() {
        return contraIndicacoes;
    }

    public void setContraIndicacoes(String contraIndicacoes) {
        this.contraIndicacoes = contraIndicacoes;
    }
}
