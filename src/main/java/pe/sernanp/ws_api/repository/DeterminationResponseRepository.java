package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.DeterminationResponse;

import java.util.List;

@Repository
@Transactional
public interface DeterminationResponseRepository extends JpaRepository<DeterminationResponse, Integer> {

    @Query(value="select t.* from od.t_determinacion_respuesta as t where t.int_id_tramite = ?1", nativeQuery=true)
    List<DeterminationResponse> findBy(int procedureId);

    int deleteByProcedureId(int procedureId);
}
