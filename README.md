# cadastroDeNinjas

Projeto de estudo de API REST com Spring Boot, para praticar modelagem de banco, migração
versionada e a separação em camadas. As duas entidades, os repositórios e os endpoints de CRUD
dos ninjas e das missões já estão de pé, junto com duas telas de listagem em Thymeleaf; faltam
alguns ajustes e os testes.

## Como rodar

O `application.properties` lê a configuração do banco de variáveis de ambiente, sem valor
padrão. Sem elas o app não sobe, e o erro é `Driver org.h2.Driver claims to not accept jdbcUrl,
${DATABASE_URL}`. Com um H2 em memória:

    export DATABASE_URL='jdbc:h2:mem:testdb'
    export DATABASE_USERNAME=sa
    export DATABASE_PASSWORD=''
    ./mvnw spring-boot:run

Sobe na porta 8080. O console do H2 fica em `http://localhost:8080/h2-console`.

O banco nasce vazio: as migrações criam as tabelas, não os ninjas. Para a tela mostrar alguma
coisa, cadastre pelo menos um ninja e uma missão pelos `POST` de `/ninjas/criar` e
`/missoes/criar`, ou insira direto pelo console do H2.

## Endpoints

As duas entidades têm as mesmas cinco rotas, com os mesmos nomes: `POST /criar` cadastra,
`GET /listar` lista tudo, `GET /listar/{id}` busca por id, `PUT /alterar/{id}` altera e
`DELETE /deletar/{id}` remove. Em ninja elas ficam sob `/ninjas` e em missão sob `/missoes`.

## Telas

Duas telas de listagem com Thymeleaf, uma por entidade: `GET /ninjas/ui/listar` e
`GET /missoes/ui/listar`, que renderizam `templates/Ninjas/listarNinjas.html` e
`templates/Missoes/listarMissoes.html`. São só leitura — cadastro e alteração continuam só pela
API — e ainda não tem CSS, então é tabela crua do navegador.

Os controllers de view são `@Controller`, e não `@RestController`: o retorno é o nome do
template, que o Spring resolve dentro de `templates/`. Com `@RestController` o texto virava o
corpo da resposta, e a página não renderizava.

As fotos ficam em `src/main/resources/static/img/`, servidas pelo próprio app, e a coluna
`img_url` guarda o caminho delas (`/img/naruto.png`). São nove retratos tirados do infobox de
cada personagem na Naruto Fandom Wiki — material com copyright, que está aqui só como dado de
teste. A migração `V3` aponta o `img_url` dos ninjas que já estão cadastrados.

## O que já está pronto

Duas entidades JPA: `NinjaModel` (tabela `tb_cadastro`, com nome, email, idade, rank e imagem na
coluna `img_url`) e `MissoesModel` (tabela `tb_missoes`, com nome e dificuldade). Um ninja
aponta para uma missão (`@ManyToOne`, chave estrangeira `missoes_id`) e uma missão reúne vários
ninjas (`@OneToMany`). O `@JsonIgnore` na lista de ninjas evita a serialização circular entre
as duas entidades.

Os dois `Repository` estendem `JpaRepository`, então o CRUD básico vem pronto do Spring Data.
Os `Service` chamam o repositório e os `Controller` expõem as rotas. `NinjaControllerUi` e
`MissoesControllerUI` são a camada de view. Os dois `POST` respondem `201` com uma mensagem
dizendo o id criado, e os dois `DELETE` respondem `404` quando o id não existe.

## O que falta

`MissoesService.alterarMissoes` salva a alteração e devolve `null` de qualquer jeito, porque o
`return` ficou fora do `if`. O `PUT /missoes/alterar/{id}` responde vazio mesmo tendo gravado.

Os dois `PUT` só aceitam a entidade inteira: mandar um campo só zera os outros, então falta a
alteração parcial.

O CSS das telas e qualquer formulário de cadastro: hoje só dá para inserir pela API ou pelo
console do H2.

Fora o `contextLoads` que o Spring Initializr gera, não há teste.

## Decisões

Lombok nas entidades (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) para não escrever
getter e setter na mão.

O schema é versionado com Flyway (`db/migrations`) e o `ddl-auto` está em `validate`: quem cria
e altera tabela é a migração, o Hibernate só confere se as colunas mapeadas existem. Foi a troca
pelo `update`, que nunca remove uma coluna nem corrige o tipo de outra sozinho.

O `email` é `unique` no banco, então dois ninjas com o mesmo email não entram.

As imagens moram no projeto, em vez de o `img_url` apontar para a URL da wiki. Link externo fica
dependendo do CDN de terceiro responder, e o da Fandom ainda recusa pedido que não pareça
navegador — baixar os arquivos uma vez deixou a tela independente disso.
