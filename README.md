# cadastroDeNinjas

Projeto de estudo de API REST com Spring Boot, para praticar modelagem de banco, migração
versionada, separação em camadas e documentação das rotas. As duas entidades, os repositórios,
os endpoints de CRUD de ninjas e de missões, as telas em Thymeleaf e a documentação no Swagger
já estão de pé. Falta escrever teste de verdade.

## Como rodar

O `application.properties` lê a configuração do banco de variáveis de ambiente, sem valor
padrão. Sem elas o app não sobe, e o erro é `Driver org.h2.Driver claims to not accept jdbcUrl,
${DATABASE_URL}`. Com um H2 em memória:

    export DATABASE_URL='jdbc:h2:mem:testdb'
    export DATABASE_USERNAME=sa
    export DATABASE_PASSWORD=''
    ./mvnw spring-boot:run

Sobe na porta 8080. O console do H2 fica em `http://localhost:8080/h2-console`.

O banco nasce vazio: as migrações criam as tabelas, não os ninjas. O caminho mais curto para
ter dado é a tela de cadastro (`/ninjas/ui/adicionar` e `/missoes/ui/adicionar`); os `POST` da
API e o console do H2 também servem.

## Documentação das rotas

O springdoc-openapi (`3.1.1`) lê as anotações do controller e monta a documentação sozinho, sem
arquivo à parte para manter em sincronia. Com o app rodando:

- `http://localhost:8080/swagger-ui/index.html` é a interface, com as rotas agrupadas e o botão
  `Try it out` para disparar a requisição dali mesmo.
- `http://localhost:8080/v3/api-docs` é o JSON no formato OpenAPI, que é a fonte da interface.

Cada rota tem `@Operation` com o resumo e a explicação, `@ApiResponses` com os códigos esperados
e `@Parameter` nos parâmetros de caminho e de corpo.

As respostas das rotas de missão ficaram copiadas das de ninja: o `@ApiResponse` de sucesso do
`POST /missoes/criar` fala em "Ninja criado com sucesso", e o `GET /missoes/listar` promete `400`
para um erro que na verdade é de criação. É só texto, a rota funciona.

## Endpoints

As duas entidades têm as mesmas cinco rotas, com os mesmos nomes: `POST /criar` cadastra,
`GET /listar` lista tudo, `GET /listar/{id}` busca por id, `PUT /alterar/{id}` altera e
`DELETE /deletar/{id}` remove. Em ninja elas ficam sob `/ninjas` e em missão sob `/missoes`.

| Método | Rota | O que faz |
| --- | --- | --- |
| POST | `/ninjas/criar` | Cadastra um ninja a partir do corpo JSON |
| GET | `/ninjas/listar` | Lista todos os ninjas |
| GET | `/ninjas/listar/{id}` | Busca um ninja pelo id |
| PUT | `/ninjas/alterar/{id}` | Altera um ninja |
| DELETE | `/ninjas/deletar/{id}` | Remove um ninja |
| POST | `/missoes/criar` | Cadastra uma missão (o corpo precisa do campo `id`, com `0`) |
| GET | `/missoes/listar` | Lista todas as missões |
| GET | `/missoes/listar/{id}` | Busca uma missão pelo id |
| PUT | `/missoes/alterar/{id}` | Altera uma missão |
| DELETE | `/missoes/deletar/{id}` | Remove uma missão |

Os `POST` respondem `201` com uma mensagem dizendo o id criado e os `GET` por id respondem `404`
quando o id não existe. Nos `DELETE`, só o de ninja responde `404`: o de missão responde `200`
com a mensagem de sucesso mesmo para um id que nunca existiu.

## Telas

As telas usam Thymeleaf e cobrem o ciclo inteiro, não só a listagem. Em ninja elas ficam sob
`/ninjas/ui` e em missão sob `/missoes/ui`, com as mesmas rotas dos dois lados:

- `GET /listar` mostra a tabela, com os botões de detalhes, alterar e deletar em cada linha.
- `GET /detalhes/{id}` abre a ficha do registro.
- `GET /adicionar` abre o formulário vazio.
- `POST /salvar` grava um registro novo.
- `GET /alterar/{id}` abre o mesmo formulário com o registro carregado.
- `POST /alterar/{id}` grava a alteração.
- `GET /deletar/{id}` remove em ninja e `POST /deletar/{id}` remove em missão, que faz a remoção
  por um formulário.

As duas listagens têm um botão que leva à listagem da outra entidade.

