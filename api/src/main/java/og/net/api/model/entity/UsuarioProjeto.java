package og.net.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private Integer idResponsavel;

    @Enumerated(EnumType.ORDINAL)
    @JoinColumn(name = "permissao_id")
    private List<Permissao> permissao = List.of(Permissao.CRIAR,Permissao.EDITAR,Permissao.PATCH, Permissao.VER);



}
