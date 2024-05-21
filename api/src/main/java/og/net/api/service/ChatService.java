package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.ChatNaoEncontradoException;
import og.net.api.model.dto.ChatEquipeDTO;
import og.net.api.model.dto.ChatPessoalDTO;
import og.net.api.model.entity.Chat;
import og.net.api.model.entity.Mensagem;
import og.net.api.model.entity.Usuario;
import og.net.api.repository.ChatRepository;
import og.net.api.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private ModelMapper modelMapper;
    private UsuarioRepository usuarioRepository;
    public Chat criaChatEquipe(ChatEquipeDTO chatEquipeDTO){
        Chat chat = new Chat();
        modelMapper.map(chatEquipeDTO,chat);
        return chatRepository.save(chat);
    }

    public Chat criaChatPessoal(ChatPessoalDTO chatPessoalDTO){
        Chat chat = new Chat();
        modelMapper.map(chatPessoalDTO,chat);
        return chatRepository.save(chat);
    }

    public List<Chat> buscarTodos(){
        return chatRepository.findAll();
    }

    public Chat buscarChatEquipe(Integer id){
        return chatRepository.findByEquipe_Id(id);
    }

    public void deletarChat(Integer id){
        try {
            Chat chat = buscarUmChat(id);
            chatRepository.delete(chat);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Chat buscarUmChat(Integer id) throws ChatNaoEncontradoException {
        try {
           return chatRepository.findById(id).get();
        }catch (Exception e){
            throw new ChatNaoEncontradoException();
        }
    }

    public List<Mensagem> buscarMensagensChat(Integer id) throws ChatNaoEncontradoException {
        try {
            Chat chat = buscarUmChat(id);
            return chat.getMensagens();
        }catch (Exception e){
            throw new ChatNaoEncontradoException();
        }
    }

    public Chat buscarChatPessoal(Integer id, Integer idUsuarioLogado) throws ChatNaoEncontradoException {
        Usuario usuario = usuarioRepository.findById(id).get();
        Usuario usuarioLogado = usuarioRepository.findById(idUsuarioLogado).get();
        Boolean primeiroUsuario = false;
        Boolean segundoUsuario = false;

        for (Chat chat: buscarTodos()){
            for (Usuario usuario1:chat.getUsuarios()){
                if(usuario1.equals(usuario)){
                    primeiroUsuario = true;
                }
                if(usuario1.equals(usuarioLogado)){
                    segundoUsuario = true;
                }
            }
            if (primeiroUsuario && segundoUsuario) {
                return chat;
            }
        }
        throw new ChatNaoEncontradoException();
    }
}
