package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.*;
import og.net.api.model.dto.TarefaCadastroDTO;
import og.net.api.model.dto.TarefaEdicaoDTO;
import og.net.api.model.entity.Tarefa;
import og.net.api.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private TarefaService tarefaService;

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarUm(@PathVariable Integer id){
        try {

            return new ResponseEntity<>(tarefaService.buscarUm(id),HttpStatus.OK);
        }catch (TarefaInesxistenteException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nome")
    public ResponseEntity<Collection<Tarefa>> buscarTarefasNome(@RequestParam String nome){
        try{
            return new ResponseEntity<>(tarefaService.buscarTarefasNome(nome),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ativa")
    public ResponseEntity<Collection<Tarefa>> buscarTarefasPorAtivas(@RequestParam Boolean ativo){
        try{
            return new ResponseEntity<>(tarefaService.buscarTarefasPorAtivas(ativo),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Tarefa>> buscarTodos(){
        try{
            return new ResponseEntity<>(tarefaService.buscarTodos(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        tarefaService.deletar(id);
    }

    @PostMapping
    public ResponseEntity<Tarefa> cadastrar(@RequestBody TarefaCadastroDTO tarefaCadastroDTO){
        try{
            tarefaService.cadastrar(tarefaCadastroDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (Exception e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Tarefa> editar(@RequestBody TarefaEdicaoDTO tarefaEdicaoDTO){
        try {
            tarefaService.editar(tarefaEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            e.getMessage();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

