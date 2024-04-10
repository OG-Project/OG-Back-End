package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.model.Notificacao.*;
import og.net.api.model.dto.*;
import og.net.api.model.entity.Usuario;
import og.net.api.service.NotificacaoService;
import og.net.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequestMapping("/notificacao")
public class NoticacaoController {
    private NotificacaoService notificacaoService;
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscarUm(@PathVariable Integer id){
        try {

            return new ResponseEntity<>(notificacaoService.buscarUm(id), HttpStatus.OK);
        }catch (Exception e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Notificacao>> buscarTodos(){
        try{
            return new ResponseEntity<>(notificacaoService.buscarTodos(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{notificacaoId}")
    public void deletar(@PathVariable Integer notificacaoId){
        notificacaoService.deletar(notificacaoId);
    }

    @PostMapping("/convite")
    public ResponseEntity<Notificacao> cadastrarNotificacaoConvite(@RequestBody NotificacaoConvite notificacaoConvite){
        try{

            return new ResponseEntity<>(notificacaoService.cadastrarNotificacaoConvite(notificacaoConvite), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/tarefa")
    public ResponseEntity<Notificacao> cadastrarNotificacaoTarefa(@RequestBody NotificacaoTarefa notificacaoTarefa){
        try{

            return new ResponseEntity<>(notificacaoService.cadastrarNotificacaoTarefa(notificacaoTarefa), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/projeto")
    public ResponseEntity<Notificacao> cadastrarNotificacaoProjeto(@RequestBody NotificacaoProjeto notificacaoProjeto){
        try{

            return new ResponseEntity<>(notificacaoService.cadastrarNotificacaoProjeto(notificacaoProjeto), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/equipe")
    public ResponseEntity<Notificacao> cadastrarNotificacaoEquipe(@RequestBody NotificacaoEquipe notificacaoEquipe){
        try{

            return new ResponseEntity<>(notificacaoService.cadastrarNotificacaoEquipe(notificacaoEquipe), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PostMapping
    public ResponseEntity<Notificacao> cadastrar(@RequestBody NotificacaoCadastroDTO notificacaoCadastroDTO){
        try{

            return new ResponseEntity<>(notificacaoService.cadastrar(notificacaoCadastroDTO), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Notificacao> editar(@RequestBody NotificacaoEdicaoDTO notificacaoEdicaoDTO){

        try {
            notificacaoService.editar( notificacaoEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (DadosNaoEncontradoException e){
            e.getMessage();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/buscar/{receptorId}")
    public ResponseEntity<Collection<Notificacao>> buscarNotificacoesPorUsuario(@PathVariable Integer receptorId){
        try{
            Usuario receptor = usuarioService.buscarUm(receptorId);
            return new ResponseEntity<>(notificacaoService.buscarPorUsuario(receptor), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
