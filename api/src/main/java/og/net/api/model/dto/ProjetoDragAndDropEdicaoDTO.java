package og.net.api.model.dto;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Usuario;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjetoDragAndDropEdicaoDTO {

    private Integer id;
    private List<Projeto> projetosUrgentes;
    private List<Projeto> projetosProntos;
    private List<Projeto> meusProjetos;
    private List<Projeto> projetosNaoIniciados;
    private Usuario usuario;
}
