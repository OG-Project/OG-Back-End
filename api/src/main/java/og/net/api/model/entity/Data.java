package og.net.api.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
public class Data extends Valor {
    private LocalDateTime data;

    public Data(Long id, LocalDateTime valor) {
        super(id);
        this.data = valor;
    }


    @Override
    public Object getValor() {
        return data;
    }
}
