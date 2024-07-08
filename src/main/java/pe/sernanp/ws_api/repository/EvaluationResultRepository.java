package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.EvaluationResult;

@Repository
public interface EvaluationResultRepository extends JpaRepository<EvaluationResult, Integer> {

    @Query(value="SELECT * FROM od.t_evaluacion_resultado WHERE int_id_tramite = :id" , nativeQuery=true)
    EvaluationResult listbytramite(int id);

}
