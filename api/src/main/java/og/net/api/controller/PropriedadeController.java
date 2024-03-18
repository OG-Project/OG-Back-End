package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.model.dto.PropriedadeCadastroDTO;
import og.net.api.model.dto.PropriedadeEdicaoDTO;
import og.net.api.model.entity.Propriedade;
import og.net.api.service.PropriedadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/propriedade")
public class PropriedadeController {

    private PropriedadeService propriedadeService;

    @GetMapping("/{id}")
    public ResponseEntity<Propriedade> buscarUm(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(propriedadeService.buscarUm(id),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping
    public ResponseEntity<Collection<Propriedade>> buscarTodos(){
        try{
            return new ResponseEntity<>(propriedadeService.buscarTodos(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        propriedadeService.deletar(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Propriedade> cadastrar(@RequestBody PropriedadeCadastroDTO propriedadeCadastroDTO, @PathVariable Integer projetoId){
        try{
            propriedadeService.cadastrar(propriedadeCadastroDTO, projetoId);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<Propriedade> editar(@RequestBody PropriedadeEdicaoDTO propriedadeEdicaoDTO){
        try {
            propriedadeService.editar(propriedadeEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            e.getMessage();
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
