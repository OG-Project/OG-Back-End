package og.net.api.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.service.TarefaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/aws")
public class AwsController {

    private TarefaService tarefaService;

    @PostMapping
    public void uploadFile(@RequestParam MultipartFile arquivos) throws IOException, TarefaInesxistenteException {
        tarefaService.uploadFile(arquivos);
    }
}
