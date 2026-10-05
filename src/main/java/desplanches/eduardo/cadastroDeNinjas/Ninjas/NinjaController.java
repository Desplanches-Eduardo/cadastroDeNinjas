package desplanches.eduardo.cadastroDeNinjas.Ninjas;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ninjas")

public class NinjaController {

    private NinjaService ninjaService;

    @PostMapping("/criar")
    public String criarNinja(){
        return "Ninja criado com sucesso";
    }

    @GetMapping("/listar")
    public List<NinjaModel> listarNinjas(){
        return ninjaService.listarNinjas();
    }

    @GetMapping("/listarid")
    public String listarId(){
        return "Ninja listado por id com sucesso";
    }

   @PutMapping("/alterar")
    public String alterarNinja(){
        return "Ninja alterado com sucesso";
   }

   @DeleteMapping("/deletar")
    public String deletarNinja(){
        return "Ninja deletado com sucesso";
   }


}
