package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.model.dto.*;
import og.net.api.model.entity.*;
import og.net.api.repository.NotificacaoRepositorys.NotificacaoProjetoRepository;
import og.net.api.repository.NotificacaoRepositorys.NotificacaoRepository;
import og.net.api.repository.ProjetoEquipeRepository;
import og.net.api.repository.ProjetoRepository;
import og.net.api.repository.TarefaRepository;
import og.net.api.repository.VisualizacaoEmListaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjetoService {

    private ProjetoRepository projetoRepository;
    private EquipeService equipeService;
    private ProjetoEquipeRepository projetoEquipeRepository;
    private UsuarioService usuarioService;
    private PropriedadeService propriedadeService;
    private VisualizacaoEmListaRepository visualizacaoEmListaRepository;
    private ModelMapper modelMapper;
    private TarefaRepository tarefaRepository;
    private final NotificacaoProjetoRepository notificacaoRepository;

    public Projeto buscarUm(Integer id) throws ProjetoNaoEncontradoException {
        if (projetoRepository.existsById(id)) {
            return projetoRepository.findById(id).get();
        }
        throw new ProjetoNaoEncontradoException();
    }

    public Projeto buscarPorTarefa(Integer id) throws ProjetoNaoEncontradoException, TarefaInesxistenteException {
            Tarefa tarefa = tarefaRepository.findById(id).get();
            return projetoRepository.findProjetoByTarefasContaining(tarefa);
    }

    public List<Projeto> buscarProjetosNome(String nome) {
        return projetoRepository.findByNome(nome);
    }

    public List<Projeto> buscarTodos() {
        return projetoRepository.findAll();
    }


    public void deletar(Integer id) {
        VisualizacaoEmLista visualizacaoEmLista = visualizacaoEmListaRepository.findVisualizacaoEmListaByProjeto(projetoRepository.findById(id).get());
        Projeto projeto = projetoRepository.findById(id).get();

        List<Tarefa> tarefas = projeto.getTarefas();
        projeto.getTarefas().removeAll(tarefas);

        List<Propriedade> propriedades = projeto.getPropriedades();
        projeto.getProjetoEquipes().removeAll(propriedades);

        projeto.getTarefas().stream().forEach(tarefa -> {
            tarefaRepository.deleteById(tarefa.getId());
        });

        projeto.getPropriedades().stream().forEach(propriedade -> {
            propriedadeService.deletar(propriedade.getId());
        });

        notificacaoRepository.deleteNotificacaoProjetosByProjeto(projeto);
        if (visualizacaoEmLista != null) {
            visualizacaoEmListaRepository.delete(visualizacaoEmLista);
        }
        projetoRepository.deleteById(id);
    }



    public Projeto cadastrar(IDTO dto) throws IOException {
        ProjetoCadastroDTO projetoCadastroDTO = (ProjetoCadastroDTO) dto;
        Projeto projeto = new Projeto();
        if (projetoCadastroDTO.getProjetoEquipes() != null) {
            projetoCadastroDTO.setProjetoEquipes(criacaoProjetoEquipe(projetoCadastroDTO));
        }
        if (projetoCadastroDTO.getResponsaveis() != null) {
            projetoCadastroDTO.setResponsaveis(criacaoResponsaveisProjeto(projetoCadastroDTO));
        }
        modelMapper.map(projetoCadastroDTO, projeto);
       Projeto projeto1= projetoRepository.save(projeto);
        VisualizacaoEmLista visualizacaoEmLista = new VisualizacaoEmLista(null, new ArrayList<>(), projeto);
        visualizacaoEmListaRepository.save(visualizacaoEmLista);
    return projeto1;
    }

    private List<UsuarioProjeto> criacaoResponsaveisProjeto(ProjetoCadastroDTO projetoCadastroDTO) {
        ArrayList<UsuarioProjeto> projetoResponsaveis = new ArrayList<>();
        projetoCadastroDTO.getResponsaveis().forEach((responsaveis -> {
            Usuario usuarioAtual = usuarioService.buscarUm(responsaveis.getIdResponsavel());
            UsuarioProjeto usuarioProjeto = new UsuarioProjeto(null,responsaveis.getIdResponsavel(),List.of(Permissao.CRIAR,Permissao.VER, Permissao.EDITAR, Permissao.DELETAR) );
           projetoResponsaveis.add(usuarioProjeto);
        }));

        return projetoResponsaveis;
    }

    private ArrayList<ProjetoEquipe> criacaoProjetoEquipe(ProjetoCadastroDTO projetoCadastroDTO) {

        ArrayList<ProjetoEquipe> projetoEquipeList = new ArrayList<>();
        projetoCadastroDTO.getProjetoEquipes().forEach((projetoEquipe -> {
            ProjetoEquipe projetoEquipe1 = new ProjetoEquipe(null, projetoEquipe.getEquipe());
            projetoEquipeList.add(projetoEquipe1);
        }));

        return projetoEquipeList;
    }

    public Projeto editar(IDTO dto) throws DadosNaoEncontradoException {
        ProjetoEdicaoDTO projetoEdicaoDTO = (ProjetoEdicaoDTO) dto;
        Projeto projeto = new Projeto();
        modelMapper.map(projetoEdicaoDTO, projeto);
        if (projetoRepository.existsById(projeto.getId())) {
            System.out.println(projeto);

            if(criaValorPorpiredadeTarefa(projeto)){
                projetoRepository.save(projeto);
            }
            return projeto;
        }
        throw new DadosNaoEncontradoException();
    }

    public void adicionarAEquipeAProjeto(Integer projetoId, Integer equipeId) throws ProjetoNaoEncontradoException {
        try {
            Equipe equipe = equipeService.buscarUm(equipeId);
            Projeto projeto = buscarUm(projetoId);

            ProjetoEquipe projetoEquipe = new ProjetoEquipe();
            projetoEquipe.setEquipe(equipe);
            projeto.getProjetoEquipes().add(projetoEquipe);
            projetoRepository.save(projeto);

        } catch (EquipeNaoEncontradaException e) {
            e.printStackTrace();
        }
    }
    public Boolean criaValorPorpiredadeTarefa(Projeto projeto) {
        ArrayList<Propriedade> propriedade2 = new ArrayList<>();
        if(projeto.getPropriedades()!=null){
             projeto.getPropriedades().forEach(propriedade -> {
                if (propriedade.getId() == null) {
                    PropriedadeCadastroDTO propriedadeCadastroDTO = new PropriedadeCadastroDTO(propriedade);
                    try {
                        propriedade2.add(propriedadeService.cadastrar(propriedadeCadastroDTO, projeto.getId()));
                    } catch (ProjetoNaoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        if(propriedade2.isEmpty()){
            return true;
        }
        return false;
    }

    public List<Projeto> buscarProjetosEquipes(Integer equipeId) throws EquipeNaoEncontradaException {
        Equipe equipe = equipeService.buscarUm(equipeId);
        List<Projeto> ps = new ArrayList<>(projetoEquipeRepository.findAllByEquipe(equipe).stream().map(
                eu -> projetoRepository.findByProjetoEquipesContaining(eu))
                .toList());

        ps.sort(Comparator.comparing(Projeto::getIndexLista));
        return ps;
    }

    public void removerProjetoDaEquipe(Integer equipeId,Integer projetoId) throws ProjetoNaoEncontradoException {
        Projeto projeto = buscarUm(projetoId);

        for(ProjetoEquipe projetoEquipe : projeto.getProjetoEquipes()){
            if(projetoEquipe.getEquipe().getId().equals(equipeId)){
                projeto.getProjetoEquipes().remove(projetoEquipe);
                break;
            }
        }
        projetoRepository.save(projeto);
    }


    public void atualizarUmaEquipeNoProjeto(List<ProjetoEquipe> projetoEquipes,Integer id) throws DadosNaoEncontradoException, EquipeNaoEncontradaException {
        Projeto projeto = projetoRepository.findById(id).get();
        projeto.setProjetoEquipes(projetoEquipes);
        List<ProjetoEquipe> projetoEquipesAuxiliar = projetoEquipes;
        for (ProjetoEquipe equipe : projetoEquipesAuxiliar) {
            equipeService.editar(new EquipeEdicaoDTO(equipe.getEquipe()));
        }
        projetoRepository.save(projeto);
    }

    public void deletarPropriedade(Integer idPropriedade, Integer idProjeto) throws ProjetoNaoEncontradoException {
        Projeto projeto = buscarUm(idProjeto);
        Propriedade propriedade = propriedadeService.buscarUm(idPropriedade);
        projeto.getPropriedades().remove(propriedade);
        propriedadeService.deletar(idPropriedade);

//        List<Propriedade> propriedadesParaRemover = new ArrayList<>();
//        for (Propriedade propriedadeFor : projeto.getPropriedades()) {
//            if (propriedade.equals(propriedadeFor)) {
//                propriedadesParaRemover.add(propriedadeFor);
//            }
//        }
//        for (Propriedade propriedadeParaRemover : propriedadesParaRemover) {
//            projeto.getPropriedades().remove(propriedadeParaRemover);
//        }
    }
}

