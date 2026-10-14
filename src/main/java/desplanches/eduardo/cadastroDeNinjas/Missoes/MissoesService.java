package desplanches.eduardo.cadastroDeNinjas.Missoes;
import desplanches.eduardo.cadastroDeNinjas.Ninjas.NinjaModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MissoesService {
    private final MissoesRepository missoesRepository;
    private final MissoesMapper missoesMapper;

    public MissoesService(MissoesRepository missoesRepository, MissoesMapper missoesMapper) {
        this.missoesRepository = missoesRepository;
        this.missoesMapper = missoesMapper;
    }


    public List<MissoesModel> listarMissoes(){
        return missoesRepository.findAll();
    }

    public MissoesModel listarMissoesPorId(Long id){
        Optional<MissoesModel> missoesPorId = missoesRepository.findById(id);
        return missoesPorId.orElse(null);
    }

    public MissoesDTO criarMissao(MissoesDTO missaoDto){
        MissoesModel missao = missoesMapper.map(missaoDto);
        missao = missoesRepository.save(missao);
        return missoesMapper.map(missao);
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
