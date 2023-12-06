package og.net.api.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ValorDeserializer.class)
public abstract  class Valor {
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Long id;

    public abstract Object getValor();

}
