package desplanches.eduardo.cadastroDeNinjas.Ninjas;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ninjas")

public class NinjaController {

    private NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @PostMapping("/criar")
    public NinjaModel criarNinja(@RequestBody NinjaModel ninja){
        return ninjaService.criarNinja(ninja);
    }

    @GetMapping("/listar")
    public List<NinjaModel> listarNinjas(){
        return ninjaService.listarNinjas();
    }

    @GetMapping("/listar/{id}")
    public NinjaModel listarNinjasporId(@PathVariable Long id){
        return ninjaService.listarNinjasporId(id);
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
