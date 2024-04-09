package og.net.api.model.dto;

import jakarta.persistence.Entity;
import og.net.api.model.entity.EquipeUsuario;
import og.net.api.model.entity.Usuario;
import og.net.api.model.entity.UsuarioProjeto;
import og.net.api.model.entity.UsuarioTarefa;

import java.util.Date;
import java.util.List;

public class UsuarioSemEquipesDTO {
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
}
