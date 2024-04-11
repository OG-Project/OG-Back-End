package og.net.api.repository;

import og.net.api.model.entity.ProjetoDragAndDrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoDragAndDropRepository extends JpaRepository<ProjetoDragAndDrop, Integer> {


    ProjetoDragAndDrop findProjetoDragAndDropByUsuario_Id(Integer id);
}
