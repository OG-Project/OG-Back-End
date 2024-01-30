package og.net.api.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Selecao extends Valor {
    private List<String> valores;

    public Selecao(Long id, List<String> valores) {
        super(id);
        this.valores = valores;
    }

    @Override
    public Object getValor() {
        return valores;
    }
}
