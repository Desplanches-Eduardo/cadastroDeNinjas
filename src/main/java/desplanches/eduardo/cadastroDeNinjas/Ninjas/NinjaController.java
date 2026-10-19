package desplanches.eduardo.cadastroDeNinjas.Ninjas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ninjas")

public class NinjaController {

    private final NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Cria um novo ninja", description = "Essa rota cria um novo ninja e insere no banco de dados")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Ninja criado com sucesso" ),
            @ApiResponse(responseCode = "400", description = "Erro na criação do ninja" ),
    })
    public ResponseEntity<String> criarNinja(@RequestBody NinjaDTO ninja){

        NinjaDTO ninjaCriado = ninjaService.criarNinja(ninja);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Ninja criado com sucesso: " + ninjaCriado.getNome() + "(ID): " + ninjaCriado.getId());

    }

    @GetMapping("/listar")
    @Operation(summary = "Lista todos os ninjas", description = "Essa rota lista todos os ninjas")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Retorna todos os ninjas" ),
            @ApiResponse(responseCode = "400", description = "Não retorna os ninjas" ),
    })
    public ResponseEntity<List<NinjaDTO>> listarNinjas(){
       List<NinjaDTO> ninjas =  ninjaService.listarNinjas();
       return ResponseEntity.ok(ninjas);
    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista o ninja por id", description = "Essa rota lista um ninja pelo seu id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ninja encontrado com sucesso" ),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado." ),
    })
    public ResponseEntity<?> listarNinjasporId(
            @Parameter(description = "Usuário manda id no caminho da requisição")
            @PathVariable Long id){
        NinjaDTO ninja = ninjaService.listarNinjasporId(id);
        if (ninja != null){
            return ResponseEntity.ok(ninja);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ninja não encontrado. Ninja com o ID: " + id + " não existe em nossos registros" );
        }
    }

   @PutMapping("/alterar/{id}")
   @Operation(summary = "Altera um ninja", description = "Essa rota altera um ninja")
   @ApiResponses(value ={
           @ApiResponse(responseCode = "200", description = "Ninja alterado com sucesso" ),
           @ApiResponse(responseCode = "404", description = "Ninja não encontrado." ),
   })
    public ResponseEntity<?> alterarNinja(
            @Parameter(description = "Usuário manda o id no caminho da requisição")
            @PathVariable Long id,
            @Parameter(description = "Usuário manda os dados do ninja a ser atualizado no corpo da requisição")
            @RequestBody NinjaDTO ninjaAlterado){
       NinjaDTO ninja = ninjaService.alterarNinja(id, ninjaAlterado);
       if (ninja!= null) {
            return ResponseEntity.ok(ninja);
        }else{
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("Ninja não encontrado. Ninja com o ID: " + id + " não existe em nossos registros");
       }
   }

   @DeleteMapping("/deletar/{id}")
   @Operation(summary = "Deleta um ninja", description = "Essa rota deleta o ninja do banco de dados")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Ninja é apagado"),
           @ApiResponse(responseCode = "404", description = "Ninja não encontrado.")
   })
    public ResponseEntity<String> deletarNinja(
            @Parameter(description = "Usuário manda o id no caminho da requisição")
            @PathVariable Long id){
        if (ninjaService.listarNinjasporId(id) != null){
            ninjaService.deletarNinja(id);
            return ResponseEntity.ok("Ninja com ID " + id + " deletado com sucesso");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ninja não encontrado. Ninja com o ID: " + id + " não existe em nossos registros");
        }
   }


}
