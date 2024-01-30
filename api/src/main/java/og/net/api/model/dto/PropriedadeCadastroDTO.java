package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Tipo;
import og.net.api.model.entity.Valor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PropriedadeCadastroDTO implements IDTO{

    private String nome;
    private Tipo tipo;


}
