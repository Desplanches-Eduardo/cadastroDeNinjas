package desplanches.eduardo.cadastroDeNinjas.Missoes;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/missoes")
public class MissoesController {
    @PostMapping("/criar")
    public String criarMisssao(){
        return "Missao criada com sucesso";
    }
    @GetMapping("/listar")
    public String listarMissoes(){
        return "Missoes listadas com sucesso";
    }
    @GetMapping("/listarid")
    public String missoesId(){
        return "listar missoes por Id";
    }
    @PutMapping("/alterar")
    public String alterarMissoes(){
        return "missão alterada com sucesso";
    }
    @DeleteMapping("/deletar")
    public String deletarMissao(){
        return "missao deletada com sucesso";
    }
}
