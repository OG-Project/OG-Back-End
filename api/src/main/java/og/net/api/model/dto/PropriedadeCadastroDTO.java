package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.PropriedadeProjetoTarefa;
import og.net.api.model.entity.Tipo;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PropriedadeCadastroDTO implements IDTO{

    private String nome;
    private Tipo tipo;
    private PropriedadeProjetoTarefa valor;

}
