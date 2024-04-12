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
import og.net.api.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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

    public void deletar(Integer id){
        Equipe equipe = equipeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada com o ID: " + id));

        // Remover relacionamentos EquipeUsuario
        List<EquipeUsuario> equipeUsuarios = equipeUsuarioRepository.findAllByEquipe(equipe);
        for (EquipeUsuario equipeUsuario : equipeUsuarios) {
            Usuario usuario = usuarioRepository.findByEquipesContaining(equipeUsuario);
            removerEquipeUsuario(equipe,usuario); // Implemente um método para remover a equipe do usuário
            equipeUsuarioRepository.delete(equipeUsuario);
        }

        // Remover relacionamentos ProjetoEquipe
        List<ProjetoEquipe> projetoEquipes = projetoEquipeRepository.findAllByEquipe(equipe);
        for (ProjetoEquipe projetoEquipe : projetoEquipes) {
            Projeto projeto = projetoRepository.findByProjetoEquipesContaining(projetoEquipe);
            removerProjetoDaEquipe(equipe,projeto); // Implemente um método para remover a equipe do projeto
            projetoEquipeRepository.delete(projetoEquipe);
        }

        // Agora a equipe pode ser excluída
        equipeRepository.delete(equipe);
    }

    public void removerEquipeUsuario(Equipe equipe, Usuario usuario){
        for(EquipeUsuario equipeUsuario : usuario.getEquipes()){
            if(equipeUsuario.getEquipe().getId().equals(equipe.getId())){
                usuario.getEquipes().remove(equipeUsuario);
                break;
            }
        }
        usuarioRepository.save(usuario);
    }

    public void removerProjetoDaEquipe(Equipe equipe,Projeto projeto){
        for(ProjetoEquipe projetoEquipe : projeto.getProjetoEquipes()){
            if(projetoEquipe.getEquipe().getId().equals(equipe.getId())){
                projeto.getProjetoEquipes().remove(projetoEquipe);
                break;
            }
        }
         projetoRepository.save(projeto);
    }

    public void atualizarFoto(Integer id, MultipartFile foto) throws IOException, EquipeNaoEncontradaException {
        Equipe equipe = buscarUm(id);
        equipe.setFoto(new Arquivo(foto));
        equipeRepository.save(equipe);
    }

    public Equipe cadastrar(IDTO dto) throws EquipeJaExistenteException {
        EquipeCadastroDTO equipeCadastroDTO = (EquipeCadastroDTO) dto;
        Equipe equipe = new Equipe();
        BeanUtils.copyProperties(equipeCadastroDTO,equipe);
        return  equipeRepository.save(equipe);
    }

    public void editar(IDTO dto) throws DadosNaoEncontradoException, EquipeNaoEncontradaException {
        //      Equipe equipe= equipeService.buscarUm(equipeEdicaoDTO.getId());
        //      //serve para não copiar atributos nulos
//              mapper.map(equipeEdicaoDTO,equipe);
        EquipeEdicaoDTO equipeEdicaoDTO = (EquipeEdicaoDTO) dto;
        Equipe equipe = buscarUm(((EquipeEdicaoDTO) dto).getId());
        BeanUtils.copyProperties(equipeEdicaoDTO,equipe);
        if (!equipeRepository.existsById(equipe.getId())){
            throw new DadosNaoEncontradoException();
        }
        equipeRepository.save(equipe);
    }
}
