package desplanches.eduardo.cadastroDeNinjas.Missoes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import desplanches.eduardo.cadastroDeNinjas.Ninjas.NinjaModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")

public class MissoesController {

    private final MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Cria uma nova missão", description = "Essa rota cria uma nova missão e insere no banco de dados")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Ninja criado com sucesso" ),
            @ApiResponse(responseCode = "400", description = "Erro na criação do ninja" ),
    })
    public ResponseEntity<String> criarMissao(@RequestBody MissoesDTO missao){
        MissoesDTO missaoCriada = missoesService.criarMissao(missao);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Missão criada com sucesso: " + missaoCriada.getNome() + "(ID): " + missaoCriada.getId());
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista todas as missões", description = "Essa rota lista todas as missões do banco de dados")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Lista todas as missões em JSON" ),
            @ApiResponse(responseCode = "400", description = "Erro na criação do ninja" ),
    })
    public ResponseEntity<List<MissoesDTO>> listarMissoes(){
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        return ResponseEntity.ok(missoes);
    }


    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista a missão por id", description = "Essa rota lista uma missão pelo seu id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Missão encontrada com sucesso" ),
            @ApiResponse(responseCode = "404", description = "Missão não encontrado." ),
    })
    public ResponseEntity<?> listarMissoesPorId(
            @Parameter(description = "Usuário manda id no caminho da requisição")
            @PathVariable Long id){
        MissoesDTO missoes =  missoesService.listarMissoesPorId(id);
        if(missoes != null){
            return ResponseEntity.ok(missoes);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão não encontrada. Missão com o ID: " + id + " não existe em nossos registros" );
        }
    }

    @PutMapping("/alterar/{id}")
    @Operation(summary = "Altera a missão por id", description = "Essa rota altera uma missão pelo seu id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Missão alterada com sucesso" ),
            @ApiResponse(responseCode = "404", description = "Missão não encontrado." ),
    })
    public ResponseEntity<?> alterarMissoes(
            @Parameter(description = "Usuário manda id no caminho da requisição")
            @PathVariable Long id,
            @Parameter(description = "Usuário manda os dados do ninja a ser atualizado no corpo da requisição")
            @RequestBody MissoesDTO missaoAlterada){
        MissoesDTO missao = missoesService.alterarMissoes(id, missaoAlterada);
        if (missao != null){
            return ResponseEntity.ok(missao);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão não encontrada. Missão com o ID: " + id + " não existe em nossos registros");
        }
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta a missão por id", description = "Essa rota deleta uma missão pelo seu id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Missão deletada com sucesso" ),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada." ),
            @ApiResponse(responseCode = "409", description = "Missão com ninjas vinculados" ),
    })
    public ResponseEntity<String> deletarMissao(
            @Parameter(description = "Usuário manda id no caminho da requisição")
            @PathVariable Long id){
        MissoesDTO missao = missoesService.listarMissoesPorId(id);
        if (missao == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão não encontrada. Missão com o ID: " + id + " não existe em nossos registros");
        }
        // A chave estrangeira em tb_cadastro.missoes_id segura a remoção: sem essa checagem
        // o banco devolve erro de integridade e a resposta sai 500.
        List<NinjaModel> ninjas = missao.getNinjas();
        if (ninjas != null && !ninjas.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Missão não deletada: " + ninjas.size() + " ninja(s) ainda estão vinculados a ela");
        }
        missoesService.deletarMissao(id);
        return ResponseEntity.ok("A missão com ID " + id + " foi deletada com sucesso");
    }
}
