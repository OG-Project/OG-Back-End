package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Projeto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipeCadastroDTO implements IDTO {

    private String nome;
    private String descricao;
    private Projeto projeto;
}
