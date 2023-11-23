package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.UsuarioJaExistenteException;
import og.net.api.model.dto.UsuarioCadastroDTO;
import og.net.api.model.dto.UsuarioEdicaoDTO;
import og.net.api.model.entity.Usuario;
import og.net.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")

@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUm(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(usuarioService.buscarUm(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nome")
    public ResponseEntity<Collection<Usuario>> buscarUsuariosNome(@RequestParam String nome){
        try{
            return new ResponseEntity<>(usuarioService.buscarUsuariosNome(nome),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/username")
    public ResponseEntity<Collection<Usuario>> buscarUsuariosUsername(@RequestParam String username){
        try{
            return new ResponseEntity<>(usuarioService.buscarUsuariosUsername(username),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<Collection<Usuario>> buscarUsuariosEmail(@RequestParam String email){
        try{
            return new ResponseEntity<>(usuarioService.buscarUsuariosEmail(email),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Usuario>> buscarTodos(){
        try{
            return new ResponseEntity<>(usuarioService.buscarTodos(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        usuarioService.deletar(id);
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody UsuarioCadastroDTO usuarioCadastroDTO){
        try{
            usuarioService.cadastrar(usuarioCadastroDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Usuario> editar(@RequestBody UsuarioEdicaoDTO usuarioEdicaoDTO){
        try {
            usuarioService.editar(usuarioEdicaoDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DadosNaoEncontradoException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
