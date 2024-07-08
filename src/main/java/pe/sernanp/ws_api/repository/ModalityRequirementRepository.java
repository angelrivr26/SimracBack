package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.ModalityRequirement;

import java.util.List;

@Repository
@Transactional
public interface ModalityRequirementRepository extends JpaRepository<ModalityRequirement, Integer> {
    List<ModalityRequirement> findByModalityIdAndIsDeleted(int modalityId, boolean isDeleted);

    List<ModalityRequirement> findByIsDeleted(boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_modalidad_requisito set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_modalidad_requisito set txt_requisito_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateDocumetId(int id, String documentRouteId);

    @Modifying
    @Query(value= "update od.t_modalidad_requisito set var_ruta_plantilla = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateTemplateId(int id, String templateRouteId);

    @Query(value= "select txt_nom_documento from od.t_modalidad_requisito"
            + " where txt_requisito_ruta = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);
}
