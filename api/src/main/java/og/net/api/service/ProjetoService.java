package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.ProjetoCadastroDTO;
import og.net.api.model.dto.ProjetoEdicaoDTO;
import og.net.api.model.entity.*;
import og.net.api.repository.ProjetoEquipeRepository;
import og.net.api.repository.ProjetoRepository;
import og.net.api.repository.UsuarioProjetoRepository;
import og.net.api.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjetoService {


    private ProjetoRepository projetoRepository;
    private EquipeService equipeService;
    private ProjetoEquipeRepository projetoEquipeRepository;
    private UsuarioService usuarioService;
    private UsuarioProjetoRepository usuarioProjetoRepository;
    private UsuarioRepository usuarioRepository;


    public Projeto buscarUm(Integer id) throws ProjetoNaoEncontradoException {
        if (projetoRepository.existsById(id)){
           return projetoRepository.findById(id).get();

        }
        throw new ProjetoNaoEncontradoException();
    }

    public List<Projeto> buscarProjetosNome(String nome){
        return projetoRepository.findByNome(nome);
    }

    public List<Projeto> buscarTodos(){
        return projetoRepository.findAll();
    }


    public void deletar(Integer id){
        projetoRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto) {
        ProjetoCadastroDTO projetoCadastroDTO = (ProjetoCadastroDTO) dto;
        Projeto projeto = new Projeto();
        BeanUtils.copyProperties(projetoCadastroDTO,projeto);
        projetoRepository.save(projeto);
    }

    public void cadastrarComListaDeEquipes(IDTO dto,List<ProjetoEquipe> equipes){
        ProjetoCadastroDTO projetoCadastroDTO = (ProjetoCadastroDTO) dto;
        Projeto projeto = new Projeto();
        BeanUtils.copyProperties(projetoCadastroDTO,projeto);
        projetoRepository.save(projeto);

    }

    public Projeto editar(IDTO dto) throws DadosNaoEncontradoException {
        ProjetoEdicaoDTO projetoEdicaoDTO = (ProjetoEdicaoDTO) dto;
        Projeto projeto = new Projeto();
        BeanUtils.copyProperties(projetoEdicaoDTO,projeto);
        if (projetoRepository.existsById(projeto.getId())){
            projetoRepository.save(projeto);
             return projeto;
        }
        throw new DadosNaoEncontradoException();
    }

    public void adicionarAEquipeAProjeto(Integer projetoId, Integer equipeId) throws ProjetoNaoEncontradoException {
        System.out.println(projetoId + " | " + equipeId);
        System.out.println(buscarUm(projetoId));
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

    public void adicionarResponsavelProjeto(Integer projetoId, Integer userId) throws ProjetoNaoEncontradoException {
        Projeto projeto = buscarUm(projetoId);
        Usuario usuario = usuarioService.buscarUm(userId);
        projeto.getResponsaveis().add(usuario);
        projetoRepository.save(projeto);
    }

    public List<Projeto> buscarProjetosEquipes(Integer equipeId) throws EquipeNaoEncontradaException {
        Equipe equipe = equipeService.buscarUm(equipeId);
        return projetoEquipeRepository.findAllByEquipe(equipe).stream().map(
                eu -> projetoRepository.findByProjetoEquipesContaining(eu)).toList();
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

    public void adicionarProjetosUsuarios(Integer userId, Integer projetoId) throws ProjetoNaoEncontradoException {
        System.out.println(userId + " | " + projetoId);
        System.out.println(buscarUm(projetoId));
        try {
            Projeto projeto = buscarUm(projetoId);
            Usuario user = usuarioService.buscarUm(userId);

            UsuarioProjeto usuarioProjeto = new UsuarioProjeto();
            usuarioProjeto.setProjeto(projeto);
            user.getProjetos().add(usuarioProjeto);
            usuarioRepository.save(user);

        } catch (ProjetoNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Projeto> buscarProjetosUsuario(Integer userId) {
        Usuario usuario = usuarioService.buscarUm(userId);

        // Encontra todos os registros de UsuarioProjeto associados ao usuário específico
        List<UsuarioProjeto> usuarioProjetos = usuario.getProjetos();

        // Extrai os projetos desses registros e os coleta em uma lista
        return usuarioProjetos.stream()
                .map(UsuarioProjeto::getProjeto)
                .collect(Collectors.toList());
    }

    public void removerProjetoDoUsuario(Integer userId, Integer projetoId) throws ProjetoNaoEncontradoException {
        Usuario usuario = usuarioService.buscarUm(userId);
        Projeto projeto = buscarUm(projetoId);
        if(usuario.getProjetos().contains(projeto)){
            usuario.getProjetos().remove(projeto);
        }
    }
}