O CSS de cada tela está no `<style>` do próprio template, sem arquivo externo e sem framework:
fundo escuro, cartões claros e um acento em roxo.

Os controllers de view são `@Controller`, e não `@RestController`: o retorno é o nome do
template, que o Spring resolve dentro de `templates/`. Com `@RestController` o texto virava o
corpo da resposta, e a página não renderizava.

O `<title>` do formulário de ninja se apoia em `ninja.id != null` para escolher entre
`Adicionar` e `Alterar`. O de missão usa `missao.id > 0`, que funciona porque um `MissoesDTO`
novo nasce com `id` igual a zero (o campo é `long`, não `Long`).

As fotos ficam em `src/main/resources/static/img/`, servidas pelo próprio app, e a coluna
`img_url` guarda o caminho delas (`/img/naruto.png`). São nove retratos tirados do infobox de
cada personagem na Naruto Fandom Wiki, material com copyright, que está aqui só como dado de
teste. A migração `V3` aponta o `img_url` dos ninjas que já estão cadastrados.

## Banco e migrações

Duas entidades JPA: `NinjaModel` (tabela `tb_cadastro`, com nome, email, idade, rank e imagem na
coluna `img_url`) e `MissoesModel` (tabela `tb_missoes`, com nome e dificuldade). Um ninja
aponta para uma missão (`@ManyToOne`, chave estrangeira `missoes_id`) e uma missão reúne vários
ninjas (`@OneToMany`). O `@JsonIgnore` na lista de ninjas evita a serialização circular entre
as duas entidades.

O schema é versionado com Flyway, em `db/migrations`:

- `V1` cria `tb_missoes` e `tb_cadastro`.
- `V2` acrescenta a coluna `rank`.
- `V3` corrige o `img_url` dos ninjas já cadastrados para o caminho das imagens do projeto.

Os dois `Repository` estendem `JpaRepository`, então o CRUD básico vem pronto do Spring Data. Os
`Service` chamam o repositório e convertem entre entidade e DTO pelo `Mapper`. `NinjaControllerUi`
e `MissoesControllerUI` são a camada de view.

## O que falta

Teste: fora o `contextLoads` que o Spring Initializr gera, não há nenhum.

`POST /missoes/criar` só aceita corpo com o campo `id` dentro, e com o valor `0`. Sem o campo a
resposta é `400` ("Cannot map `null` into type `long`") e com um id diferente de zero é `500`.
A causa do `400` é o `id` do `MissoesDTO` ser `long` primitivo, que o Jackson recusa preencher
com nulo; no `NinjaDTO` o campo é `Long`, e por isso `POST /ninjas/criar` aceita um corpo só com
nome, email, idade e rank. Trocar o tipo em `MissoesDTO` resolve.

`DELETE /missoes/deletar/{id}` responde `200` e a mensagem de sucesso mesmo quando o id não
existe, porque o controller compara o `ResponseEntity` que ele mesmo devolveu em vez do DTO
encontrado, e esse objeto nunca é nulo.

`DELETE /missoes/deletar/{id}` com ninjas vinculados devolve `500`: a chave estrangeira em
`tb_cadastro` segura a remoção e a resposta é o erro de integridade cru. A tela de missões checa
antes de chamar e mostra um aviso no lugar.

Os dois `PUT` da API gravam a entidade inteira, então mandar um campo só zera os outros. A tela
de alteração de ninja contorna isso repondo a missão atual antes de salvar, mas a rota continua
sem aceitar alteração parcial.

## Decisões

Lombok nas entidades (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) para não escrever
getter e setter na mão.

O `ddl-auto` está em `validate`: quem cria e altera tabela é a migração, o Hibernate só confere
se as colunas mapeadas existem. Foi a troca pelo `update`, que nunca remove uma coluna nem
corrige o tipo de outra sozinho.

O `email` é `unique` no banco, então dois ninjas com o mesmo email não entram. O erro chega como
`500` na resposta, sem tratamento.

As imagens moram no projeto, em vez de o `img_url` apontar para a URL da wiki. Link externo fica
dependendo do CDN de terceiro responder, e o da Fandom ainda recusa pedido que não pareça
navegador; baixar os arquivos uma vez deixou a tela independente disso.

As anotações do Swagger ficam no controller, junto da rota que descrevem. Um arquivo OpenAPI
separado descreveria as mesmas rotas duas vezes e sairia de sincronia no primeiro método novo.
