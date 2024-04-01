package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Arquivo extends Valor {

    public Arquivo(MultipartFile arquivo) throws IOException {
        this.nome = arquivo.getOriginalFilename();
        this.dados = arquivo.getBytes();
        this.tipo = arquivo.getContentType();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] dados;
    @ManyToOne
    private  Tarefa tarefa;
    @GeneratedValue(strategy = GenerationType.UUID)
    private String chaveAws;


    @Override
    public Object getValor() {
        return null;
    }

}
