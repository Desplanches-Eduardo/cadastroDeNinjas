package desplanches.eduardo.cadastroDeNinjas.Ninjas;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NinjaService {
    private final NinjaRepository ninjaRepository;
    private final NinjaMapper ninjaMapper;

    public NinjaService(NinjaRepository ninjaRepository, NinjaMapper ninjaMapper) {
        this.ninjaRepository = ninjaRepository;
        this.ninjaMapper = ninjaMapper;
    }

    public List<NinjaDTO> listarNinjas(){
        List<NinjaModel> ninjas = ninjaRepository.findAll();
       return ninjas.stream()
               .map(ninjaMapper::map)
               .collect(Collectors.toList());
    }

    public NinjaDTO listarNinjasporId(Long id){
        Optional<NinjaModel> ninjaPorId = ninjaRepository.findById(id);
        return ninjaPorId.map(ninjaMapper::map).orElse(null);
    }
    public NinjaDTO criarNinja(NinjaDTO ninjaDTO){
        NinjaModel ninja = ninjaMapper.map(ninjaDTO);
        ninja = ninjaRepository.save(ninja);
        return ninjaMapper.map(ninja);
    }
    public void deletarNinja(Long id){
        ninjaRepository.deleteById(id);
    }

    public NinjaDTO alterarNinja(Long id, NinjaDTO ninjaDTO){
        Optional<NinjaModel> ninjaId = ninjaRepository.findById(id);
        if (ninjaId.isPresent()){
                NinjaModel ninjaAlterado = ninjaMapper.map(ninjaDTO);
                ninjaAlterado.setId(id);
                NinjaModel ninjaSalvo = ninjaRepository.save(ninjaAlterado);
                return ninjaMapper.map(ninjaSalvo);
           }
        return null;
    }
}
