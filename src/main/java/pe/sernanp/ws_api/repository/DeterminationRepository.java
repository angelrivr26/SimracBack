package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.Criterion;
import pe.sernanp.ws_api.model.Determination;

import java.util.List;

@Repository
@Transactional
public interface DeterminationRepository extends JpaRepository<Determination, Integer> {

    @Query(value="select t.* from od.t_determinacion as t where t.int_id_tipo = ?1 and t.int_id_modalidad = ?2", nativeQuery=true)
    List<Determination> findByTypeAndModality(int typeId, int modalidad);
}
