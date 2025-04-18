CREATE SCHEMA IF NOT EXISTS admin;
CREATE TABLE IF NOT EXISTS admin.tenant
(
    id      uuid NOT NULL,
    name    character varying(100) NOT NULL,
    schema  character varying(100) NOT NULL,
    CONSTRAINT pk_tenant PRIMARY KEY (id),
    CONSTRAINT uk_tenant_name UNIQUE (name),
    CONSTRAINT uk_tenant_schema UNIQUE (schema)
);

CREATE TABLE IF NOT EXISTS admin."user"
(
    id        uuid NOT NULL,
    email     character varying(100) NOT NULL,
    name      character varying(100) NOT NULL,
    password  character varying(256) NOT NULL,
    role      character varying(10) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uk_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS admin.user_tenant
(
    user_id         uuid NOT NULL,
    tenant_id       uuid NOT NULL,
    default_tenant  boolean DEFAULT false,
    CONSTRAINT pk_user_tenant PRIMARY KEY (user_id, tenant_id),
    CONSTRAINT fk_user_tenant_user FOREIGN KEY (user_id) REFERENCES admin."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_user_tenant_tenant FOREIGN KEY (tenant_id) REFERENCES admin.tenant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

DELETE FROM admin.user_tenant;
DELETE FROM admin."user";
DELETE FROM admin.tenant;

INSERT INTO admin.tenant(id, name, schema) VALUES ('01960762-41da-7252-8046-ebeea203a45e', 'AP 503', 'ap503');
INSERT INTO admin.tenant(id, name, schema) VALUES ('01960762-41da-7592-9985-ba4e888ab8cf', 'AP 711', 'ap711');


-- ################### AP 503
INSERT INTO admin."user"(id, password, email, name, role)
VALUES ('01960762-41da-7f92-b176-b75ba3b7d2a3', '$2a$10$g7swg4PRzaJ0/p5.BisFz.5O1knKfGeL0khLDKJ62Y6ZmvO9dQjte',
        'anderson@email.com', 'Anderson', 'ADMIN');

INSERT INTO admin."user"(id, password, email, name, role)
VALUES ('01964891-2363-75bd-ae80-b21deda31c28', '$2a$10$g7swg4PRzaJ0/p5.BisFz.5O1knKfGeL0khLDKJ62Y6ZmvO9dQjte',
        'yasmine@email.com', 'Yasmine', 'RESIDENT');

INSERT INTO admin.user_tenant(user_id, tenant_id, default_tenant) VALUES ('01960762-41da-7f92-b176-b75ba3b7d2a3','01960762-41da-7252-8046-ebeea203a45e', true);
INSERT INTO admin.user_tenant(user_id, tenant_id, default_tenant) VALUES ('01964891-2363-75bd-ae80-b21deda31c28','01960762-41da-7252-8046-ebeea203a45e', true);


-- ################### AP 711
INSERT INTO admin."user"(id, password, email, name, role)
VALUES ('01960762-41da-7ee7-abf2-e8669d7fb168', '$2a$10$g7swg4PRzaJ0/p5.BisFz.5O1knKfGeL0khLDKJ62Y6ZmvO9dQjte',
        'robson@email.com', 'Robson', 'RESIDENT');

INSERT INTO admin.user_tenant(user_id, tenant_id, default_tenant) VALUES ('01960762-41da-7ee7-abf2-e8669d7fb168','01960762-41da-7592-9985-ba4e888ab8cf', true);

-- SCHEMA AP 503
CREATE SCHEMA IF NOT EXISTS ap503;
CREATE TABLE IF NOT EXISTS ap503.pet
(
    id        uuid NOT NULL,
    name      character varying(60) NOT NULL,
    category  character varying(10) NOT NULL,
    CONSTRAINT pk_pet PRIMARY KEY (id)
);

DELETE FROM ap503.pet;
INSERT INTO ap503.pet(id, name, category) VALUES ('019612f9-3e1e-70e6-97e8-5a945e176d2c', 'Spooke', 'CAT');
INSERT INTO ap503.pet(id, name, category) VALUES ('019612f9-3e1e-7334-b730-1bafaa80de8d', 'Karino', 'CAT');

-- SCHEMA AP 711
CREATE SCHEMA IF NOT EXISTS ap711;
CREATE TABLE IF NOT EXISTS ap711.pet
(
    id            uuid NOT NULL,
    name      character varying(60) NOT NULL,
    category  character varying(10) NOT NULL,
    CONSTRAINT pk_pet PRIMARY KEY (id)
);

DELETE FROM ap711.pet;
INSERT INTO ap711.pet(id, name, category) VALUES ('019612f9-3e1e-7bff-b96f-bb328db9ec96', 'Roberval', 'DOG');