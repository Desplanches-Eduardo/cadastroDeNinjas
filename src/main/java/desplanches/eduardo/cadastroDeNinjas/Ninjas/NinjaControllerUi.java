package desplanches.eduardo.cadastroDeNinjas.Ninjas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ninjas/ui")
public class NinjaControllerUi {

    private final NinjaService ninjaService;

    public NinjaControllerUi(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @GetMapping("/listar")
    public String listarNinjas(Model model) {
        List<NinjaDTO> ninjas = ninjaService.listarNinjas();
        model.addAttribute("ninjas", ninjas);
        return "Ninjas/listarNinjas";
    }

    @GetMapping("/deletar/{id}")
    public String deletarNinja(@PathVariable Long id) {
        ninjaService.deletarNinja(id);
        return "redirect:/ninjas/ui/listar";
    }

    @GetMapping("/detalhes/{id}")
    public String listarNinjasporId(@PathVariable Long id, Model model) {
        NinjaDTO ninja = ninjaService.listarNinjasporId(id);
        if (ninja != null) {
            model.addAttribute("ninja", ninja);
            return "Ninjas/detalhesNinjas";
        } else {
            model.addAttribute("mensagem", "Ninja não encontrado");
            return "redirect:/ninjas/ui/listar";
        }
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionarNinja(Model model) {
        model.addAttribute("ninja", new NinjaDTO());
        return "Ninjas/adicionarNinja";
    }

    @GetMapping("/alterar/{id}")
    public String mostrarFormularioAlterarNinja(@PathVariable Long id, Model model) {
        NinjaDTO ninja = ninjaService.listarNinjasporId(id);
        if (ninja != null) {
            model.addAttribute("ninja", ninja);
            return "Ninjas/adicionarNinja";
        } else {
            model.addAttribute("mensagem", "Ninja não encontrado");
            return "redirect:/ninjas/ui/listar";
        }
    }

    @PostMapping("/alterar/{id}")
    public String alterarNinja(@PathVariable Long id, @ModelAttribute NinjaDTO ninja,
                               RedirectAttributes redirectAttributes) {
        NinjaDTO ninjaAtual = ninjaService.listarNinjasporId(id);
        if (ninjaAtual == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Ninja não encontrado");
            return "redirect:/ninjas/ui/listar";
        }
        // O formulário não tem campo de missão: mantém a que já estava no banco
        ninja.setMissoes(ninjaAtual.getMissoes());
        ninjaService.alterarNinja(id, ninja);
        redirectAttributes.addFlashAttribute("mensagem", "Ninja alterado com sucesso!");
        return "redirect:/ninjas/ui/listar";
    }

    @PostMapping("/salvar")
    public String salvarNinja(@ModelAttribute NinjaDTO ninja,
                              RedirectAttributes redirectAttributes) {
        ninjaService.criarNinja(ninja);
        redirectAttributes.addFlashAttribute("mensagem", "Ninja cadastrado com sucesso!");
        return "redirect:/ninjas/ui/listar";
    }
}