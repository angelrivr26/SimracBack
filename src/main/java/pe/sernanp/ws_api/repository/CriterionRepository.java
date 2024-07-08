package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.Criterion;
import java.util.List;

@Repository
@Transactional
public interface CriterionRepository extends JpaRepository<Criterion, Integer> {

    @Query(value="select t.* from od.t_criterio as t where t.int_id_tipo = ?1 and t.int_componente = ?2 ", nativeQuery=true)
    List<Criterion> findBy(int typeId, int component);

    @Query(value="select t.* from od.t_criterio as t where t.int_id_tipo = ?1 and t.int_id_modalidad = ?2", nativeQuery=true)
    List<Criterion> findByTypeAndModality(int typeId, int modalidad);
}
