package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.model.dto.HistoricoCadastroDTO;
import og.net.api.model.dto.HistoricoEdicaoDTO;
import og.net.api.model.entity.Historico.Historico;
import og.net.api.model.entity.Notificacao.Notificacao;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Tarefa;
import og.net.api.service.HistoricoService;
import og.net.api.service.ProjetoService;
import og.net.api.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin
@Controller
@RequestMapping("/historico")
public class HistoricoController {
    private HistoricoService historicoService;
    private ProjetoService projetoService;
    private TarefaService tarefaService;
    @GetMapping("/{id}")
    public ResponseEntity<Historico> buscarUm(@PathVariable Integer id){
        try {

            return new ResponseEntity<>(historicoService.buscarUm(id), HttpStatus.OK);
        }catch (Exception e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Historico>> buscarTodos(){
        try{
            return new ResponseEntity<>(historicoService.buscarTodos(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Historico> cadastrar(@RequestBody HistoricoCadastroDTO historicoCadastroDTO){
        try{

            return new ResponseEntity<>(historicoService.cadastrar(historicoCadastroDTO), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Historico> editar(@RequestBody HistoricoEdicaoDTO historicoEdicaoDTO){
        try {
            historicoService.editar(historicoEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (DadosNaoEncontradoException e){
            e.getMessage();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<Collection<Historico>> buscarHistoricosPorProjeto(@PathVariable Integer projetoId){
        try{
            Projeto projeto = projetoService.buscarUm(projetoId);
            return new ResponseEntity<>(historicoService.buscarPorProjeto(projeto), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ProjetoNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/tarefa/{tarefaId}")
    public ResponseEntity<Collection<Historico>> buscarHistoricosPorTarefa(@PathVariable Integer tarefaId){
        try{
            Tarefa tarefa = tarefaService.buscarUm(tarefaId);
            return new ResponseEntity<>(historicoService.buscarPorTarefa(tarefa), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (TarefaInesxistenteException e) {
            throw new RuntimeException(e);
        }
    }
}
