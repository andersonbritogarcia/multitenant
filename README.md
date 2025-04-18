# README - Aplicação Monolítica Modular com Multitenant

## Descrição do Projeto

Este projeto é uma aplicação **monolítica modular multitenant** desenvolvida em **Java** utilizando o **Spring Boot** e o **Spring Modulith** para organizar os módulos internos. A aplicação é projetada para gerenciar diferentes entidades, como usuários, locatários (tenants) e pets, com suporte a autenticação e autorização via **Spring Security** e **JWT**. O banco de dados utilizado é o **PostgreSQL**, e a aplicação é containerizada com **Docker** para facilitar a execução e o deploy.

A aplicação faz uso do multitenant por `shared schema`, onde as rotinas administrativas como Tenants e User são mantidas no `schema admin` e a rotina de **Pet** faz uso exclusivo do `schema do tenant`.

### Principais Tecnologias e Ferramentas
- **Java 21**: Linguagem de programação principal.
- **Spring Boot**: Framework para desenvolvimento rápido de aplicações.
- **Spring Modulith**: Organização modular dentro de um monólito.
- **Spring Security**: Gerenciamento de autenticação e autorização.
- **Spring Data JPA**: Abstração para acesso ao banco de dados.
- **PostgreSQL**: Banco de dados relacional.
- **Docker** e **Docker Compose**: Containerização e orquestração.
- **Maven**: Gerenciamento de dependências e build.
- **JUnit 5** e **Mockito**: Testes unitários e mocks.

---

## Estrutura Modular

A aplicação é dividida em módulos independentes, cada um responsável por uma funcionalidade específica:

1. **IAM (Identity and Access Management)**:
   - Gerencia usuários, locatários (tenants) e permissões.
   - Implementa autenticação e autorização com JWT.
   
2. **Pet**:
   - Gerencia informações sobre pets e adoções.
   - Exemplo de funcionalidade: Cadastro e listagem de pets.

3. **Shared**:
   - Contém classes e utilitários compartilhados entre os módulos, como exceções e configurações globais.

---

## Configuração e Execução

### Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:
- **Java 21** ou superior.
- **Maven** (para build e gerenciamento de dependências).
- **Docker** e **Docker Compose** (para execução em contêineres).

### Configuração do Banco de Dados

O banco de dados utilizado é o **PostgreSQL**, configurado no arquivo `application.properties`:

```ini
spring.datasource.url=jdbc:postgresql://postgres:5432/db
spring.datasource.username=postgres
spring.datasource.password=masterkey
spring.datasource.driver-class-name=org.postgresql.Driver
```

O banco de dados será inicializado automaticamente com o Docker Compose.

### Passos para Execução

#### 1. Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

#### 2. Construir o Projeto
```bash
mvn clean install
```

#### 3. Executar com Docker Compose
```bash
docker-compose up --build
```

Isso iniciará os serviços:
- **PostgreSQL**: Disponível na porta `5432`.
- **Aplicação**: Disponível na porta `8080`.

#### 4. Acessar a Aplicação
- Para teste da aplicação o Swagger está disponível em: `http://localhost:8080/swagger-ui/index.html`.
- O login pode ser realizado utilizando as credencias: 
* **email:** `anderson@email.com` 
* **password:** `01`
- Será devolvido um token que pode ser utilizado para autenticação.
---

## Segurança

A aplicação utiliza **JWT** para autenticação e autorização. As chaves pública e privada para geração e validação dos tokens estão configuradas no arquivo `application.properties`:
```ini
jwt.public.key=classpath:app.pub
jwt.private.key=classpath:app.key
```

Certifique-se de que as chaves estão corretamente configuradas antes de executar a aplicação.

---

## Testes

Os testes unitários são implementados com **JUnit 5** e **Mockito**. Para executar os testes, utilize o comando:
```bash
mvn test
```

---

## Estrutura de Arquivos

- `src/main/java`: Código-fonte principal.
- `src/main/resources`: Arquivos de configuração, como `application.properties`.
- `src/test/java`: Testes unitários.
- `Dockerfile`: Configuração do contêiner da aplicação.
- `docker-compose.yml`: Orquestração dos serviços Docker.

---

## Contribuição

Contribuições são bem-vindas! Siga os passos abaixo para contribuir:
1. Faça um fork do repositório.
2. Crie uma branch para sua feature/bugfix: `git checkout -b minha-feature`.
3. Faça commit das suas alterações: `git commit -m "Minha feature"`.
4. Envie para o repositório remoto: `git push origin minha-feature`.
5. Abra um Pull Request.

---

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).