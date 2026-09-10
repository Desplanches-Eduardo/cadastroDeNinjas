package desplanches.eduardo.cadastroDeNinjas.Missoes;

import desplanches.eduardo.cadastroDeNinjas.Ninjas.NinjaModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_missoes")
public class MissoesModel {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String dificuldade;
    // @OneToMany -> Uma missão pode ter muitos ninjas
    @OneToMany(mappedBy = "missoes")
    private List<NinjaModel> ninjas;


}
