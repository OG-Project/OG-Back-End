package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatusEdicaoDTO implements IDTO{

    private Integer id;
    private String nome;
    private String cor;
}
