package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.model.dto.*;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Notificacao;
import og.net.api.model.entity.Usuario;
import og.net.api.service.NotificacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequestMapping("/notificacao")
public class NoticacaoController {
    private NotificacaoService notificacaoService;

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
            notificacaoService.editar((IDTO) notificacaoEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (DadosNaoEncontradoException e){
            e.getMessage();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/buscar")
    public ResponseEntity<Collection<Notificacao>> buscarNotificacoesPorUsuario(@RequestBody List<Usuario> receptores){
        try{
            return new ResponseEntity<>(notificacaoService.buscarPorUsuario(receptores), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
