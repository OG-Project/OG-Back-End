package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.PropriedadeCadastroDTO;
import og.net.api.model.dto.PropriedadeEdicaoDTO;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Propriedade;
import og.net.api.repository.ProjetoRepository;
import og.net.api.repository.PropriedadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PropriedadeService {

    private PropriedadeRepository propriedadeRepository;
    private ProjetoService projetoService;

    public Propriedade buscarUm(Integer id){
        return propriedadeRepository.findById(id).get();
    }

    public List<Propriedade> buscarTodos(){
        return propriedadeRepository.findAll();
    }

    public void deletar(Integer id){
        propriedadeRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto, Integer projetoId) throws ProjetoNaoEncontradoException {
        PropriedadeCadastroDTO propriedadeCadastroDTO = (PropriedadeCadastroDTO) dto;
        Projeto projeto = projetoService.buscarUm(projetoId);
        Propriedade propriedade = new Propriedade();
        projeto.getPropriedades().add(propriedade);
        projeto.getTarefas().forEach(tarefa -> {

        });
        BeanUtils.copyProperties(propriedadeCadastroDTO,propriedade);
        propriedadeRepository.save(propriedade);
    }

    public Propriedade editar(IDTO dto) throws DadosNaoEncontradoException {
        PropriedadeEdicaoDTO propriedadeEdicaoDTO = (PropriedadeEdicaoDTO) dto;
        Propriedade propriedade = new Propriedade();
        BeanUtils.copyProperties(propriedadeEdicaoDTO,propriedade);
        if (propriedadeRepository.existsById(propriedade.getId())){
            propriedadeRepository.save(propriedade);
            return propriedade;
        }
        throw new DadosNaoEncontradoException();

    }
}
