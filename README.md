# Aplicação Monolítica Modular com Multitenant

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
   
4. **infrastructure**:
   - Abstrai e gerencia detalhes técnicos relacionados à infraestrutura do sistema, como multitenancy e autenticação
   
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
git clone https://github.com/andersonbritogarcia/multitenant.git
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
## Classes responsáveis pela arquitetura multitenancy

`TenantContext`

**Responsabilidade:** Gerenciar o contexto do tenant (esquema do banco de dados) em nível de thread.

- Armazena o esquema do tenant atual usando `ThreadLocal`.
- Permite definir, obter e limpar o esquema do tenant.
- Integra com o MDC (Mapped Diagnostic Context) para logging.

**Relação:** É usado por outras classes, como `JwtAuthenticationTenantFilter` e `TenantResolver`, para definir e recuperar o esquema do tenant.

---

`TenantResolver`

**Responsabilidade:** Resolver o identificador do tenant atual para o Hibernate.

- Implementa `CurrentTenantIdentifierResolver` para fornecer o esquema do tenant ao Hibernate.
- Define o esquema padrão como `"admin"` caso nenhum tenant esteja configurado.

**Relação:** Depende do `TenantContext` para obter o esquema do tenant atual. É configurado no Hibernate para suportar multitenancy.

---

`JwtAuthenticationTenantFilter`

**Responsabilidade:** Filtrar requisições HTTP e configurar o esquema do tenant com base no JWT.

- Verifica se a URL é administrativa ou se o token JWT está presente.
- Decodifica o JWT para extrair o tenant e valida o acesso do usuário ao tenant.
- Configura o esquema do tenant no `TenantContext`.

**Relação:**  
- Usa o `TenantContext` para definir o esquema do tenant.
- Trabalha com o `IamApi` para validar o tenant e o acesso do usuário.
- Atua como um ponto inicial para configurar o tenant antes que a requisição seja processada.

---

`ConnectionProvider`

**Responsabilidade:**  Gerenciar conexões com o banco de dados para diferentes tenants.

- Implementa `MultiTenantConnectionProvider` para fornecer conexões configuradas com o esquema do tenant.
- Define o esquema do tenant na conexão antes de usá-la.

**Relação:**  
- Trabalha em conjunto com o `TenantResolver` para obter o esquema do tenant atual.
- É configurado no Hibernate para fornecer conexões apropriadas para cada tenant.

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