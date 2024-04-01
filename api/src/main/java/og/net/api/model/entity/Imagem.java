package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Data
@AllArgsConstructor@NoArgsConstructor
public class Imagem {

    public Imagem(MultipartFile imagem) throws IOException {
        this.nome = imagem.getOriginalFilename();
        this.tipo = imagem.getContentType();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Tarefa tarefa;
    private String nome;
    private String chaveAws;
    private String tipo;
}
