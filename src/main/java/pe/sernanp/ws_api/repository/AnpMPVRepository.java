package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.AnpMPV;

import java.util.List;

@Repository
public interface AnpMPVRepository extends JpaRepository<AnpMPV, Integer> {
    List<AnpMPV> findByProcedureId(int procedureId);
}
