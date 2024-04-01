package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.TarefaCadastroDTO;
import og.net.api.model.dto.TarefaEdicaoDTO;
import og.net.api.model.entity.Arquivo;
import og.net.api.model.entity.Tarefa;
import og.net.api.repository.TarefaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    public List<Tarefa> buscarTodos(){
        return tarefaRepository.findAll();
    }
    public Page<Tarefa> buscarTodos(Pageable pageable){
        return tarefaRepository.findAll(pageable);
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

    public void atualizarArquivos(Integer id, List<MultipartFile> arquivos) throws IOException, TarefaInesxistenteException {
        Tarefa tarefa = buscarUm(id);
        ArrayList<Arquivo> arquivosTeste = new ArrayList<>() ;
        arquivos.stream().forEach(arquivo->{
            try {
                arquivosTeste.add(new Arquivo(arquivo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        tarefa.setArquivos(arquivosTeste);
        tarefaRepository.save(tarefa);
    }

    public Tarefa editar(IDTO dto) throws DadosNaoEncontradoException {
        TarefaEdicaoDTO tarefaEdicaoDTO = (TarefaEdicaoDTO) dto;
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaEdicaoDTO,tarefa);
        if (tarefaRepository.existsById(tarefa.getId())){
            tarefaRepository.save(tarefa);
            return tarefa;
        }
        throw new DadosNaoEncontradoException();

    }
}
