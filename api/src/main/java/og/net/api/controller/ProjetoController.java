package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.*;
import og.net.api.model.dto.ProjetoCadastroDTO;
import og.net.api.model.dto.ProjetoEdicaoDTO;
import og.net.api.model.entity.Projeto;
import og.net.api.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "http://localhost:5173")
@Controller
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
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Collection<Projeto>> buscarProjetoNome(@PathVariable String nome){
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
        try {
            projetoService.deletar(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<Projeto> cadastrar(@RequestBody ProjetoCadastroDTO projetoCadastroDTO) throws IOException {
            return new ResponseEntity<>(projetoService.cadastrar(projetoCadastroDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{equipeId}")
    public ResponseEntity<Projeto> cadastrarPelaEquipe(@RequestBody ProjetoCadastroDTO projetoCadastroDTO, Integer equipeId) throws IOException {
        return new ResponseEntity<>(projetoService.cadastrar(projetoCadastroDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Projeto> editar(@RequestBody ProjetoEdicaoDTO projetoEdicaoDTO){
        try {
            return new ResponseEntity<>(projetoService.editar(projetoEdicaoDTO), HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{equipeId}")
    public ResponseEntity<Projeto> editarPelaEquipe(@RequestBody ProjetoEdicaoDTO projetoEdicaoDTO, Integer equipeId){
        try {
            return new ResponseEntity<>(projetoService.editar(projetoEdicaoDTO), HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buscarProjetos/{equipeId}")
    public List<Projeto> buscarProjetosEquipe(@PathVariable Integer equipeId) throws EquipeNaoEncontradaException {
        return projetoService.buscarProjetosEquipes(equipeId);
    }

    @DeleteMapping("/removerProjetoEquipe/{equipeId}/{projetoId}")
    public void removerUsuarioDaEquipe(@PathVariable Integer equipeId, @PathVariable Integer projetoId) throws ProjetoNaoEncontradoException {
        projetoService.removerProjetoDaEquipe( equipeId, projetoId);
    }
//
//    @PatchMapping("/addUser/{projetoId}/{userId}")
//    public void adicionarResponsaveisProjeto(@PathVariable Integer userId, @PathVariable Integer projetoId ) throws ProjetoNaoEncontradoException {
//        projetoService.adicionarResponsavelProjeto(projetoId,userId);
//
//    }

    @DeleteMapping("/deletarPropriedade/{idPropriedade}/{idProjeto}")
    public void deletarPropriedade( @PathVariable Integer idPropriedade, @PathVariable Integer idProjeto) throws ProjetoNaoEncontradoException {
        projetoService.deletarPropriedade(idPropriedade,idProjeto);
    }

    @PatchMapping("/add/{projetoId}/{equipeId}")
    public void adicionarProjetoAEquipe(@PathVariable Integer projetoId, @PathVariable Integer equipeId) throws ProjetoNaoEncontradoException {
        projetoService.adicionarAEquipeAProjeto(projetoId,equipeId);
    }
}

