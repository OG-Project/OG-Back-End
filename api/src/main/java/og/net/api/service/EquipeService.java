package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.EquipeJaExistenteException;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.dto.EquipeCadastroDTO;
import og.net.api.model.dto.EquipeEdicaoDTO;
import og.net.api.model.dto.IDTO;
import og.net.api.model.entity.*;
import og.net.api.model.entity.Notificacao.Notificacao;
import og.net.api.model.entity.Notificacao.NotificacaoConvite;
import og.net.api.model.entity.Notificacao.NotificacaoEquipe;
import og.net.api.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class EquipeService {
//        private ModelMapper mapper;
    private EquipeRepository equipeRepository;
    private EquipeUsuarioRepository equipeUsuarioRepository;
    private ProjetoEquipeRepository projetoEquipeRepository;
    private ProjetoRepository projetoRepository;
    private UsuarioRepository usuarioRepository;
    private NotificacaoService notificacaoService;

    private VisualizacaoEmListaRepository visualizacaoEmListaRepository;
    private ModelMapper modelMapper;

    public Equipe buscarUm(Integer id) throws EquipeNaoEncontradaException {
        if (equipeRepository.existsById(id)){
           return equipeRepository.findById(id).get();
        }
        throw new EquipeNaoEncontradaException();
    }

    public List<Equipe> buscarEquipesNome(String nome){
        return equipeRepository.findByNome(nome);
    }

    public List<EquipeUsuario> buscarTodosUsuarios(Equipe equipe) {
        return equipeUsuarioRepository.findAllByEquipe(equipe);
    }


    public List<Equipe> buscarTodos(){
        return equipeRepository.findAll();
    }

    public void deletar(Integer id) throws EquipeNaoEncontradaException {
        Equipe equipe = equipeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada com o ID: " + id));

        List<NotificacaoEquipe> lista = notificacaoService.buscarNotificaoEquipePorEquipe(equipe.getId());

        List<NotificacaoConvite>lista2 = (notificacaoService.buscarNotificaoConviteParaEquipePorEquipe(equipe.getId()));

        lista.forEach((notificacaoEquipe)->{
            notificacaoService.deletar(notificacaoEquipe.getId());
        });

        lista2.forEach((notificacaoConvite)->{
            notificacaoService.deletar(notificacaoConvite.getId());
        });

        List<EquipeUsuario> equipeUsuarios = equipeUsuarioRepository.findAllByEquipe(equipe);
        for (EquipeUsuario equipeUsuario : equipeUsuarios) {
            Usuario usuario = usuarioRepository.findByEquipesContaining(equipeUsuario);
            removerEquipeUsuario(equipe,usuario);

        }

        List<ProjetoEquipe> projetoEquipes = projetoEquipeRepository.findAllByEquipe(equipe);
        for (ProjetoEquipe projetoEquipe : projetoEquipes) {
            Projeto projeto = projetoRepository.findByProjetoEquipesContaining(projetoEquipe);
            removerProjetoDaEquipe(id, projeto.getId()); // Implemente um método para remover a equipe do projeto
        }
        // Agora a equipe pode ser excluída
        equipeRepository.delete(equipe);
    }

    public void removerEquipeUsuario(Equipe equipe, Usuario usuario){
        for(EquipeUsuario equipeUsuario : usuario.getEquipes()){
            if(equipeUsuario.getEquipe().getId().equals(equipe.getId())){
                usuario.getEquipes().remove(equipeUsuario);
                equipeUsuarioRepository.delete(equipeUsuario);
                break;
            }
        }
        usuarioRepository.save(usuario);
    }

    public void removerProjetoDaEquipe(Integer idEquipe,Integer idProjeto){
        Projeto projeto = projetoRepository.findById(idProjeto).get();
        for(ProjetoEquipe projetoEquipe : projeto.getProjetoEquipes()){
            if(projetoEquipe.getEquipe().getId().equals(idEquipe)){
                projeto.getProjetoEquipes().remove(projetoEquipe);
                projetoEquipeRepository.delete(projetoEquipe);
                break;
            }
        }
        projeto = projetoRepository.save(projeto);
        if(projeto.getProjetoEquipes().isEmpty()){
            VisualizacaoEmLista visualizacaoEmLista = visualizacaoEmListaRepository.findVisualizacaoEmListaByProjeto(projeto);
            //Tirar o if depois de recomeçar o banco de dados
            if (visualizacaoEmLista != null) {
                visualizacaoEmListaRepository.delete(visualizacaoEmLista);
            }
            projetoRepository.delete(projeto);
        }
    }

    public void atualizarFoto(Integer id, MultipartFile foto) throws IOException, EquipeNaoEncontradaException {
        Equipe equipe = buscarUm(id);
        equipe.setFoto(new Arquivo(foto));
        equipeRepository.save(equipe);
    }

    public Equipe cadastrar(IDTO dto) throws EquipeJaExistenteException {
        EquipeCadastroDTO equipeCadastroDTO = (EquipeCadastroDTO) dto;
        Equipe equipe = new Equipe();
        modelMapper.map(equipeCadastroDTO,equipe);
        return  equipeRepository.save(equipe);
    }

    public Equipe editar(IDTO dto) throws DadosNaoEncontradoException, EquipeNaoEncontradaException {
        //      Equipe equipe= equipeService.buscarUm(equipeEdicaoDTO.getId());
        //      //serve para não copiar atributos nulos
//              mapper.map(equipeEdicaoDTO,equipe);
        EquipeEdicaoDTO equipeEdicaoDTO = (EquipeEdicaoDTO) dto;
        Equipe equipe = buscarUm(((EquipeEdicaoDTO) dto).getId());
        modelMapper.map(equipeEdicaoDTO,equipe);
        if (!equipeRepository.existsById(equipe.getId())){
            throw new DadosNaoEncontradoException();
        }
        return equipeRepository.save(equipe);
    }
    public Usuario criadorDaEquipe(Integer equipeId) {
        Equipe equipe = equipeRepository.findById(equipeId).get();
        List<Usuario> usuarios = usuarioRepository.findAll();
         return usuarios.stream().filter(usuario -> {
             return usuario.getEquipes().stream().anyMatch(equipeUsuario -> equipeUsuario.getEquipe().getId().equals(equipe.getId()) && equipeUsuario.getCriador());
        }).findFirst().get();
    }
}
