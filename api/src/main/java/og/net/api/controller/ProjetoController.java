package og.net.api.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import og.net.api.exception.*;
import og.net.api.model.dto.ProjetoCadastroDTO;
import og.net.api.model.dto.ProjetoEdicaoDTO;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.ProjetoEquipe;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.Usuario;
import og.net.api.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequestMapping("/projeto")
public class ProjetoController {

    @NonNull
    private ProjetoService projetoService;

    @GetMapping("/id")
    public ResponseEntity<Projeto> buscarUm(@RequestParam Integer id){
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
            projetoService.cadastrar(projetoCadastroDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Projeto> editar(@RequestBody ProjetoEdicaoDTO projetoEdicaoDTO){
        try {
            projetoService.editar(projetoEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/add/{projetoId}")
    public void adicionarAEquipe(
            @PathVariable Integer projetoId,
            @RequestBody List<Integer> ids) throws ProjetoNaoEncontradoException {
        projetoService.adicionarAProjeto(projetoId, ids);
    }

    @GetMapping("/buscarProjetos/{equipeId}")
    public List<Projeto> buscarProjetosEquipe(@PathVariable Integer equipeId) throws EquipeNaoEncontradaException {
        return projetoService.buscarProjetosEquipes(equipeId);
    }
}

