package og.net.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fonteCorpo;
    private Double fonteCorpoTamanho;
    private String fonteTitulo;
    private Integer fonteTituloTamanho;
    private String hueCor;
    private String idioma;
    private Boolean isVisualizaProjetos;
    private Boolean isVisualizaEmail;
    private Boolean isVisualizaEquipes;
    private Boolean isVisualizaPerfil;
    private Boolean isDigitarVoz;
    private Boolean isTecladoVirtual;
    private Boolean isLibras;
}
