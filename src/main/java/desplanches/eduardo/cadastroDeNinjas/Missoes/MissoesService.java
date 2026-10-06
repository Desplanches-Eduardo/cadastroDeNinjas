package desplanches.eduardo.cadastroDeNinjas.Missoes;
import desplanches.eduardo.cadastroDeNinjas.Ninjas.NinjaModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MissoesService {
    private final MissoesRepository missoesRepository;

    public MissoesService(MissoesRepository missoesRepository) {
        this.missoesRepository = missoesRepository;
    }

    public List<MissoesModel> listarMissoes(){
        return missoesRepository.findAll();
    }

    public MissoesModel listarMissoesPorId(Long id){
        Optional<MissoesModel> missoesPorId = missoesRepository.findById(id);
        return missoesPorId.orElse(null);
    }

    public MissoesModel criarMissao(MissoesModel missao){
        return missoesRepository.save(missao);
    }

    public void deletarMissao(Long id){
        missoesRepository.deleteById(id);
    }

    public MissoesModel alterarMissoes(Long id, MissoesModel missaoAlterada){
    if (missoesRepository.existsById(id)){
        missaoAlterada.setId(id);
        missoesRepository.save(missaoAlterada);
    }
    return null;
    }
}
