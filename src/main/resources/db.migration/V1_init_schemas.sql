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

insert into ey.bula (id, descricao, contra_indicacoes) values ('0195abbe-605c-7ae0-9cef-c3a06acd0d4a', 'doril', 'suspeita de dengue');
insert into ey.bula (id, descricao, contra_indicacoes) values ('0195abbe-e80c-78ec-a3d1-e319c1235ae7', 'alegra', 'gravidas');

CREATE TABLE eurofarma.bula
(
    id                UUID NOT NULL,
    descricao         VARCHAR(255) NOT NULL,
    contra_indicacoes VARCHAR(255),
    CONSTRAINT pk_bula PRIMARY KEY (id)
);

insert into ey.bula (id, descricao, contra_indicacoes) values ('0195abc0-b51b-7875-8e55-63af3844bc5b', 'omeprazol', 'lactantes');

CREATE TABLE idbrasil.bula
(
    id                UUID NOT NULL,
    descricao         VARCHAR(255) NOT NULL,
    contra_indicacoes VARCHAR(255),
    CONSTRAINT pk_bula PRIMARY KEY (id)
);