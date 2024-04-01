package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.model.dto.*;
import og.net.api.model.entity.Status;
import og.net.api.repository.StatusRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusService {

    private StatusRepository statusRepository;

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
        BeanUtils.copyProperties(statusCadastroDTO,status);
        statusRepository.save(status);
    }

    public Status editar(IDTO dto) throws DadosNaoEncontradoException {
        StatusEdicaoDTO statusEdicaoDTO = (StatusEdicaoDTO) dto;
        Status status = new Status();
        BeanUtils.copyProperties(statusEdicaoDTO,status);
        if (statusRepository.existsById(status.getId())){
            statusRepository.save(status);
            return status;
        }
        throw new DadosNaoEncontradoException();

    }
}
