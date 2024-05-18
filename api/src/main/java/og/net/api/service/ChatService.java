package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.ChatNaoEncontradoException;
import og.net.api.model.dto.ChatEquipeDTO;
import og.net.api.model.dto.ChatPessoalDTO;
import og.net.api.model.entity.Chat;
import og.net.api.model.entity.Mensagem;
import og.net.api.repository.ChatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private ModelMapper modelMapper;
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
}
