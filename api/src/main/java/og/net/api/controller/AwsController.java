package og.net.api.controller;

import lombok.AllArgsConstructor;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.model.dto.EquipeCadastroDTO;
import og.net.api.model.entity.Equipe;
import og.net.api.service.ImagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequestMapping("/tarefa/imagem")
public class AwsController {
    private ImagemService imagemService;

    @PostMapping("/{id}")
    public void cadastrarFoto(@RequestParam MultipartFile arquivos, @PathVariable Integer id) throws IOException, TarefaInesxistenteException {
        imagemService.uploadFile(arquivos,id);
    }
}
