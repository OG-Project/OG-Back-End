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
    private Configuracao configuracao;
    private Arquivo foto;
    private Boolean isGoogleLogado;
    private UsuarioDetailsEntity usuarioDetailsEntity;

}
