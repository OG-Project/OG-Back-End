package og.net.api.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import og.net.api.exception.MensagemNaoEncontradaException;
import og.net.api.exception.UsuarioInesxistenteException;
import og.net.api.model.entity.Chat;
import og.net.api.model.entity.Mensagem;
import og.net.api.repository.ChatRepository;
import og.net.api.repository.MensagemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor

public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final UsuarioService usuarioService;
    private final ChatService chatService;
    private final ChatRepository chatRepository;
    public Mensagem enviaMensagem(Mensagem mensagem, Integer idChat){
        Chat chat= null;
        List<Mensagem> mensagens = new ArrayList<>();
        try {
            chat = chatService.buscarUmChat(idChat);
            usuarioService.buscarUm(mensagem.getCriador().getId());
            mensagem.getReceptor().forEach(usuario -> usuarioService.buscarUm(usuario.getId()));
        }catch (Exception e){
            e.printStackTrace();
        }
        chat.getMensagens().add(mensagem);
        mensagens = chat.getMensagens();
        chat.setMensagens(mensagens);
        Mensagem mensagem1 =mensagemRepository.save(mensagem);
        chatRepository.save(chat);
        return mensagem1;
    }


    public Mensagem buscarMensagem(Integer id) throws MensagemNaoEncontradaException {
        try{
            return mensagemRepository.findById(id).get();
        }catch (Exception e){
            throw new MensagemNaoEncontradaException();
        }
    }

    public void deletaMensagem(Integer id){
        try {
            Mensagem mensagem =buscarMensagem(id);
            mensagemRepository.delete(mensagem);
        }catch (MensagemNaoEncontradaException e){
            e.printStackTrace();
        }
    }
}
