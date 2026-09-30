package desplanches.eduardo.cadastroDeNinjas.Ninjas;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ninjas")

public class NinjaController {

    @PostMapping("/adicionar")
    public String criarNinja(){
        return "Ninja criado com sucesso";
    }

    @GetMapping("/listar")
    public String listarTodos(){
        return "Ninjas listados com sucesso";
    }

    @GetMapping("/listarid")
    public String listarId(){
        return "Ninja listado por id com sucesso";
    }

   @PutMapping("/alterar")
    public String alterarNinja(){
        return "Ninja alterado com sucesso";
   }

   @DeleteMapping("/Deleta")
    public String deletarNinja(){
        return "Ninja deletado com sucesso";
   }


}
