package og.net.api.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Selecao extends Tipo{
    private List<String> valores;


}
