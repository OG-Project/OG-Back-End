package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.model.dto.ProjetoDragAndDropEdicaoDTO;
import og.net.api.model.entity.Projeto;
import og.net.api.model.entity.ProjetoDragAndDrop;
import og.net.api.model.entity.Usuario;
import og.net.api.repository.ProjetoDragAndDropRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjetoDragAndDropService {

    private ProjetoDragAndDropRepository projetoDragAndDropRepository;

    public ProjetoDragAndDrop cadastrarListaProjeto(ProjetoDragAndDrop projetoDragAndDrop){
        return projetoDragAndDropRepository.save(projetoDragAndDrop);
    }

    public ProjetoDragAndDrop atualizarListaProjeto(ProjetoDragAndDropEdicaoDTO dto){
        ProjetoDragAndDrop projetoDragAndDrop = new ProjetoDragAndDrop();
        BeanUtils.copyProperties(dto, projetoDragAndDrop);
        return projetoDragAndDropRepository.save(projetoDragAndDrop);
    }

    public ProjetoDragAndDrop buscar(Integer id){
        return projetoDragAndDropRepository.findProjetoDragAndDropByUsuario_Id(id);
    }


}
