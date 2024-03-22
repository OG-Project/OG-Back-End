package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.*;
import og.net.api.model.dto.TarefaCadastroDTO;
import og.net.api.model.dto.TarefaEdicaoDTO;
import og.net.api.model.entity.Tarefa;
import og.net.api.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private final TarefaService tarefaService;
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
    @GetMapping("/visualizacao")
    public ResponseEntity<Collection<Tarefa>> buscarTarefasPorVisualizacao(@RequestParam String visualizacao){
        try{
            return new ResponseEntity<>(tarefaService.buscarTarefasPorVisualizacao(visualizacao),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    public ResponseEntity<?> buscarTodos(Pageable pageable){
        try{
            if (pageable.getSort()== Sort.unsorted()){
                return new ResponseEntity<>(tarefaService.buscarTodos(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(tarefaService.buscarTodos(pageable), HttpStatus.OK);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        tarefaService.deletar(id);
    }

    @PostMapping("/{projetoId}")
    public ResponseEntity<Tarefa> cadastrar(@RequestBody TarefaCadastroDTO tarefaCadastroDTO, @PathVariable Integer projetoId){
        try{
            tarefaService.cadastrar(tarefaCadastroDTO, projetoId);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Tarefa> editar(@RequestBody TarefaEdicaoDTO tarefaEdicaoDTO){
        try {
            tarefaService.editar(tarefaEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}")
    public void cadastrarFoto(@RequestParam List<MultipartFile> arquivos, @PathVariable Integer id  ) throws IOException, TarefaInesxistenteException {
        tarefaService.atualizarFoto(id,arquivos);
    }
}

