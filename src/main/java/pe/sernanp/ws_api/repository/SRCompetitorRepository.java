package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.SRCompetitor;

import java.util.List;

@Repository
public interface SRCompetitorRepository extends JpaRepository<SRCompetitor,Integer> {

    List<SRCompetitor> findBySupervisionRecordId(int supervisionRecordId);
}
