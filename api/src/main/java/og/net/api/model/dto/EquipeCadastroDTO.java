package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.Arquivo;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipeCadastroDTO implements IDTO {

    private String nome;
    private String descricao;
    private Arquivo foto;
    private Usuario criador;

    public void setFoto(MultipartFile foto) throws IOException {
        Arquivo a = new Arquivo();
        a.setDados(foto.getBytes());
        a.setNome(foto.getOriginalFilename());
        a.setTipo(foto.getContentType());
        this.foto = a;
    }

}
