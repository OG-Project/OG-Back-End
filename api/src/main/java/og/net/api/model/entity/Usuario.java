package og.net.api.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import og.net.api.model.dto.UsuarioCadastroDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private List<UsuarioTarefa> tarefas;

    @OneToMany(cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private List<EquipeUsuario> equipes;
    @OneToOne(cascade = CascadeType.ALL)
    private Configuracao configuracao;
    @OneToOne(cascade = CascadeType.ALL)
    private Arquivo foto;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private UsuarioDetailsEntity usuarioDetailsEntity;

    public Usuario(UsuarioDetailsEntity usuarioDetailsEntity){
            setUsuarioDetailsEntity(usuarioDetailsEntity);
    }

    public Usuario(){
        setUsuarioDetailsEntityCadastro();
    }

    public void setSenha(String senha) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(senha);
    }

    public void setUsuarioDetailsEntityCadastro() {
        this.usuarioDetailsEntity = UsuarioDetailsEntity
                .builder()
                .authorities(List.of(Permissao.CRIAR, Permissao.VER, Permissao.DELETAR, Permissao.EDITAR, Permissao.PATCH))
                .usuario(this)
                .build();

    }

}
