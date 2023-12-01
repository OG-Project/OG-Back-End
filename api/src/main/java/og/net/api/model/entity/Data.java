package og.net.api.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Data extends Valor {
    private LocalDate data;

    public Data(Long id, LocalDate valor) {
        super(id);
        this.data = valor;
    }


    @Override
    public Object getValor() {
        return data;
    }
}
