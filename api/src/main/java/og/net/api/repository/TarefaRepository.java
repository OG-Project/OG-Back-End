package og.net.api.repository;

import jakarta.persistence.LockModeType;
import og.net.api.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

    List<Tarefa> findByNome(String nome);

    Page<Tarefa> findAll(Pageable pageable);

    List<Tarefa> findTarefasByIndice_Visualizacao(Visualizacao visualizacao);

}
