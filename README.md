# cadastroDeNinjas

Projeto de estudo de API REST com Spring Boot, para praticar modelagem de banco e a separação
em camadas. Está em construção: as duas entidades estão prontas, os endpoints ainda não.

## Como rodar

O `application.properties` lê a configuração do banco de variáveis de ambiente, sem valor
padrão. Sem elas o app não sobe, e o erro é `Driver org.h2.Driver claims to not accept jdbcUrl,
${DATABASE_URL}`. Com um H2 em memória:

    export DATABASE_URL='jdbc:h2:mem:testdb'
    export DATABASE_USERNAME=sa
    export DATABASE_PASSWORD=''
    ./mvnw spring-boot:run

Sobe na porta 8080. O único endpoint até agora é o `GET /boasvindas`. O console do H2 fica em
`http://localhost:8080/h2-console`.

## O que já está pronto

Duas entidades JPA: `NinjaModel` (tabela `tb_cadastro`, com nome, email, idade e imagem) e
`MissoesModel` (tabela `tb_missoes`, com nome e dificuldade). A relação é um ninja para uma
missão (`@ManyToOne`) e uma missão para vários ninjas (`@OneToMany`), com a chave estrangeira
`missoes_id` na tabela do ninja.

Os dois `Repository` estendem `JpaRepository`, então o CRUD básico vem pronto do Spring Data.

## O que falta

`NinjaService`, `MissoesController` e `MissoesService` estão vazios e nenhum endpoint de CRUD
foi escrito, então ainda não dá para cadastrar ninja pela API.

## Decisões

Lombok nas entidades (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) para não escrever
getter e setter na mão.

`ddl-auto=update`: o Hibernate cria e altera as tabelas a partir das entidades, o que serve bem
enquanto o modelo muda toda semana. Em produção isso pede migração versionada (Flyway), porque
o `update` nunca remove nem corrige uma coluna sozinho.

O `email` é `unique` no banco, então dois ninjas com o mesmo email não entram.
