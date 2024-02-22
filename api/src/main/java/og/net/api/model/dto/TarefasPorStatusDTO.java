package og.net.api.model.dto;

import lombok.Data;
import og.net.api.model.entity.Tarefa;

import java.util.ArrayList;
import java.util.List;
@Data
public class TarefasPorStatusDTO {

    List<List<Tarefa>> tarefas = new ArrayList<>();
}
