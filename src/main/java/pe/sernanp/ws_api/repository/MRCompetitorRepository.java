package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.MRCompetitor;

import java.util.List;

@Repository
public interface MRCompetitorRepository extends JpaRepository<MRCompetitor,Integer> {

    List<MRCompetitor> findByMonitoringRecordId(int monitoringRecordId);
}
