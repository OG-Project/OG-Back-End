package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.dto.*;
import og.net.api.model.entity.Notificacao;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Status;
import og.net.api.model.entity.Usuario;
import og.net.api.repository.NotificacaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificacaoService {
    private NotificacaoRepository notificacaoRepository;

    public Notificacao buscarUm(Integer id){
        return notificacaoRepository.findById(id).get();
    }

    public List<Notificacao> buscarTodos(){
        return notificacaoRepository.findAll();
    }

    public void deletar(Integer id){
        notificacaoRepository.deleteById(id);
    }

    public Notificacao cadastrar(IDTO dto) {
        NotificacaoCadastroDTO notificacaoCadastroDTO = (NotificacaoCadastroDTO) dto;
        Notificacao notificacao = new Notificacao();
        BeanUtils.copyProperties(notificacaoCadastroDTO, notificacao);
        return notificacaoRepository.save(notificacao);
    }
    public Notificacao editar(IDTO dto) throws DadosNaoEncontradoException {
        NotificacaoEdicaoDTO notificacaoEdicaoDTO = (NotificacaoEdicaoDTO) dto;
        Notificacao notificacao = new Notificacao();
        BeanUtils.copyProperties(notificacaoEdicaoDTO,notificacao);
        if (notificacaoRepository.existsById(notificacao.getId())){
            notificacaoRepository.save(notificacao);
            return notificacao;
        }
        throw new DadosNaoEncontradoException();
    }
    public List<Notificacao> buscarPorUsuario(Usuario receptor){
        return notificacaoRepository.findNotificacaosByReceptoresContaining(receptor);
    }
}
