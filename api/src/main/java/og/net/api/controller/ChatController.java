package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.ChatNaoEncontradoException;
import og.net.api.model.dto.ChatEquipeDTO;
import og.net.api.model.dto.ChatPessoalDTO;
import og.net.api.model.entity.Chat;
import og.net.api.model.entity.Mensagem;
import og.net.api.service.ChatService;
import og.net.api.service.MensagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/chat")
public class ChatController {
    private ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarChat(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(chatService.buscarUmChat(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Chat>> buscarTodos(){
        return new ResponseEntity<>(chatService.buscarTodos(),HttpStatus.OK);
    }

    @GetMapping("/mensagens/{id}")
    public ResponseEntity<?> buscarMensagensChat(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(chatService.buscarMensagensChat(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pessoal")
    public ResponseEntity<?> criaChatPessoal(@RequestBody ChatPessoalDTO chatPessoalDTO){
        try {
            return new ResponseEntity<>(chatService.criaChatPessoal(chatPessoalDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/equipe/{id}")
    public ResponseEntity<?> buscarChatEquipe(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(chatService.buscarChatEquipe(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/pessoal/{idUsuarioLogado}/{id}")
    public ResponseEntity<Chat> buscarChatPessoal(@PathVariable Integer id, @PathVariable Integer idUsuarioLogado) throws ChatNaoEncontradoException {
        try {
            return new ResponseEntity<>(chatService.buscarChatPessoal(id,idUsuarioLogado), HttpStatus.OK);
        }catch (Exception e){
            throw new ChatNaoEncontradoException();
        }
    }

    @PostMapping("/equipe")
    public ResponseEntity<?> criaChatEquipe(@RequestBody ChatEquipeDTO chatEquipeDTO){
        try {
            return new ResponseEntity<>(chatService.criaChatEquipe(chatEquipeDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deltarChat(@PathVariable Integer id){
        try {
            chatService.deletarChat(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
