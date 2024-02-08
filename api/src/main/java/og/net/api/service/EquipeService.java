package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.EquipeJaExistenteException;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.model.dto.EquipeCadastroDTO;
import og.net.api.model.dto.EquipeEdicaoDTO;
import og.net.api.model.dto.IDTO;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Usuario;
import og.net.api.repository.EquipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class EquipeService {
//        private ModelMapper mapper;
    private EquipeRepository equipeRepository;

    public Equipe buscarUm(Integer id) throws EquipeNaoEncontradaException {
        if (equipeRepository.existsById(id)){
           return equipeRepository.findById(id).get();
        }
        throw new EquipeNaoEncontradaException();
    }

    public List<Equipe> buscarEquipesNome(String nome){
        return equipeRepository.findByNome(nome);
    }

    public List<Equipe> buscarTodos(){
        return equipeRepository.findAll();
    }

    public void deletar(Integer id){
        equipeRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto) throws EquipeJaExistenteException {
        EquipeCadastroDTO equipeCadastroDTO = (EquipeCadastroDTO) dto;
        Equipe equipe = new Equipe();
        BeanUtils.copyProperties(equipeCadastroDTO,equipe);
        equipeRepository.save(equipe);
    }

    public void editar(IDTO dto) throws DadosNaoEncontradoException {
        //      Equipe equipe= equipeService.buscarUm(equipeEdicaoDTO.getId());
        //      //serve para não copiar atributos nulos
//              mapper.map(equipeEdicaoDTO,equipe);
        EquipeEdicaoDTO equipeEdicaoDTO = (EquipeEdicaoDTO) dto;
        Equipe equipe = new Equipe();
        BeanUtils.copyProperties(equipeEdicaoDTO,equipe);
        if (!equipeRepository.existsById(equipe.getId())){
            throw new DadosNaoEncontradoException();
        }
        equipeRepository.save(equipe);
    }
}
