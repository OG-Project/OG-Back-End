package og.net.api.model.dto;

import og.net.api.model.entity.*;

import java.util.Date;
import java.util.List;

public class UsuarioResponsavelProjetoDTO {

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

}
