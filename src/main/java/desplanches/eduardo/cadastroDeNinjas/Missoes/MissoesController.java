package desplanches.eduardo.cadastroDeNinjas.Missoes;

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
    public MissoesDTO criarMissao(@RequestBody MissoesDTO missao){
        return missoesService.criarMissao(missao);
    }

    @GetMapping("/listar")
    public List<MissoesDTO> listarMissoes(){return missoesService.listarMissoes();}

    @GetMapping("/listar/{id}")
    public MissoesDTO listarMissoesPorId(@PathVariable Long id){
        return missoesService.listarMissoesPorId(id);
    }

    @PutMapping("/alterar/{id}")
    public MissoesDTO alterarMissoes(@PathVariable Long id, @RequestBody MissoesDTO missaoAlterada){return missoesService.alterarMissoes(id, missaoAlterada);}

    @DeleteMapping("/deletar/{id}")
    public void deletarMissao(@PathVariable Long id){missoesService.deletarMissao(id);}
}
