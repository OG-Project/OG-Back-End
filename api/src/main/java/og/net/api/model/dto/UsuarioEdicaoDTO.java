package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
import og.net.api.model.entity.EquipeUsuario;
import og.net.api.model.entity.UsuarioProjeto;
import og.net.api.model.entity.UsuarioTarefa;
=======
import og.net.api.model.entity.*;
>>>>>>> 20bf8abd6522b7f375c3d393459deb07a0f38c56

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEdicaoDTO implements IDTO {

    private Integer id;
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
<<<<<<< HEAD
=======
    private Configuracao configuracao;
    private Arquivo foto;
    private UsuarioDetailsEntity usuarioDetailsEntity;

>>>>>>> 20bf8abd6522b7f375c3d393459deb07a0f38c56
}
