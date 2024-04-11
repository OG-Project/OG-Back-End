package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjetoDragAndDrop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany
    private List<Projeto> projetosUrgentes;
    @OneToMany
    private List<Projeto> projetosProntos;
    @OneToMany
    private List<Projeto> meusProjetos;
    @OneToMany
    private List<Projeto> projetosNaoIniciados;
    @OneToOne
    private Usuario usuario;
}
