# Supermercado Penitenciária

Sistema web para gestão de pedidos de um supermercado que atende detentos de uma unidade penitenciária. Clientes cadastrados fazem pedidos de produtos para um detento, respeitando o limite de quantidade de cada categoria, e o atendente acompanha o pedido até a entrega na ala ou pavilhão.

## Funcionalidades

- **Categorias** — cadastro com limite de quantidade por pedido
- **Produtos** — cadastro vinculado a uma categoria
- **Clientes** — cadastro de quem faz os pedidos
- **Detentos** — cadastro com localização (ala ou pavilhão)
- **Pedidos** — montagem, controle de limite por categoria, acompanhamento de status (pagamento, entrega, recusa) e histórico
- **Painel inicial** — estatísticas dos pedidos em andamento
- **Login e perfis** — `ATENDENTE` e `ADMINISTRADOR` (este também gerencia usuários)

## Tecnologias

- Java 21+
- Spring Boot (Web MVC, Data JPA, Security, Validation)
- Thymeleaf
- MySQL
- Maven

## Arquitetura

Monolito MVC em camadas:

```
controller → service (interfaces) / impl → repository (Spring Data JPA) → MySQL
```

As telas são renderizadas no servidor com Thymeleaf (`src/main/resources/templates`).

## Como rodar

1. Tenha o **JDK 21 ou superior** e o **MySQL** instalados.
2. Crie o banco:
   ```sql
   CREATE DATABASE supermercado_penitenciaria_db;
   ```
3. Defina a senha do MySQL na variável de ambiente `DB_PASSWORD` (o usuário padrão é `root`, configurado em `application.properties`).
4. Na raiz do projeto:
   ```bash
   ./mvnw spring-boot:run      # Linux/macOS
   .\mvnw.cmd spring-boot:run  # Windows
   ```
5. Acesse http://localhost:8080.

As tabelas são criadas automaticamente pelo Hibernate. Para popular o banco com dados de exemplo, execute o script `seed-dados-teste.sql`.

> O primeiro usuário administrador precisa ser inserido diretamente no banco, com a senha criptografada em BCrypt.
