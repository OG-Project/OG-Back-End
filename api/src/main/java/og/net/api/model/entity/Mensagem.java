package og.net.api.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToMany
    private List<Usuario> receptor;
    @ManyToOne
    private Usuario criador;
    @OneToMany
    private List<Arquivo> arquivos;
    private String mensagem;
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
