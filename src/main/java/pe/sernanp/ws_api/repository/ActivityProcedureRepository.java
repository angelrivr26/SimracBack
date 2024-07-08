package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.ActivityProcedure;

import java.util.List;

@Repository
public interface ActivityProcedureRepository extends JpaRepository<ActivityProcedure, Integer> {

    @Query(value = "SELECT * FROM od.t_tramite_actividad WHERE int_id_tramite = :id", nativeQuery = true)
    List<ActivityProcedure> listIdProcedure(int id);
}
