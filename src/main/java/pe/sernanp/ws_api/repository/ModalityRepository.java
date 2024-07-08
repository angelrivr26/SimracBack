package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.model.Modality;

import java.util.List;

@Repository
@Transactional
public interface ModalityRepository extends JpaRepository<Modality, Integer> {
    @Query(value="select t_mod.srl_id as id, t_mod.var_cod_modalidad as code"
            + " ,t_list.srl_id as typeId, t_list.txt_nom_corto as typeName "
            + " ,t_mod.txt_nom_largo as description "
            + " ,t_mod.txt_base_legal_crea as sustentationDocumentName "
            + " ,t_mod.txt_nom_corto as shortName "
            + " ,t_mod.txt_nom_corto_titulo as titleEnables "
            + " ,t_mod.dte_fec_inicio as validFrom "
            + " ,t_mod.dte_fec_fin as validUntil "
            + " ,t_mod.bol_flg_activo as flagValidity "
            + " ,t_mod.bol_flg_borrador as flagDraft "
            + " ,count(t_mr.srl_id) as requirementCount"
            + " from od.t_modalidad as t_mod "
            + " left join od.t_modalidad_requisito as t_mr on t_mr.int_id_modalidad = t_mod.srl_id"
            + " inner join ge.t_listado_detalle as t_list on t_list.srl_id = t_mod.int_id_tipo"
            + " where case when ?1 > 0 then t_mod.int_id_tipo = ?1 else 1 = 1 end "
            + " and case when ?2 > 0 then t_mod.int_id_estado = ?2 else 1 = 1 end "
            + " and case when ?3 = 2 then 1 = 1 when ?3 = 1 then t_mod.bol_flg_activo = TRUE else t_mod.bol_flg_activo = FALSE end "
            + " and case when ?4 = 2 then 1 = 1 when ?4 = 1 then t_mod.bol_flg_borrador = TRUE else t_mod.bol_flg_borrador = FALSE end "
            + " and t_mod.bol_flg_eliminado = ?5 "
            + " group by t_mod.srl_id, t_list.srl_id"
            + " order by t_mod.srl_id desc ",
            nativeQuery=true)
    Page<ModalityDTO> search(int typeId, int stateId, int flagValidity, int flagDraft, boolean isDeleted, Pageable page);

    @Query(value="select t_mod.srl_id as id, t_mod.var_cod_modalidad as code"
            + " ,t_list.srl_id as typeId, t_list.txt_nom_corto as typeName "
            + " ,t_mod.txt_nom_largo as description "
            + " ,t_mod.txt_base_legal_crea as sustentationDocumentName "
            + " ,t_mod.txt_nom_corto as shortName "
            + " ,t_mod.txt_nom_corto_titulo as titleEnables "
            + " ,t_mod.dte_fec_inicio as validFrom "
            + " ,t_mod.dte_fec_fin as validUntil "
            + " ,t_mod.bol_flg_activo as flagValidity "
            + " ,t_mod.bol_flg_borrador as flagDraft "
            + " ,count(t_mr.srl_id) as requirementCount"
            + " from od.t_modalidad as t_mod "
            + " left join od.t_modalidad_requisito as t_mr on t_mr.int_id_modalidad = t_mod.srl_id"
            + " inner join ge.t_listado_detalle as t_list on t_list.srl_id = t_mod.int_id_tipo"
            + " where case when ?1 > 0 then t_mod.int_id_tipo = ?1 else 1 = 1 end "
            + " and case when ?2 > 0 then t_mod.int_id_estado = ?2 else 1 = 1 end "
            + " and case when ?3 = 2 then 1 = 1 when ?3 = 1 then t_mod.bol_flg_activo = TRUE else t_mod.bol_flg_activo = FALSE end "
            + " and case when ?4 = 2 then 1 = 1 when ?4 = 1 then t_mod.bol_flg_borrador = TRUE else t_mod.bol_flg_borrador = FALSE end "
            + " and t_mod.bol_flg_eliminado = ?5 "
            + " group by t_mod.srl_id, t_list.srl_id"
            + " order by t_mod.srl_id desc ",
            nativeQuery=true)
    List<ModalityDTO> search2(int typeId, int stateId, int flagValidity, int flagDraft, boolean isDeleted);

    List<Modality> findByFlagDraftAndIsDeleted(boolean isDraft, boolean isDeleted);

    List<Modality> findByFlagTupaAndFlagDraftAndIsDeleted(boolean isTupa, boolean isDraft, boolean isDeleted);

    @Modifying
    @Query(value="update od.t_modalidad set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Query(value="select t_mod.* from od.t_modalidad as t_mod"
            + " inner join od.t_modalidad_anp_config as t_cmod on t_cmod.int_id_modalidad = t_mod.srl_id"
            + " where t_mod.int_id_tipo = ?1 "
            + " and t_mod.bol_flg_borrador = ?2 "
            + " and t_mod.bol_flg_eliminado = ?3"
            + " group by t_mod.srl_id ",
            nativeQuery=true)
    List<Modality> listByTypeIdAndAnpConfig(int typeId, boolean isDraft, boolean isDeleted);


    @Query(value="select t_mod.* from od.t_modalidad as t_mod"
            + " where t_mod.int_id_tipo = ?1 "
            + " and t_mod.bol_flg_borrador = ?2 "
            + " and t_mod.bol_flg_eliminado = ?3",
            nativeQuery=true)
    List<Modality> listByTypeId(int typeId, boolean isDraft, boolean isDeleted);

    @Query(value = "SELECT (last_value +1) FROM od.t_modalidad_srl_id_seq", nativeQuery = true)
    public int getCurrentValSequence();

    @Modifying
    @Query(value= "update od.t_modalidad set txt_base_legal_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateDocumetId(int id, String sustentationDocumentId);

    @Query(value= "select txt_base_legal_crea from od.t_modalidad"
            + " where txt_base_legal_ruta = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);
}
