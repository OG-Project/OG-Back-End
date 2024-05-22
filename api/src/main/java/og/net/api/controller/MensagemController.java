package og.net.api.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Mensagem;
import og.net.api.service.MensagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/mensagem")
public class MensagemController {
    private MensagemService mensagemService;
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMensagem(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(mensagemService.buscarMensagem(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{idChat}")
    public ResponseEntity<?> criaMensagem(@RequestBody Mensagem mensagem, @PathVariable Integer idChat){
        try {
            return new ResponseEntity<>(mensagemService.enviaMensagem(mensagem, idChat), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletaMensagem(@PathVariable Integer id){
        try {
            mensagemService.deletaMensagem(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

}
