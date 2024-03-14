package og.net.api.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ValorDeserializer.class)
public abstract  class Valor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    public abstract Object getValor();

}
