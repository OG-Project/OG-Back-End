package og.net.api.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Numero extends Valor {
    private Double numero;


    public Numero(Long id, Double valor) {
        super(id);
        this.numero = valor;
    }


    @Override
    public Object getValor() {
        return numero;
    }
}
