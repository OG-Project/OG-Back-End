package og.net.api.repository;

import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

    List<Tarefa> findByNome(String nome);
    List<Tarefa> findByAtivo(Boolean ativo);
}
