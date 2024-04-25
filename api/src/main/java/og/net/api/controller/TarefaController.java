package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.*;
import og.net.api.model.dto.TarefaCadastroDTO;
import og.net.api.model.dto.TarefaEdicaoDTO;
import og.net.api.model.entity.Comentario;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.ValorPropriedadeTarefa;
import og.net.api.service.ProjetoService;
import og.net.api.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
            Tarefa tarefa = tarefaService.buscarUm(id);
            tarefa = atualizarComentario(tarefa);
            return new ResponseEntity<>(tarefa,HttpStatus.OK);
        }catch (TarefaInesxistenteException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nome")
    public ResponseEntity<Collection<Tarefa>> buscarTarefasNome(@RequestParam String nome){
        try{
            List<Tarefa> tarefas = tarefaService.buscarTarefasNome(nome);
            for(Tarefa tarefa:tarefas){
                atualizarComentario(tarefa);
            }
            return new ResponseEntity<>(tarefas,HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/visualizacao")
    public ResponseEntity<Collection<Tarefa>> buscarTarefasPorVisualizacao(@RequestParam String visualizacao){
        try{
            List<Tarefa> tarefas = tarefaService.buscarTarefasPorVisualizacao(visualizacao);
            for(Tarefa tarefa:tarefas){
                atualizarComentario(tarefa);
            }
            return new ResponseEntity<>(tarefas,HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    public ResponseEntity<?> buscarTodos(Pageable pageable){
        try{
            if (pageable.getSort()== Sort.unsorted()){
                List<Tarefa> tarefas = tarefaService.buscarTodos();
                for(Tarefa tarefa:tarefas){
                    atualizarComentario(tarefa);
                }
                return new ResponseEntity<>(tarefas, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(tarefaService.buscarTodos(pageable), HttpStatus.OK);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) throws ProjetoNaoEncontradoException, TarefaInesxistenteException {

        tarefaService.deletar(id);
    }

    @PostMapping("/{projetoId}")
    public ResponseEntity<?> cadastrar(@RequestBody TarefaCadastroDTO tarefaCadastroDTO, @PathVariable Integer projetoId){
        try{
             return new ResponseEntity<>(tarefaService.cadastrar(tarefaCadastroDTO, projetoId), HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
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
    @PutMapping("/valorPropriedadeTarefa/{id}")
    public ResponseEntity<Tarefa> editarValorPropriedadeTarefa(@PathVariable Integer id, @RequestBody List<ValorPropriedadeTarefa> valorPropriedadeTarefas){
        try {
            tarefaService.editarValorPropriedadetarefa(id,valorPropriedadeTarefas);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}")
    public void cadastrarFoto(@RequestParam MultipartFile arquivo, @PathVariable Integer id  ) throws IOException, TarefaInesxistenteException {
        tarefaService.atualizarFoto(id,arquivo);
    }

    @DeleteMapping("/arquivos/{id}")
    public void deletarTodosOsArquivos(@PathVariable Integer id) throws TarefaInesxistenteException, IOException {
        tarefaService.deletaListaDeArquivos(id);
    }
    private Tarefa atualizarComentario(Tarefa tarefa){
        ArrayList comentarios = new ArrayList();
        for(Comentario comentario:tarefa.getComentarios()){
            comentarios.add(comentario.getId());
        }
        tarefa.setComentarios(comentarios);
        return tarefa;
    }
}

