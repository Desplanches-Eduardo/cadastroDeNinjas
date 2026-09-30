package desplanches.eduardo.cadastroDeNinjas.Missoes;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MissoesController {
    @PostMapping("/adicionarmissao")
    public String adicionarMisssao(){
        return "adicionar missão";
    }
    @GetMapping("/todasmissoes")
    public String todasMissoes(){
        return "Mostra todas as missoes";
    }
    @GetMapping("/missoesid")
    public String missoesId(){
        return "Mostra missoes por Id";
    }
    @PutMapping("/alterarmissoes")
    public String alterarMissoes(){
        return "Alterar missão por Id";
    }

    @DeleteMapping("/deletarmissao")
    public String deletarMissao(){
        return "Deleta missao por ID";
    }
}
