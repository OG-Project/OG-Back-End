package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDTO implements IDTO{

    private String nome;
    private String sobrenome;
    private String username;
    private Date data_nascimento;
    private String email;
    private String empresa;
    private String senha;

}
