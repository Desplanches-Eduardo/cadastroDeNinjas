package desplanches.eduardo.cadastroDeNinjas.Missoes;

import desplanches.eduardo.cadastroDeNinjas.Ninjas.NinjaModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor


public class MissoesDTO {
    private Long id;
    private String nome;
    private String dificuldade;
    private List<NinjaModel> ninjas;


}
