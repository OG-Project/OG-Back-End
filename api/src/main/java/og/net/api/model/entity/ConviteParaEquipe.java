package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConviteParaEquipe extends Convite{
    @ManyToOne
    private Equipe equipe;
    @OneToMany(cascade = CascadeType.ALL)
    private List<UsuarioPermissao> permissoes;
}
