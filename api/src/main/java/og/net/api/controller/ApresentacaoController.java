package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.service.ApresentacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/apresentacao")
@AllArgsConstructor
public class ApresentacaoController {
    private final ApresentacaoService apresentacaoService;

    @PostMapping
    public ResponseEntity<?> padraoApresentacao(){
        try {
            apresentacaoService.criaObjetosPadraoApresentacao();
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
