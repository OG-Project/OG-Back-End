package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValorEntidadeTarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @OneToOne
    @JoinColumn(name = "tarefa_id")
    private  Tarefa tarefa;
    @OneToOne
    @JoinColumn(name = "propriedade_id")
    private  Propriedade propriedade;
    private String valor;

}
