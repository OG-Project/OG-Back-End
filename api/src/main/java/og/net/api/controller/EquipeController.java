package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.*;
import og.net.api.model.dto.EquipeCadastroDTO;
import og.net.api.model.dto.EquipeEdicaoDTO;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.ProjetoEquipe;
import og.net.api.service.EquipeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/equipe")
@CrossOrigin(origins = "http://localhost:5173")
public class EquipeController {

    private EquipeService equipeService;

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> buscarUm(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(equipeService.buscarUm(id),HttpStatus.OK);
        }catch (EquipeNaoEncontradaException e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nome")
    public ResponseEntity<Collection<Equipe>> buscarEquipesNome(@RequestParam String nome){
        try{
            return new ResponseEntity<>(equipeService.buscarEquipesNome(nome),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Equipe>> buscarTodos(){
        try{
            return new ResponseEntity<>(equipeService.buscarTodos(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{equipeId}")
    public void deletar(@PathVariable Integer equipeId){
        equipeService.deletar(equipeId);
    }

    @DeleteMapping("/{equipeId}/{projetoId}")
    public void deletarProjetoEquipe(@PathVariable Integer equipeId, @PathVariable Integer projetoId){
        equipeService.removerProjetoDaEquipe(equipeId,projetoId);
    }

    @PostMapping
    public ResponseEntity<Equipe> cadastrar(@RequestBody EquipeCadastroDTO equipeCadastroDTO){
        try{

            return new ResponseEntity<>(equipeService.cadastrar(equipeCadastroDTO), HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Equipe> editar(@RequestBody EquipeEdicaoDTO equipeEdicaoDTO){

        try {
            equipeService.editar(equipeEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (DadosNaoEncontradoException e){
            e.getMessage();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EquipeNaoEncontradaException e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/{id}")
    public void cadastrarFoto(@RequestParam MultipartFile foto, @PathVariable Integer id) throws IOException, EquipeNaoEncontradaException {
        equipeService.atualizarFoto(id,foto);
    }


}
