package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.model.dto.PropriedadeCadastroDTO;
import og.net.api.model.dto.PropriedadeEdicaoDTO;
import og.net.api.model.entity.Propriedade;
import og.net.api.model.entity.Status;
import og.net.api.service.PropriedadeService;
import og.net.api.service.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/status")
public class StatusController {

    private StatusService statusService;

    @GetMapping("/{id}")
    public ResponseEntity<Status> buscarUm(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(statusService.buscarUm(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Status>> buscarTodos(){
        try{
            return new ResponseEntity<>(statusService.buscarTodos(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        statusService.deletar(id);
    }

    @PostMapping
    public ResponseEntity<Propriedade> cadastrar(@RequestBody PropriedadeCadastroDTO propriedadeCadastroDTO){
        try{
            statusService.cadastrar(propriedadeCadastroDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Propriedade> editar(@RequestBody PropriedadeEdicaoDTO propriedadeEdicaoDTO){
        try {
            statusService.editar(propriedadeEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            e.getMessage();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
