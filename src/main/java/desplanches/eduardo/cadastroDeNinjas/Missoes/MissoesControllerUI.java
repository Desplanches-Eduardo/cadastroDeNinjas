package desplanches.eduardo.cadastroDeNinjas.Missoes;

import desplanches.eduardo.cadastroDeNinjas.Ninjas.NinjaModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/missoes/ui")
public class MissoesControllerUI {
    private final MissoesService missoesService;

    public MissoesControllerUI(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    @GetMapping("/listar")
    public String listarMissoes(Model model){
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        model.addAttribute("missoes", missoes);
        return "Missoes/listarMissoes";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesMissao(@PathVariable Long id, Model model) {
        MissoesDTO missao = missoesService.listarMissoesPorId(id);
        if (missao != null) {
            model.addAttribute("missao", missao);
            return "Missoes/detalhesMissoes";
        } else {
            model.addAttribute("erro", "Missão não encontrada");
            return "redirect:/missoes/ui/listar";
        }
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionarMissao(Model model) {
        model.addAttribute("missao", new MissoesDTO());
        return "Missoes/adicionarMissao";
    }

    @PostMapping("/salvar")
    public String salvarMissao(@ModelAttribute MissoesDTO missao,
                               RedirectAttributes redirectAttributes) {
        missoesService.criarMissao(missao);
        redirectAttributes.addFlashAttribute("mensagem", "Missão cadastrada com sucesso!");
        return "redirect:/missoes/ui/listar";
    }

    @GetMapping("/alterar/{id}")
    public String mostrarFormularioAlterarMissao(@PathVariable Long id, Model model) {
        MissoesDTO missao = missoesService.listarMissoesPorId(id);
        if (missao != null) {
            model.addAttribute("missao", missao);
            return "Missoes/adicionarMissao";
        } else {
            model.addAttribute("erro", "Missão não encontrada");
            return "redirect:/missoes/ui/listar";
        }
    }

    @PostMapping("/alterar/{id}")
    public String alterarMissao(@PathVariable Long id, @ModelAttribute MissoesDTO missao,
                                RedirectAttributes redirectAttributes) {
        if (missoesService.listarMissoesPorId(id) == null) {
            redirectAttributes.addFlashAttribute("erro", "Missão não encontrada");
            return "redirect:/missoes/ui/listar";
        }
        // Os ninjas apontam para a missão pelo lado dono da relação (tb_cadastro.missoes_id),
        // então o formulário não precisa reenviar a lista: a alteração não mexe nela.
        missoesService.alterarMissoes(id, missao);
        redirectAttributes.addFlashAttribute("mensagem", "Missão alterada com sucesso!");
        return "redirect:/missoes/ui/listar";
    }

    @PostMapping("/deletar/{id}")
    public String deletarMissao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        MissoesDTO missao = missoesService.listarMissoesPorId(id);
        if (missao == null) {
            redirectAttributes.addFlashAttribute("erro", "Missão não encontrada");
            return "redirect:/missoes/ui/listar";
        }
        // A chave estrangeira em tb_cadastro aponta para a missão: com ninjas vinculados
        // o delete estoura no banco, então avisa em vez de estourar.
        List<NinjaModel> ninjas = missao.getNinjas();
        if (ninjas != null && !ninjas.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro",
                    "Missão não deletada: " + ninjas.size() + " ninja(s) ainda estão vinculados a ela.");
            return "redirect:/missoes/ui/listar";
        }
        missoesService.deletarMissao(id);
        redirectAttributes.addFlashAttribute("mensagem", "Missão deletada com sucesso!");
        return "redirect:/missoes/ui/listar";
    }
}
