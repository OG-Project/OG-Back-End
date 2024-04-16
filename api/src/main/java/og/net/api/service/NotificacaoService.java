package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.dto.*;
import og.net.api.model.entity.*;
import og.net.api.model.entity.Notificacao.*;
import og.net.api.repository.NotificacaoRepositorys.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificacaoService {
    private NotificacaoRepository notificacaoRepository;
    private NotificacaoConviteRepository notificacaoConviteRepository;
    private NotificacaoEquipeRepository notificacaoEquipeRepository;
    private NotificacaoProjetoRepository notificacaoProjetoRepository;
    private NotificacaoTarefaRepository notificacaoTarefaRepository;
    private EquipeService equipeService;
    private ProjetoService projetoService;

    public Notificacao buscarUm(Integer id){
        return notificacaoRepository.findById(id).get();
    }

    public List<Notificacao> buscarTodos(){
        return notificacaoRepository.findAll();
    }

    public List<NotificacaoConvite> buscarNotificaoConviteParaEquipePorEquipe(Integer equipeId) throws EquipeNaoEncontradaException {
        Equipe equipe = equipeService.buscarUm(equipeId);
        return notificacaoConviteRepository.findNotificacaoConviteByConviteParaEquipe_Equipe(equipe);
    }
    public List<NotificacaoConvite> buscarNotificaoConviteParaProjetoPorProjeto(Integer projetoId) throws ProjetoNaoEncontradoException {
        Projeto projeto = projetoService.buscarUm(projetoId);
        return notificacaoConviteRepository.findNotificacaoConviteByConviteParaProjeto_Projeto(projeto);
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
    public Notificacao editar(NotificacaoConvite notificacao) throws DadosNaoEncontradoException {
        if (notificacaoRepository.existsById(notificacao.getId())){
            notificacaoRepository.save(notificacao);
            return notificacao;
        }
        throw new DadosNaoEncontradoException();
    }
    public Notificacao cadastrarNotificacaoConviteParaEquipe(NotificacaoConvite notificacaoConvite) {
        return notificacaoConviteRepository.save(notificacaoConvite);
    }
    public Notificacao cadastrarNotificacaoConviteParaProjeto(NotificacaoConvite notificacaoConvite) {
        return notificacaoConviteRepository.save(notificacaoConvite);
    }
    public Notificacao cadastrarNotificacaoTarefa(NotificacaoTarefa notificacaoConvite) {
        return notificacaoTarefaRepository.save(notificacaoConvite);
    }
    public Notificacao cadastrarNotificacaoProjeto(NotificacaoProjeto notificacaoProjeto) {
        return notificacaoProjetoRepository.save(notificacaoProjeto);
    }
    public Notificacao cadastrarNotificacaoEquipe(NotificacaoEquipe notificacaoEquipe) {
        return notificacaoEquipeRepository.save(notificacaoEquipe);
    }
    public List<Notificacao> buscarPorUsuario(Usuario receptor){
        return notificacaoRepository.findNotificacaosByReceptoresContaining(receptor);
    }
}
