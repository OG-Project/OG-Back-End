package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.model.dto.HistoricoCadastroDTO;
import og.net.api.model.dto.HistoricoEdicaoDTO;
import og.net.api.model.dto.IDTO;
import og.net.api.model.entity.Historico.Historico;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Tarefa;
import og.net.api.repository.HistoricoRepository.HistoricoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoricoService {
    private HistoricoRepository historicoRepository;
    private ModelMapper modelMapper;

    public Historico buscarUm(Integer id){
        return historicoRepository.findById(id).get();
    }

    public List<Historico> buscarTodos(){
        return historicoRepository.findAll();
    }

    public void deletar(Integer id){
        historicoRepository.deleteById(id);
    }

    public Historico cadastrar(IDTO dto) {
        HistoricoCadastroDTO historicoCadastroDTO = (HistoricoCadastroDTO) dto;
        Historico historico = new Historico();
        modelMapper.map(historicoCadastroDTO,historico);
        return historicoRepository.save(historico);
    }

    public Historico editar(HistoricoEdicaoDTO historicoEdicaoDTO) throws DadosNaoEncontradoException {
        if (historicoRepository.existsById(historicoEdicaoDTO.getId())){
            Historico historico = new Historico();
            modelMapper.map(historicoEdicaoDTO, historico);
            historicoRepository.save(historico);
            return historico;
        }
        throw new DadosNaoEncontradoException();
    }

    public List<Historico> buscarPorTarefa(Tarefa tarefa){
        return historicoRepository.findHistoricoByTarefa(tarefa);
    }

    public List<Historico> buscarPorProjeto(Projeto projeto){
        return historicoRepository.findHistoricoByProjeto(projeto);
    }
}
