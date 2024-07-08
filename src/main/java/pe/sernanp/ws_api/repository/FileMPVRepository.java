package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.FileMPV;

import java.util.List;

@Repository
public interface FileMPVRepository extends JpaRepository<FileMPV, Integer> {
    List<FileMPV> findByProcedureId(int procedureId);
}
