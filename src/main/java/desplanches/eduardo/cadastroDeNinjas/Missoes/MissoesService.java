package desplanches.eduardo.cadastroDeNinjas.Missoes;
import desplanches.eduardo.cadastroDeNinjas.Ninjas.NinjaModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MissoesService {
    private final MissoesRepository missoesRepository;
    private final MissoesMapper missoesMapper;

    public MissoesService(MissoesRepository missoesRepository, MissoesMapper missoesMapper) {
        this.missoesRepository = missoesRepository;
        this.missoesMapper = missoesMapper;
    }


    public List<MissoesDTO> listarMissoes(){
        List<MissoesModel> missoes = missoesRepository.findAll();
        return missoes.stream()
                .map(missoesMapper::map)
                .collect(Collectors.toList());
    }

    public MissoesDTO listarMissoesPorId(Long id){
        Optional<MissoesModel> missoesPorId = missoesRepository.findById(id);
        return missoesPorId.map(missoesMapper::map).orElse(null);
    }

    public MissoesDTO criarMissao(MissoesDTO missaoDto){
        MissoesModel missao = missoesMapper.map(missaoDto);
        missao = missoesRepository.save(missao);
        return missoesMapper.map(missao);
    }

    public void deletarMissao(Long id){
        missoesRepository.deleteById(id);
    }

    public MissoesDTO alterarMissoes(Long id, MissoesDTO missoesDTO){
        Optional<MissoesModel> missaoid = missoesRepository.findById(id);
    if (missaoid.isPresent()){
       MissoesModel missaoAlterada = missoesMapper.map(missoesDTO);
       missaoAlterada.setId(id);
       MissoesModel missaoSalva = missoesRepository.save(missaoAlterada);
       return missoesMapper.map(missaoSalva);
    }
    return null;
    }
}
