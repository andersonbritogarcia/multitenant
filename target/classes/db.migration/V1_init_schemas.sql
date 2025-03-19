CREATE SCHEMA IF NOT EXISTS ey;
CREATE SCHEMA IF NOT EXISTS eurofarma;
CREATE SCHEMA IF NOT EXISTS idbrasil;

CREATE TABLE ey.bula
(
    id                UUID NOT NULL,
    descricao         VARCHAR(255) NOT NULL,
    contra_indicacoes VARCHAR(255),
    CONSTRAINT pk_bula PRIMARY KEY (id)
);

CREATE TABLE eurofarma.bula
(
    id                UUID NOT NULL,
    descricao         VARCHAR(255) NOT NULL,
    contra_indicacoes VARCHAR(255),
    CONSTRAINT pk_bula PRIMARY KEY (id)
);

CREATE TABLE idbrasil.bula
(
    id                UUID NOT NULL,
    descricao         VARCHAR(255) NOT NULL,
    contra_indicacoes VARCHAR(255),
    CONSTRAINT pk_bula PRIMARY KEY (id)
);