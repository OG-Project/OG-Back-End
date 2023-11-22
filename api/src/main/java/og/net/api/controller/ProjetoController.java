package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.*;
import og.net.api.model.dto.ProjetoCadastroDTO;
import og.net.api.model.dto.ProjetoEdicaoDTO;
import og.net.api.model.entity.Projeto;
import og.net.api.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/projeto")
public class ProjetoController {

    private ProjetoService projetoService;

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarUm(@PathVariable Integer id){
        try {

            return new ResponseEntity<>(projetoService.buscarUm(id),HttpStatus.OK);
        }catch (ProjetoNaoEncontradoException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nome")
    public ResponseEntity<Collection<Projeto>> buscarProjetoNome(@RequestParam String nome){
        try{
            return new ResponseEntity<>(projetoService.buscarProjetosNome(nome),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Projeto>> buscarTodos(){
        try{
            return new ResponseEntity<>(projetoService.buscarTodos(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        projetoService.deletar(id);
    }

    @PostMapping
    public ResponseEntity<Projeto> cadastrar(@RequestBody ProjetoCadastroDTO projetoCadastroDTO){
        try{
            projetoService.cadastrar(projetoCadastroDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (Exception e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Projeto> editar(@RequestBody ProjetoEdicaoDTO projetoEdicaoDTO){
        try {
            projetoService.editar(projetoEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            e.getMessage();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

