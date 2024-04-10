package og.net.api.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.Factory.ValorFactory;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.PropriedadeCadastroDTO;
import og.net.api.model.dto.PropriedadeEdicaoDTO;
import og.net.api.model.entity.*;
import og.net.api.repository.ProjetoRepository;
import og.net.api.repository.PropriedadeRepository;
import og.net.api.repository.TarefaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PropriedadeService {

    private PropriedadeRepository propriedadeRepository;
    private ProjetoRepository projetoRepository;
    private TarefaRepository tarefaRepository;

    public Propriedade buscarUm(Integer id) {
        return propriedadeRepository.findById(id).get();
    }

    public List<Propriedade> buscarTodos() {
        return propriedadeRepository.findAll();
    }

    public void deletar(Integer id) {
        propriedadeRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto, Integer projetoId) throws ProjetoNaoEncontradoException {
        PropriedadeCadastroDTO propriedadeCadastroDTO = (PropriedadeCadastroDTO) dto;
        Projeto projeto = projetoRepository.findById(projetoId).get();
        Propriedade propriedade = new Propriedade();
        BeanUtils.copyProperties(propriedadeCadastroDTO, propriedade);
        projeto.getPropriedades().add(propriedade);
        propriedadeRepository.save(propriedade);
        criaValorPropriedadeTarefa(projeto, propriedade);
        projetoRepository.save(projeto);

    }

    public void criaValorPropriedadeTarefa(Projeto projeto, Propriedade propriedade) {
        List<ValorPropriedadeTarefa> valorPropriedadeTarefas = new ArrayList<>();
        projeto.getTarefas().forEach(tarefa -> {
            List<Indice> lista = List.of(
                    new Indice(null, 0L, Visualizacao.CALENDARIO),
                    new Indice(null, 0L, Visualizacao.LISTA),
                    new Indice(null, 0L, Visualizacao.TIMELINE),
                    new Indice(null, 0L, Visualizacao.KANBAN));

            ValorPropriedadeTarefa valorPropriedadeTarefa = new ValorPropriedadeTarefa(null, propriedade, gerarValor(propriedade), false, lista);
            tarefa.getValorPropriedadeTarefas().add(valorPropriedadeTarefa);
            tarefaRepository.save(tarefa);
        });
    }


    private Valor gerarValor(Propriedade propriedade) {
        Valor valor = null;
        if (propriedade.getTipo().equals(Tipo.DATA)) {
            valor = new Data(null, LocalDateTime.now());
        } else if (propriedade.getTipo().equals(Tipo.NUMERO)) {
            valor = new Numero(null, null);
        } else if (propriedade.getTipo().equals(Tipo.SELECAO)) {
            valor = new Selecao(null, null);
        } else if (propriedade.getTipo().equals(Tipo.TEXTO)) {
            valor = new Texto(null, "");
        }
        return valor;
    }

    public Propriedade editar(IDTO dto) throws DadosNaoEncontradoException {
        PropriedadeEdicaoDTO propriedadeEdicaoDTO = (PropriedadeEdicaoDTO) dto;
        Propriedade propriedade = new Propriedade();
        BeanUtils.copyProperties(propriedadeEdicaoDTO, propriedade);
        if (propriedadeRepository.existsById(propriedade.getId())) {
            propriedadeRepository.save(propriedade);
            return propriedade;
        }
        throw new DadosNaoEncontradoException();

    }
}
