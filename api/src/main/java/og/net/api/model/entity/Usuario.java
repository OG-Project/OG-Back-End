package og.net.api.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import og.net.api.model.dto.UsuarioCadastroDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String sobrenome;
    @Column(unique = true, nullable = false)
    private String username;
    private Date dataNascimento;
    @Column(unique = true, nullable = false)
    private String email;
    private String empresa;
    @Column(nullable = false)
    private String senha;

    @OneToMany(cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private List<EquipeUsuario> equipes;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Configuracao configuracao;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Arquivo foto;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private UsuarioDetailsEntity usuarioDetailsEntity;

    public Usuario(UsuarioDetailsEntity usuarioDetailsEntity){
            setUsuarioDetailsEntity(usuarioDetailsEntity);
    }

    public Usuario(){
        setUsuarioDetailsEntityCadastro();
    }


    public void setUsuarioDetailsEntityCadastro() {
        this.usuarioDetailsEntity = UsuarioDetailsEntity
                .builder()
                .authorities(List.of(Permissao.CRIAR, Permissao.VER, Permissao.DELETAR, Permissao.EDITAR, Permissao.PATCH))
                .usuario(this)
                .build();

    }

    public Usuario(OAuth2User auth2User) {
        String primeiroNome = auth2User.getAttribute("name");
        String sobrenome = auth2User.getAttribute("family_name");
        primeiroNome = primeiroNome.substring(0, primeiroNome.indexOf(" "));
        String email= auth2User.getAttribute("email");
        this.nome = primeiroNome;
        this.sobrenome = sobrenome;
        this.senha = auth2User.getAttribute("email");
        this.email = auth2User.getAttribute("email");
        System.out.println(email.substring(0,email.indexOf("@")));
        this.username = email.substring(0,email.indexOf("@"));
    }
}
