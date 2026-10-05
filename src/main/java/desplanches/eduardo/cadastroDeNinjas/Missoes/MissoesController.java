package desplanches.eduardo.cadastroDeNinjas.Missoes;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")

public class MissoesController {

    private MissoesService missoesService;

    @PostMapping("/criar")
    public String criarMisssao(){
        return "Missao criada com sucesso";
    }

    @GetMapping("/listar")
    public List<MissoesModel> listarMissoes(){return missoesService.listarMissoes();}

    @GetMapping("/listar/id")
    public MissoesModel listarMissoesPorId(Long id){
        return missoesService.listarMissoesPorId(id);
    }

    @PutMapping("/alterar")
    public String alterarMissoes(){return "missão alterada com sucesso";}

    @DeleteMapping("/deletar")
    public String deletarMissao(){return "missao deletada com sucesso";}
}
