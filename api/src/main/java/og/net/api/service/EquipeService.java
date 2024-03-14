package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.EquipeJaExistenteException;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.model.dto.EquipeCadastroDTO;
import og.net.api.model.dto.EquipeEdicaoDTO;
import og.net.api.model.dto.IDTO;
import og.net.api.model.entity.Arquivo;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.EquipeUsuario;
import og.net.api.model.entity.Usuario;
import og.net.api.repository.EquipeRepository;
import og.net.api.repository.EquipeUsuarioRepository;
import og.net.api.repository.UsuarioRepository;
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
        List<EquipeUsuario> equipeUsuarios = equipeUsuarioRepository.findAllByEquipe_id(id);
        Equipe equipe = equipeRepository.findById(id).get();
        equipeUsuarios.forEach(eu -> {
           equipeUsuarioRepository.deleteById(eu.getId());
        });

        equipeRepository.deleteById(id);
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
        System.out.println(equipeEdicaoDTO);
        Equipe equipe = buscarUm(((EquipeEdicaoDTO) dto).getId());  
        BeanUtils.copyProperties(equipeEdicaoDTO,equipe);
        if (!equipeRepository.existsById(equipe.getId())){
            throw new DadosNaoEncontradoException();
        }
        equipeRepository.save(equipe);
    }
}
