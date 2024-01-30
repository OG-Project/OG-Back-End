package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<UsuarioProjeto>  projetos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private List<EquipeUsuario> equipes;

//    Criar um atributo de equipe ativa que terá a equipe que o usuario estará usando naquele momento
//      será feito de maneira dinamica no front-end com logica

//    entity intermediaria que terá um id da equipe e outro do usuario tendo também um id próprio esse terá relação com o usuario sendo esta OneToOne.
//
//    entity intermediaria que terá um id de propriedade que terá também um id de tarefas e um de projetos, tendo uma relação OneToMany.
//
//    entity intermediaria que tera um id de permissao tendo também um id de tarefa e de usuario, tendo uma relação OneToOne. Isso serve também para projeto_usuario.
}
