package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.EvaluationCompatible;

@Transactional
@Repository
public interface EvaluationCompatibleRepository extends JpaRepository<EvaluationCompatible, Integer> {
    @Query(value="SELECT * FROM od.t_evaluacion_compatible WHERE int_id_tramite = :id" , nativeQuery=true)
    EvaluationCompatible listbytramite(int id);

    @Modifying
    @Query(value= "update od.t_evaluacion_compatible set var_id_documento_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateDocumetId(int id, String documentRouteId);

    @Query(value= "select var_nom_documento "
            + " from od.t_evaluacion_resultado"
            + " where var_id_documento_ruta = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);
}
