package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProjetoEdicaoDTO implements IDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private List<Status> statusList;
    private List<Tarefa> tarefas;
    private List<Propriedade> propriedades;
    private List<ProjetoEquipe> projetoEquipes;
    private List<UsuarioProjeto> responsaveis;
    private LocalTime tempoAtuacao;
    private LocalDate dataFinal;
    private String categoria ="meus-projetos";;
    private Integer indexLista = 1;
    private List<Comentario> comentarios;

    public ProjetoEdicaoDTO(Projeto projeto) {
        this.id = projeto.getId();
        this.nome = projeto.getNome();
        this.descricao = projeto.getDescricao();
        this.statusList = projeto.getStatusList();
        this.tarefas = projeto.getTarefas();
        this.propriedades = projeto.getPropriedades();
        this.projetoEquipes = projeto.getProjetoEquipes();
        this.responsaveis = projeto.getResponsaveis();
    }



}
