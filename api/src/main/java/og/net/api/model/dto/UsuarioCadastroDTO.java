package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Arquivo;
import og.net.api.model.entity.EquipeUsuario;
import og.net.api.model.entity.UsuarioProjeto;
import og.net.api.model.entity.UsuarioTarefa;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDTO implements IDTO{

    private String nome;
    private String sobrenome;
    private String username;
    private Date dataNascimento;
    private String email;
    private String empresa;
    private String senha;
    private List<UsuarioTarefa> tarefas;
    private List<UsuarioProjeto>  projetos;
    private List<EquipeUsuario> equipes;
    private Arquivo foto;

    public void setFoto(MultipartFile foto) throws IOException {
        Arquivo a = new Arquivo();
        a.setDados(foto.getBytes());
        a.setNome(foto.getOriginalFilename());
        a.setTipo(foto.getContentType());
        this.foto = a;
    }

}
