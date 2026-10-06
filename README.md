# cadastroDeNinjas

Projeto de estudo de API REST com Spring Boot, para praticar modelagem de banco, migração
versionada e a separação em camadas. As duas entidades, os repositórios e os endpoints de CRUD
dos ninjas e das missões já estão de pé; faltam alguns ajustes e os testes.

## Como rodar

O `application.properties` lê a configuração do banco de variáveis de ambiente, sem valor
padrão. Sem elas o app não sobe, e o erro é `Driver org.h2.Driver claims to not accept jdbcUrl,
${DATABASE_URL}`. Com um H2 em memória:

    export DATABASE_URL='jdbc:h2:mem:testdb'
    export DATABASE_USERNAME=sa
    export DATABASE_PASSWORD=''
    ./mvnw spring-boot:run

Sobe na porta 8080. O console do H2 fica em `http://localhost:8080/h2-console`.

## Endpoints

As duas entidades têm as mesmas cinco rotas, com os mesmos nomes: `POST /criar` cadastra,
`GET /listar` lista tudo, `GET /listar/{id}` busca por id, `PUT /alterar/{id}` altera e
`DELETE /deletar/{id}` remove. Em ninja elas ficam sob `/ninjas` e em missão sob `/missoes`.

## O que já está pronto

Duas entidades JPA: `NinjaModel` (tabela `tb_cadastro`, com nome, email, idade e imagem na
coluna `img_url`) e `MissoesModel` (tabela `tb_missoes`, com nome e dificuldade). Um ninja
aponta para uma missão (`@ManyToOne`, chave estrangeira `missoes_id`) e uma missão reúne vários
ninjas (`@OneToMany`). O `@JsonIgnore` na lista de ninjas evita a serialização circular entre
as duas entidades.

Os dois `Repository` estendem `JpaRepository`, então o CRUD básico vem pronto do Spring Data.
Os `Service` chamam o repositório e os `Controller` expõem as rotas.

## O que falta

`MissoesService.alterarMissoes` salva a alteração e devolve `null` de qualquer jeito, porque o
`return` ficou fora do `if`. O `PUT /missoes/alterar/{id}` responde vazio mesmo tendo gravado.

Os dois `PUT` só aceitam a entidade inteira: mandar um campo só zera os outros, então falta a
alteração parcial.

A coluna `rank` existe na tabela (migração `V2`) mas não existe no `NinjaModel`, então não dá
para ler nem gravar rank pela API.

Fora o `contextLoads` que o Spring Initializr gera, não há teste.

## Decisões

Lombok nas entidades (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) para não escrever
getter e setter na mão.

O schema é versionado com Flyway (`db/migrations`) e o `ddl-auto` está em `validate`: quem cria
e altera tabela é a migração, o Hibernate só confere se as colunas mapeadas existem. Foi a troca
pelo `update`, que nunca remove uma coluna nem corrige o tipo de outra sozinho.

O `email` é `unique` no banco, então dois ninjas com o mesmo email não entram.
