package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.model.dto.TarefaEdicaoDTO;
import og.net.api.model.dto.VisualizacaoEmListaEdicaoDTO;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.Usuario;
import og.net.api.model.entity.VisualizacaoEmLista;
import og.net.api.service.VisualizacaoEmListaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/visualizacaoEmLista")
public class VisualizacaoEmListaController {

    VisualizacaoEmListaService visualizacaoEmListaService;
    @GetMapping()
    public ResponseEntity<VisualizacaoEmLista> buscarUm(@RequestParam Integer projetoId){
        try {
            return new ResponseEntity<>(visualizacaoEmListaService.buscarPorProjeto(projetoId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<VisualizacaoEmLista> editar(@RequestBody VisualizacaoEmListaEdicaoDTO visualizacaoEmListaEdicaoDTO){
        try {
            visualizacaoEmListaService.editar(visualizacaoEmListaEdicaoDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        }catch (DadosNaoEncontradoException e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
