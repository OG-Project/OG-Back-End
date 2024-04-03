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

    @PatchMapping("/add/{projetoId}/{equipeId}")
    public void adicionarAEquipeProjeto(@PathVariable Integer projetoId, @PathVariable Integer equipeId) throws ProjetoNaoEncontradoException {
        projetoService.adicionarAEquipeAProjeto(projetoId,equipeId);
    }

    @GetMapping("/buscarProjetos/{equipeId}")
    public List<Projeto> buscarProjetosEquipe(@PathVariable Integer equipeId) throws EquipeNaoEncontradaException {
        return projetoService.buscarProjetosEquipes(equipeId);
    }

    @DeleteMapping("/removerProjetoEquipe/{equipeId}/{projetoId}")
    public void removerUsuarioDaEquipe(@PathVariable Integer equipeId, @PathVariable Integer projetoId) throws ProjetoNaoEncontradoException {
        projetoService.removerProjetoDaEquipe( equipeId, projetoId);
    }

    @PatchMapping("/adicionarUmaEquipe/{id}")
    public void adicionarUmaEquipe(@PathVariable Integer id, @RequestBody List<ProjetoEquipe> projetoEquipes) throws DadosNaoEncontradoException, EquipeNaoEncontradaException {
        projetoService.atualizarUmaEquipeNoProjeto(projetoEquipes,id);
    }
}

