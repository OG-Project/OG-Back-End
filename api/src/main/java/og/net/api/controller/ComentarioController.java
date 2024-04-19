package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.model.entity.Comentario;
import og.net.api.model.entity.Equipe;
import og.net.api.service.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/comentario")
@CrossOrigin(origins = "http://localhost:5173")
public class ComentarioController {

    private ComentarioService comentarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscarUm(@PathVariable Integer id){
        return new ResponseEntity<>(comentarioService.buscarUm(id), HttpStatus.OK);
    }
}
