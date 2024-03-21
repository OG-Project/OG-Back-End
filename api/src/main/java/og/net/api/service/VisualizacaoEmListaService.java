package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.UsuarioEdicaoDTO;
import og.net.api.model.dto.VisualizacaoEmListaEdicaoDTO;
import og.net.api.model.entity.Usuario;
import og.net.api.model.entity.VisualizacaoEmLista;
import og.net.api.repository.VisualizacaoEmListaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VisualizacaoEmListaService {

    VisualizacaoEmListaRepository visualizacaoEmListaRepository;
    ProjetoService projetoService;
    public VisualizacaoEmLista buscarPorProjeto(Integer projetoId) throws ProjetoNaoEncontradoException {
        return visualizacaoEmListaRepository.findVisualizacaoEmListaByProjeto(projetoService.buscarUm(projetoId));
    }

    public VisualizacaoEmLista editar(IDTO dto) throws DadosNaoEncontradoException {
        VisualizacaoEmListaEdicaoDTO visualizacaoEmListaEdicaoDTO = (VisualizacaoEmListaEdicaoDTO) dto;
        VisualizacaoEmLista visualizacaoEmLista = new VisualizacaoEmLista();
        BeanUtils.copyProperties(visualizacaoEmListaEdicaoDTO,visualizacaoEmLista);
        if (visualizacaoEmListaRepository.existsById(visualizacaoEmLista.getId())){
            visualizacaoEmListaRepository.save(visualizacaoEmLista);
            return visualizacaoEmLista;
        }
        throw new DadosNaoEncontradoException();
    }

}
