package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.UsuarioEdicaoDTO;
import og.net.api.model.dto.VisualizacaoEmListaEdicaoDTO;
import og.net.api.model.entity.VisualizacaoEmLista;
import og.net.api.repository.VisualizacaoEmListaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisualizacaoEmListaService {

    private VisualizacaoEmListaRepository visualizacaoEmListaRepository;
    private ProjetoService projetoService;
    private ModelMapper modelMapper;
    public VisualizacaoEmLista buscarPorProjeto(Integer projetoId) throws ProjetoNaoEncontradoException {
        return visualizacaoEmListaRepository.findVisualizacaoEmListaByProjeto(projetoService.buscarUm(projetoId));
    }
    public List<VisualizacaoEmLista> buscarTodos(){
        return visualizacaoEmListaRepository.findAll();
    }

    public VisualizacaoEmLista editar(IDTO dto) throws DadosNaoEncontradoException {
        VisualizacaoEmListaEdicaoDTO visualizacaoEmListaEdicaoDTO = (VisualizacaoEmListaEdicaoDTO) dto;
        VisualizacaoEmLista visualizacaoEmLista = new VisualizacaoEmLista();
        modelMapper.map(visualizacaoEmListaEdicaoDTO,visualizacaoEmLista);
        if (visualizacaoEmListaRepository.existsById(visualizacaoEmLista.getId())){
            visualizacaoEmListaRepository.save(visualizacaoEmLista);
            return visualizacaoEmLista;
        }
        throw new DadosNaoEncontradoException();
    }

}
