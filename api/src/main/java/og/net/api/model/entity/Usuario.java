package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

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
    private String username;
    private Date data_nascimento;
    private String email;
    private String empresa;
    private String senha;
    @OneToOne
    private UsuarioTarefa usuarioTarefa;
    @OneToOne
    private UsuarioProjeto usuarioProjeto;
    @OneToOne
    private EquipeUsuario equipeUsuario;

//    Criar um atributo de equipe ativa que terá a equipe que o usuario estará usando naquele momento
//
//    entity intermediaria que terá um id da equipe e outro do usuario tendo também um id próprio esse terá relação com o usuario sendo esta OneToOne.
//
//    entity intermediaria que terá um id de propriedade que terá também um id de tarefas e um de projetos, tendo uma relação OneToMany.
//
//    entity intermediaria que tera um id de permissao tendo também um id de tarefa e de usuario, tendo uma relação OneToOne. Isso serve também para projeto_usuario.
}
