package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.model.entity.Comentario;
import og.net.api.repository.ComentarioRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComentarioService {
    private ComentarioRepository comentarioRepository;

   public Comentario buscarUm(Integer id){
        return comentarioRepository.findById(id).get();
    }
}
