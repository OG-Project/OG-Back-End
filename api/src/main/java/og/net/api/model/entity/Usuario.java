package og.net.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private List<EquipeUsuario> equipes;
    @OneToOne(cascade = CascadeType.ALL)
    private Configuracao configuracao;
    @OneToOne(cascade = CascadeType.ALL)
    private Arquivo foto;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private UsuarioDetailsEntity usuarioDetailsEntity;
}
