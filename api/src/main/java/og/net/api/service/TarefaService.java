package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.model.Factory.ValorFactory;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.ProjetoEdicaoDTO;
import og.net.api.model.dto.TarefaCadastroDTO;
import og.net.api.model.dto.TarefaEdicaoDTO;
import og.net.api.model.entity.*;
import og.net.api.repository.HistoricoRepository.HistoricoRepository;
import og.net.api.repository.ProjetoRepository;
import og.net.api.repository.TarefaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TarefaService {

    private TarefaRepository tarefaRepository;
    private ProjetoRepository projetoRepository;
    private ProjetoService projetoService;
    private ModelMapper modelMapper;
    private HistoricoService historicoService;

    public Tarefa buscarUm(Integer id) throws TarefaInesxistenteException {
        if (tarefaRepository.existsById(id)) {
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

    public void deletar(Integer id) throws ProjetoNaoEncontradoException, TarefaInesxistenteException {
        Projeto projeto = projetoService.buscarPorTarefa(id);
        projeto.getTarefas().remove(tarefaRepository.findById(id).get());
        historicoService.buscarPorTarefa(buscarUm(id)).forEach(historico -> {
            historico.setTarefa(null);
        });
        tarefaRepository.deleteById(id);
    }


    public Tarefa cadastrar(IDTO dto, Integer projetoId) throws DadosNaoEncontradoException {
        TarefaCadastroDTO tarefaCadastroDTO = (TarefaCadastroDTO) dto;
        Tarefa tarefa = new Tarefa();
        modelMapper.map(tarefaCadastroDTO,tarefa);
        Projeto projeto = projetoRepository.findById(projetoId).get();
        List<ValorPropriedadeTarefa> valorPropriedadeTarefas = new ArrayList<>();
        List<Indice> lista = new ArrayList<>(List.of(
                new Indice(null, 0L, Visualizacao.CALENDARIO),
                new Indice(null, 0L, Visualizacao.LISTA),
                new Indice(null, 0L, Visualizacao.TIMELINE),
                new Indice(null, 0L, Visualizacao.KANBAN)));
        tarefa.setIndice(lista);
        if (projeto.getProjetoEquipes()!=null) {
            if (projeto.getPropriedades()!=null) {
                for (Propriedade propriedade : projeto.getPropriedades()) {

                    ValorPropriedadeTarefa valorPropriedadeTarefa = new ValorPropriedadeTarefa(null, propriedade, gerarValor(propriedade), false);
                    valorPropriedadeTarefas.add(valorPropriedadeTarefa);
                }
                tarefa.setValorPropriedadeTarefas(valorPropriedadeTarefas);
            }
        }
        Tarefa tarefaReturn = null;
        tarefa.setResponsaveis(colocaResponsaveisTarefa(tarefaCadastroDTO));
        tarefa.setArquivos(new ArrayList<>());
        try {
            tarefaReturn = tarefaRepository.save(tarefa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        projeto.getTarefas().add(tarefa);
        projetoService.editar(new ProjetoEdicaoDTO(projeto));
        return tarefaReturn;
    }


    private List<UsuarioTarefa> colocaResponsaveisTarefa(TarefaCadastroDTO tarefaCadastroDTO){
        List<UsuarioTarefa> usuarioTarefas = new ArrayList<>();
        tarefaCadastroDTO.getResponsaveis().forEach(usuarioTarefa -> {
            UsuarioTarefa usuarioTarefa1 = new UsuarioTarefa(null,usuarioTarefa.getIdResponsavel());
            usuarioTarefas.add(usuarioTarefa1);
        });
        return usuarioTarefas;
    }

    private Valor gerarValor(Propriedade propriedade){
        return ValorFactory.getValor(propriedade.getTipo());
    }

    public void atualizarFoto(Integer id, MultipartFile arquivo) throws IOException, TarefaInesxistenteException {
        Tarefa tarefa = buscarUm(id);
        Arquivo arquivo1 = new Arquivo(arquivo);
        tarefa.getArquivos().add(arquivo1);
        tarefaRepository.save(tarefa);
    }

    public void atualizarArquivo(Integer id, Arquivo arquivo) throws IOException, TarefaInesxistenteException {
        Tarefa tarefa = buscarUm(id);
        if(tarefa.getArquivos().isEmpty()){
            tarefa.setArquivos(new ArrayList<>());
        }
        System.out.println(tarefa.getArquivos());
        tarefa.getArquivos().add(arquivo);
        tarefaRepository.save(tarefa);
    }

    public void deletaListaDeArquivos(Integer id)throws IOException, TarefaInesxistenteException{
        Tarefa tarefa = buscarUm(id);
        for(Arquivo arquivo: tarefa.getArquivos()){
            tarefa.getArquivos().remove(arquivo);
        }
    }

    public Tarefa editar(TarefaEdicaoDTO dto) throws DadosNaoEncontradoException {
        Tarefa tarefa = new Tarefa();
        modelMapper.map(dto,tarefa);
        if (tarefaRepository.existsById(tarefa.getId())){
            return  tarefaRepository.save(tarefa);
        }
        throw new DadosNaoEncontradoException();

    }
    public Tarefa editarValorPropriedadetarefa(Integer id,List<ValorPropriedadeTarefa> valorPropriedadeTarefas) throws DadosNaoEncontradoException {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()){
            tarefa.get().setValorPropriedadeTarefas(valorPropriedadeTarefas);
            return tarefaRepository.save(tarefa.get());
        }
        throw new DadosNaoEncontradoException();
    }
}
