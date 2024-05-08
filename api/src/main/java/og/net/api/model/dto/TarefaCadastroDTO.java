package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaCadastroDTO implements IDTO{

    private String nome;
    private String descricao;
    private Boolean ativo;
    private LocalDateTime data_criacao;
    private String cor;
    private List<ValorPropriedadeTarefa> valorPropriedadeTarefas;
    private Status status;
    private List<SubTarefa> subTarefas;

    public LocalDateTime getData_criacao() {
        return data_criacao = LocalDateTime.now();
    }
}
