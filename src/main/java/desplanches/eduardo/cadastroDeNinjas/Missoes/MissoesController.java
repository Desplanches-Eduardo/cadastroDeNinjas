package desplanches.eduardo.cadastroDeNinjas.Missoes;

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
    public ResponseEntity<String> criarMissao(@RequestBody MissoesDTO missao){
        MissoesDTO missaoCriada = missoesService.criarMissao(missao);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Missão criada com sucesso: " + missaoCriada.getNome() + "(ID): " + missaoCriada.getId());
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MissoesDTO>> listarMissoes(){
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        return ResponseEntity.ok(missoes);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarMissoesPorId(@PathVariable Long id){
        MissoesDTO missoes =  missoesService.listarMissoesPorId(id);
        if(missoes != null){
            return ResponseEntity.ok(missoes);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão não encontrada. Missão com o ID: " + id + " não existe em nossos registros" );
        }
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterarMissoes(@PathVariable Long id, @RequestBody MissoesDTO missaoAlterada){
        MissoesDTO missao = missoesService.alterarMissoes(id, missaoAlterada);
        if (missao != null){
            return ResponseEntity.ok(missao);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão não encontrada. Missão com o ID: " + id + " não existe em nossos registros");
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarMissao(@PathVariable Long id){
        if (listarMissoesPorId(id)!= null){
            missoesService.deletarMissao(id);
            return ResponseEntity.ok("A missçao com ID " + id + " foi deletada com sucesso");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão não encontrada. Missão com o ID:" + id +" não existe em nossos registros");
        }
    }
}
