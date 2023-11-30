package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.serializer.Deserializer;


@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract  class Tipo  {
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    private Long id;
}
