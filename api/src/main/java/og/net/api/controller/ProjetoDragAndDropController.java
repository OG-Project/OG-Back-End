package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.model.dto.ProjetoDragAndDropEdicaoDTO;
import og.net.api.model.entity.ProjetoDragAndDrop;
import og.net.api.service.ProjetoDragAndDropService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/projetoDrag")
public class ProjetoDragAndDropController {

    private ProjetoDragAndDropService projetoDragAndDropService;

    @PostMapping
    public ResponseEntity<ProjetoDragAndDrop> cadastrarLista(@RequestBody ProjetoDragAndDrop projetoDragAndDrop){
        try{
            projetoDragAndDropService.cadastrarListaProjeto(projetoDragAndDrop);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<ProjetoDragAndDropEdicaoDTO> atualizarLista(@RequestBody ProjetoDragAndDropEdicaoDTO projetoDragAndDropEdicaoDTO){
        try{
           projetoDragAndDropService.atualizarListaProjeto(projetoDragAndDropEdicaoDTO);
           return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDragAndDrop> buscar(@PathVariable Integer id){
        try{
            return new ResponseEntity<>(projetoDragAndDropService.buscar(id), HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
