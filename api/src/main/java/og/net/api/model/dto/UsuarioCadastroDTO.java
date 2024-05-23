package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    private Configuracao configuracao;
    private Boolean isGoogleLogado;
    private UsuarioDetailsEntity usuarioDetailsEntity;

    public void setFoto(MultipartFile foto) throws IOException {
        Arquivo a = new Arquivo();
        a.setDados(foto.getBytes());
        a.setNome(foto.getOriginalFilename());
        a.setTipo(foto.getContentType());
        this.foto = a;
    }

    public UsuarioCadastroDTO(OAuth2User auth2User) {
        String primeiroNome = auth2User.getAttribute("name");
        String sobrenome = auth2User.getAttribute("family_name");
        primeiroNome = primeiroNome.substring(0, primeiroNome.indexOf(" "));
        this.nome = primeiroNome;
        this.sobrenome = sobrenome;
        this.senha = auth2User.getAttribute("email");
        this.email = auth2User.getAttribute("email");
        this.username = email.substring(0,email.indexOf("@"));
    }

}
