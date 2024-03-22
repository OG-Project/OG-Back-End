package og.net.api.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Propriedade;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisualizacaoEmListaEdicaoDTO implements IDTO{
    private Long id;
    private List<Propriedade> propriedadeVisiveis;
    private Projeto projeto;
}
