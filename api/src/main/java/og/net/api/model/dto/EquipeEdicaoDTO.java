package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.ProjetoEquipe;
import og.net.api.model.entity.Usuario;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipeEdicaoDTO implements IDTO {

    private Integer id;
    private String nome;
    private String descricao;

    public EquipeEdicaoDTO(Equipe equipe) {
        this.id = equipe.getId();
        this.nome = equipe.getNome();
        this.descricao = equipe.getDescricao();
    }
}
