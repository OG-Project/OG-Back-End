package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.dto.*;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Status;
import og.net.api.repository.StatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusService {

    private ModelMapper modelMapper;
    private StatusRepository statusRepository;
    private ProjetoService projetoService;
    public Status buscarUm(Integer id){
        return statusRepository.findById(id).get();
    }

    public List<Status> buscarTodos(){
        return statusRepository.findAll();
    }

    public void deletar(Integer id){
        statusRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto){
        StatusCadastroDTO statusCadastroDTO = (StatusCadastroDTO) dto;
        Status status = new Status();
        modelMapper.map(statusCadastroDTO,status);
        statusRepository.save(status);
    }
    public void cadastrarStatusPeloProjeto(IDTO dto,Integer id) throws ProjetoNaoEncontradoException, DadosNaoEncontradoException {
        Projeto projeto = projetoService.buscarUm(id);
        StatusCadastroDTO statusCadastroDTO = (StatusCadastroDTO) dto;
        Status status = new Status();
        modelMapper.map(statusCadastroDTO,status);
        statusRepository.save(status);
        projeto.getStatusList().add(status);
        projetoService.editar(new ProjetoEdicaoDTO(projeto));
    }

    public Status editar(IDTO dto) throws DadosNaoEncontradoException {
        StatusEdicaoDTO statusEdicaoDTO = (StatusEdicaoDTO) dto;
        Status status = new Status();
        modelMapper.map(statusEdicaoDTO,status);
        if (statusRepository.existsById(status.getId())){
            statusRepository.save(status);
            return status;
        }
        throw new DadosNaoEncontradoException();

    }
}
