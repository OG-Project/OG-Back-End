package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.exception.TarefaJaExistenteException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.ProjetoEdicaoDTO;
import og.net.api.model.dto.TarefaCadastroDTO;
import og.net.api.model.dto.TarefaEdicaoDTO;
import og.net.api.model.entity.*;
import og.net.api.repository.ProjetoRepository;
import og.net.api.repository.TarefaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TarefaService {

    private TarefaRepository tarefaRepository;
    private ProjetoRepository projetoRepository;
    private ProjetoService projetoService;

    public Tarefa buscarUm(Integer id) throws TarefaInesxistenteException {
        if (tarefaRepository.existsById(id)){
            return tarefaRepository.findById(id).get();

        }
        throw new TarefaInesxistenteException();
    }
     public List<Tarefa> buscarTarefasPorVisualizacao(String nome){
        return tarefaRepository.findTarefasByValorPropriedadeTarefas_indice_visualizacaoOrderByValorPropriedadeTarefas_indice_indice(nome);
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

    public void cadastrar(IDTO dto, Integer projetoId) throws DadosNaoEncontradoException {
        TarefaCadastroDTO tarefaCadastroDTO = (TarefaCadastroDTO) dto;
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaCadastroDTO,tarefa);
        Projeto projeto = projetoRepository.findById(projetoId).get();
        List<ValorPropriedadeTarefa> valorPropriedadeTarefas = new ArrayList<>();
        for (Propriedade propriedade : projeto.getPropriedades()){
            List<Indice> lista = List.of(
                    new Indice(null, 0L, Visualizacao.CALENDARIO),
                    new Indice(null, 0L, Visualizacao.LISTA),
                    new Indice(null, 0L, Visualizacao.TIMELINE),
                    new Indice(null, 0L, Visualizacao.KANBAN));

            ValorPropriedadeTarefa valorPropriedadeTarefa = new ValorPropriedadeTarefa(null, propriedade, gerarValor(propriedade),false, lista);
            valorPropriedadeTarefas.add(valorPropriedadeTarefa);
        }
        tarefa.setValorPropriedadeTarefas(valorPropriedadeTarefas);
        tarefaRepository.save(tarefa);
        projeto.getTarefas().add(tarefa);
        projetoService.editar(new ProjetoEdicaoDTO(projeto));
    }

    private Valor gerarValor(Propriedade propriedade){
        Valor valor = null;
        if(propriedade.getTipo().equals(Tipo.DATA)){
            valor = new Data(null, LocalDateTime.now());
        }
        else if(propriedade.getTipo().equals(Tipo.NUMERO)){
            valor = new Numero(null, null);
        }
        else if(propriedade.getTipo().equals(Tipo.SELECAO)){
            valor = new Selecao(null, null);
        }
        else if(propriedade.getTipo().equals(Tipo.TEXTO)){
            valor = new Texto(null, "");
        }
        return valor;
    }

    public void atualizarFoto(Integer id, List<MultipartFile> arquivos) throws IOException, TarefaInesxistenteException {
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
            System.out.println(tarefaRepository);
                tarefaRepository.save(tarefa);
            System.out.println(tarefa);
            return tarefa;
        }
        throw new DadosNaoEncontradoException();

    }
}
