package desplanches.eduardo.cadastroDeNinjas.Ninjas;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping

public class NinjaController {

    @GetMapping("/boasvindas")
    public String boasVindas(){
        return "Essa é minha primeira mensagem nessa rota";
    }

    @PostMapping("/adicionar")
    public String criarNinja(){
        return "Ninja criado com sucesso";
    }

    @GetMapping("/todosninjas")
    public String mostrarTodos(){
        return "Mostra todos os ninjas";
    }

    @GetMapping("/ninjasid")
    public String ninjasId(){
        return "Mostrar ninja por id";
    }

   @PutMapping("/alterarid")
    public String alterarNinja(){
        return "Alterar ninja por id";
   }

   @DeleteMapping("/Deletaidr")
    public String deletarNinja(){
        return "Ninja deletado por Id";
   }


}
