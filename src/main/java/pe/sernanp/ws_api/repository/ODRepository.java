package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.ODDTO;
import pe.sernanp.ws_api.model.OD;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface ODRepository extends JpaRepository<OD, Integer> {

    @Query(value="select t_od.srl_id as id "
            + " ,t_od.var_cod as code "
            + " ,t_ld1.srl_id as typeId "
            + " ,t_ld1.txt_nom_corto as typeName "
            + " ,t_od.var_ids_anp_config as anpCodes"
            + " ,t_od.var_ids_sector as sectorCodes"
            + " ,t_od.var_ids_poligono as polygonCodes"
            + " ,t_od.txt_nom_anps as anpConfigs"
            + " ,t_od.txt_descripcion as description "
            + " ,coalesce(t_od.txt_num_titulo, '') as titleNumber "
            + " ,t_od.txt_nom_recursos as resourceAnpConfigs"
            + " ,t_ben.srl_id as beneficiaryId "
            + " ,t_ben.txt_nom as beneficiaryName "
            + " ,TO_CHAR(t_od.dte_fec_firma, 'yyyy-mm-dd') as signatureDate "
            + " ,t_ld2.srl_id as stateId "
            + " ,t_ld2.txt_nom_corto as stateName "
            + " ,t_od.bol_flg_plan as flagPlan "
            + " ,t_m.txt_nom_largo as modalityDescription "
            + " ,(t_m.var_cod_modalidad || '-' || t_m.txt_nom_corto || '-' || t_m.var_siglas) as modalityCode2 "
            + " from od.t_od as t_od "
            + "	left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
            + "	left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
            + "	left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
            + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
            + "	where UPPER(t_od.var_cod) like '%' || UPPER(?1) || '%' "
            + " and (case when ?2 > 0 then t_od.int_id_estado = ?2 else 1 = 1 end)  "
            + " and (case when ?3 > 0 then t_od.int_id_tipo = ?3 else 1 = 1 end) "
            + " and (case when ?4 = '' then 1 = 1 else string_to_array(t_od.var_ids_anp_config, ',') && cast(?4 as text[]) end) "
            + " and (case when ?5 = 0 then 1 = 1 else ?5 = t_od.int_id_modalidad end) "
            + " and (case when ?6 = '' then 1 = 1 else string_to_array(t_od.var_ids_recursos_anp, ',') && cast(?6 as text[]) end)"
            + " and (case when ?7 > 0 then t_od.int_id_beneficiario = ?7 else 1 = 1 end) "
            + " and t_od.bol_flg_eliminado = false "
            + " and t_od.txt_descripcion is not null "
            + " order by t_od.srl_id desc",
            nativeQuery=true)
    Page<ODDTO> search(String code, int stateId, int typeId, String anpCode, int modalityId, String resourceIds, int beneficiaryId, Pageable page);

    @Query(value="select t_od.srl_id as id "
            + " ,t_od.var_cod as code "
            + " ,t_ld1.srl_id as typeId "
            + " ,t_ld1.txt_nom_corto as typeName "
            + " ,t_od.var_ids_anp_config as anpCodes"
            + " ,t_od.var_ids_sector as sectorCodes"
            + " ,t_od.var_ids_poligono as polygonCodes"
            + " ,t_od.txt_nom_anps as anpConfigs"
            + " ,t_od.txt_descripcion as description "
            + " ,coalesce(t_od.txt_num_titulo, '') as titleNumber "
            + " ,t_od.txt_nom_recursos as resourceAnpConfigs"
            + " ,t_ben.srl_id as beneficiaryId "
            + " ,t_ben.txt_nom as beneficiaryName "
            + " ,TO_CHAR(t_od.dte_fec_firma, 'yyyy-mm-dd') as signatureDate "
            + " ,t_ld2.srl_id as stateId "
            + " ,t_ld2.txt_nom_corto as stateName "
            + " ,t_od.bol_flg_plan as flagPlan "
            + " ,t_m.txt_nom_largo as modalityDescription "
            + " ,(t_m.var_cod_modalidad || '-' || t_m.txt_nom_corto || '-' || t_m.var_siglas) as modalityCode2 "
            + " from od.t_od as t_od "
            + "	left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
            + "	left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
            + "	left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
            + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
            + "	where UPPER(t_od.var_cod) like '%' || UPPER(?1) || '%' "
            + " and (case when ?2 > 0 then t_od.int_id_estado = ?2 else 1 = 1 end)  "
            + " and (case when ?3 > 0 then t_od.int_id_tipo = ?3 else 1 = 1 end) "
            + " and (case when ?4 = '' then 1 = 1 else string_to_array(t_od.var_ids_anp_config, ',') && cast(?4 as text[]) end) "
            + " and (case when ?5 = 0 then 1 = 1 else ?5 = t_od.int_id_modalidad end) "
            + " and (case when ?6 = '' then 1 = 1 else string_to_array(t_od.var_ids_recursos_anp, ',') && cast(?6 as text[]) end)"
            + " and (case when ?7 > 0 then t_od.int_id_beneficiario = ?7 else 1 = 1 end) "
            + " and t_od.bol_flg_eliminado = false "
            + " and t_od.txt_descripcion is not null "
            + " order by t_od.srl_id desc",
            nativeQuery=true)
    List<ODDTO> search(String code, int stateId, int typeId, String anpCode, int modalityId, String resourceIds, int beneficiaryId);

    @Query(value="select t_od.srl_id as id "
            + " ,t_od.var_cod as code "
            + " ,t_ld1.srl_id as typeId "
            + " ,t_ld1.txt_nom_corto as typeName "
            + " ,t_od.var_ids_anp_config as anpCodes"
            + " ,t_od.var_ids_sector as sectorCodes"
            + " ,t_od.var_ids_poligono as polygonCodes"
            + " ,t_od.txt_nom_anps as anpConfigs"
            + " ,t_od.txt_descripcion as description "
            + " ,coalesce(t_od.txt_num_titulo, '') as titleNumber "
            + " ,t_od.txt_nom_recursos as resourceAnpConfigs"
            + " ,t_ben.srl_id as beneficiaryId "
            + " ,t_ben.txt_nom as beneficiaryName "
            + " ,TO_CHAR(t_od.dte_fec_firma, 'yyyy-mm-dd') as signatureDate "
            + " ,t_ld2.srl_id as stateId "
            + " ,t_ld2.txt_nom_corto as stateName "
            + " ,t_od.bol_flg_plan as flagPlan "
            + " ,t_m.txt_nom_largo as modalityDescription "
            + " ,(t_m.var_cod_modalidad || '-' || t_m.txt_nom_corto || '-' || t_m.var_siglas) as modalityCode2 "
            + " from od.t_od as t_od "
            + "	left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
            + "	left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
            + "	left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
            + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
            + "	where t_od.var_ids_poligono is not null and t_od.var_ids_poligono <> '' and t_od.var_ids_sector is not null and t_od.var_ids_sector <> '' "
            + " and UPPER(t_od.var_cod) like '%' || UPPER(?1) || '%' "
            + " and (case when ?2 > 0 then t_od.int_id_estado = ?2 else 1 = 1 end)  "
            + " and (case when ?3 > 0 then t_od.int_id_tipo = ?3 else 1 = 1 end) "
            + " and (case when ?4 = '' then 1 = 1 else string_to_array(t_od.var_ids_anp_config, ',') && cast(?4 as text[]) end) "
            + " and (case when ?5 = 0 then 1 = 1 else ?5 = t_od.int_id_modalidad end) "
            + " and (case when ?6 = '' then 1 = 1 else string_to_array(t_od.var_ids_recursos_anp, ',') && cast(?6 as text[]) end)"
            + " and (case when ?7 > 0 then t_od.int_id_beneficiario = ?7 else 1 = 1 end) "
            + " and t_od.bol_flg_eliminado = false and t_od.bol_flg_borrador = false "
            + " order by t_od.srl_id desc",
            nativeQuery=true)
    List<ODDTO> search2(String code, int stateId, int typeId, String anpCode, int modalityId, String resourceIds, int beneficiaryId);

    List<OD>findByFlagDraftAndIsDeleted(boolean isDraft, boolean isDeleted);

    List<OD>findByTypeIdAndIsDeleted(int typeId, boolean isDeleted);

    List<OD>findByTypeIdAndFlagDraftAndIsDeleted(int typeId, boolean flagDraft, boolean isDeleted);

    @Modifying
    @Query(value="update od.t_od set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Query(value= "select string_agg((c_nomb || ' ' || anp_nomb), ', ') from ge.v_tbt_anp "
            + " where anp_codi = any(string_to_array(?1, ','))",
            nativeQuery=true)
    String getAnpNames(String anpCodes);

    @Query(value= "select string_agg(distinct txt_nom_corto, ', ') from ge.t_listado_detalle "
            + " where cast(srl_id as varchar) = any(string_to_array(?1, ','))",
            nativeQuery=true)
    String getResourceNames(String resourceAnpConfigIds);

//    @Query(value = "select string_agg(distinct t_ld.txt_nom_corto, ', ') "
//            + " from od.t_recurso_anp_config as t_rac "
//            + " inner join ge.t_listado_detalle as t_ld on t_ld.srl_id = t_rac.int_id_recurso"
//            + " where t_rac.srl_id in (:resourceAnpConfigIds)", nativeQuery = true)
//    public String getResourceNames(@Param("resourceAnpConfigIds")int[] resourceAnpConfigIds);

    @Query(value = "SELECT (last_value +1) FROM od.t_od_srl_id_seq", nativeQuery = true)
    public int getCurrentValSequence();

    @Modifying
    @Query(value= "update od.t_od set txt_num_resolucion_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateResolutionDocumentId(int id, String resolutionRouteId);

    @Modifying
    @Query(value= "update od.t_od set txt_num_titulo_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateTitleRouteDocumetId(int id, String titleRouteId);

    @Query(value= "select (case"
            + " when txt_num_resolucion_ruta = ?1 then txt_doc_resolucion "
            + " when txt_num_titulo_ruta = ?1 then txt_doc_nom_titulo "
            + " else '' end) as fileName from od.t_od"
            + " where txt_num_resolucion_ruta = ?1 or txt_num_titulo_ruta = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);

    @Modifying
    @Query(value = "insert into od.t_od_obligacion_fiscal(int_id_od, int_id_obligacion_fiscal)"
            + " select ?1, t_of.srl_id"
            + " from od.t_obligacion_fiscal as t_of "
            + " where ((case when ?2 = 0 then t_of.int_id_plan_sitio is null else t_of.int_id_plan_sitio = ?2 end) and t_of.int_id_od is null) "
            + " and ((case when ?3 = 0 then t_of.int_id_plan_manejo is null else t_of.int_id_plan_manejo = ?3 end) and t_of.int_id_od is null) ",
            nativeQuery = true)
    void generateMatrizForPlan(int id, int sitePlanId, int managementPlanId);

    @Modifying
    @Query(value = "insert into od.t_od_obligacion_fiscal(int_id_od, int_id_norma_anp_config)"
            + " select ?1, t_nac.srl_id"
            + " from od.t_norma_anp_config as t_nac "
            + " inner join od.t_config_anp as t_ca on t_ca.srl_id = t_nac.int_id_configanp "
            + " where t_ca.txt_cod_anp = any(string_to_array(?2, ',')) "
            + " and t_ca.var_cod_sector = any(string_to_array(?3, ',')) "
            + " and t_ca.var_cod_poligono = any(string_to_array(?4, ','))",
            nativeQuery = true)
    void generateMatrizForNormAnpConfig(int id, String anpCodes, String sectorCodes, String polygonCodes);

    @Modifying
    @Query(value = "delete from od.t_od_obligacion_fiscal "
            + " where srl_id in (select t_oof.srl_id from od.t_od_obligacion_fiscal as t_oof "
            + " left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_oof.int_id_obligacion_fiscal "
                + " where t_oof.int_id_od = ?1 and t_of.int_id_od is null "
                + " or (t_oof.int_id_norma_anp_config is not null and t_oof.int_id_od = ?1))",
            nativeQuery = true)
    void deleteMatrizRelated(int id);

    List<OD> findByFlagDraftAndIsDeletedAndTypeId(boolean b, boolean b1, int typeId);
}
