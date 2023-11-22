package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.exception.TarefaJaExistenteException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.TarefaCadastroDTO;
import og.net.api.model.dto.TarefaEdicaoDTO;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.Usuario;
import og.net.api.repository.TarefaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TarefaService {

    private TarefaRepository tarefaRepository;

    public Tarefa buscarUm(Integer id) throws TarefaInesxistenteException {
        if (tarefaRepository.existsById(id)){
            return tarefaRepository.findById(id).get();

        }
        throw new TarefaInesxistenteException();
    }

    public List<Tarefa> buscarTarefasNome(String nome){
        return tarefaRepository.findByNome(nome);
    }

    public List<Tarefa> buscarTarefasPorAtivas(Boolean ativo){
        return tarefaRepository.findByAtivo(ativo);
    }

    public List<Tarefa> buscarTodos(){
        return tarefaRepository.findAll();
    }

    public void deletar(Integer id){
        tarefaRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto) {
        TarefaCadastroDTO tarefaCadastroDTO = (TarefaCadastroDTO) dto;
        System.out.println(dto);
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaCadastroDTO,tarefa);
        tarefaRepository.save(tarefa);
    }

    public void editar(IDTO dto) throws DadosNaoEncontradoException {
        TarefaEdicaoDTO tarefaEdicaoDTO = (TarefaEdicaoDTO) dto;
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaEdicaoDTO,tarefa);
        if (tarefaRepository.existsById(tarefa.getId())){
            tarefaRepository.save(tarefa);
        }
        throw new DadosNaoEncontradoException();

    }
}
