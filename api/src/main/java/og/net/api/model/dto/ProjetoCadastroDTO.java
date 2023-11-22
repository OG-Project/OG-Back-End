package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoCadastroDTO implements IDTO{

    private String nome;
    private String descricao;
    private Date data_inicio;
    private Date data_termino;
    private List<Status> status;
}
