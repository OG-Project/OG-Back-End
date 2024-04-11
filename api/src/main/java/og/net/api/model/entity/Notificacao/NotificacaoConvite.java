package og.net.api.model.entity.Notificacao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Convite;
import og.net.api.model.entity.ConviteParaEquipe;
import og.net.api.model.entity.ConviteParaProjeto;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoConvite extends Notificacao{
    @OneToOne(cascade = CascadeType.ALL)
    private ConviteParaProjeto conviteParaProjeto;
    @OneToOne(cascade = CascadeType.ALL)
    private ConviteParaEquipe conviteParaEquipe;
}
