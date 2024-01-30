package og.net.api.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Texto extends Valor {
    private String texto;

    public Texto(Long id, String texto) {
        super(id);
        this.texto = texto;
    }


    @Override
    public Object getValor() {
        return texto;
    }
}
