package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.EvaluationEconomic;

@Transactional
@Repository
public interface EvaluationEconomicRepository extends JpaRepository<EvaluationEconomic, Integer> {

    @Query(value="SELECT * FROM od.t_evaluacion_economica WHERE int_id_tramite = :id" , nativeQuery=true)
    EvaluationEconomic listbytramite(int id);

    @Modifying
    @Query(value= "update od.t_evaluacion_economica set var_id_documento_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateDocumetId(int id, String documentRouteId);

    @Query(value= "select var_nom_documento "
            + " from od.t_evaluacion_economica"
            + " where var_id_documento_ruta = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);


}
