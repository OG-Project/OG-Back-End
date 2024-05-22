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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoCadastroDTO implements IDTO{

    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao =LocalDateTime.now();
    private List<Status> statusList;
    private List<Tarefa> tarefas;
    private List<Propriedade> propriedades;
    private List<ProjetoEquipe> projetoEquipes;
    private List<UsuarioProjeto> responsaveis;
    private LocalDate dataFinal;
    private String categoria = "meus-projetos";


    //Define a data da criação!
    public LocalDateTime getDataCriacao() {
        return LocalDateTime.now();
    }




}
