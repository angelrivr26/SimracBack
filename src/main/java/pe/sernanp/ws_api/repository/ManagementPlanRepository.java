package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.ManagementPlanDTO;
import pe.sernanp.ws_api.model.ManagementPlan;

import java.util.List;

@Repository
@Transactional
public interface ManagementPlanRepository extends JpaRepository<ManagementPlan, Integer> {

    @Query(value="select pm.srl_id as id "
            + " ,pm.txt_cod_anp as anpCode "
            + " ,pm.txt_nom_anp as anpName "
            + " ,pm.txt_nombre as name "
            + " ,pm.txt_resolucion as resolution "
            + " ,pm.bol_flg_vigente as flagValidity "
            + " ,(select string_agg(ld2.txt_nom_corto, ', ') from od.t_pm_recurso as pmr" +
                " left join ge.t_listado_detalle as ld2 on ld2.srl_id = pmr.int_id_recurso" +
                " where pmr.int_id_plan_manejo = pm.srl_id) as resources"
            + " ,(case when pm.dte_fec_inicio isnull then '' else Cast(EXTRACT('year' from pm.dte_fec_inicio) as varchar) end) as startdate "
            + " ,(case when pm.dte_fec_fin isnull then '' else Cast(EXTRACT('year' from pm.dte_fec_fin) as varchar) end) as enddate "
            + " ,ld.srl_id as instrumentTypeId "
            + " ,ld.txt_nom_corto as instrumentTypeName "
            + " from od.t_plan_manejo as pm"
            + " left join ge.t_listado_detalle as ld on ld.srl_id = pm.int_id_tipo_instrumento "
            + " left join od.t_pm_recurso as pmr on pmr.int_id_plan_manejo = pm.srl_id"
            + " where case when ?1 = '' then 1 = 1 else pm.txt_cod_anp = ?1 end "
            + " and case when ?2 = 0 then 1 = 1 else pm.int_id_tipo_instrumento = ?2 end "
            + " and case when ?3 = '' then 1 = 1 else pm.txt_resolucion like '%' || ?3 || '%' end "
            + " and case when ?4 = '' then 1 = 1 else pm.txt_nombre like '%' || ?4 || '%' end "
            + " and case when ?5 = 0 then 1 = 1 else ?5 = pmr.int_id_tipo_recurso end "
            + " and case when ?6 = 0 then 1 = 1 else ?6 = pmr.int_id_recurso end "
            + " and case when ?7 = 2 then 1 = 1 when ?7 = 1 then pm.bol_flg_vigente = TRUE else pm.bol_flg_vigente = FALSE end"
            + " and case when ?8 = 2 then 1 = 1 when ?8 = 1 then pm.bol_flg_borrador = TRUE else pm.bol_flg_borrador = FALSE end "
            + " and pm.bol_flg_eliminado = ?9 "
            + " group by pm.srl_id,ld.srl_id "
            + " order by pm.srl_id desc ",
            countQuery = "select count(1) from od.t_plan_manejo as pm"
            + " left join ge.t_listado_detalle as ld on ld.srl_id = pm.int_id_tipo_instrumento "
            + " left join od.t_pm_recurso as pmr on pmr.int_id_plan_manejo = pm.srl_id"
            + " where case when ?1 = '' then 1 = 1 else pm.txt_cod_anp = ?1 end "
            + " and case when ?2 = 0 then 1 = 1 else pm.int_id_tipo_instrumento = ?2 end "
            + " and case when ?3 = '' then 1 = 1 else pm.txt_resolucion like '%' || ?3 || '%' end "
            + " and case when ?4 = '' then 1 = 1 else pm.txt_nombre like '%' || ?4 || '%' end "
            + " and case when ?5 = 0 then 1 = 1 else ?5 = pmr.int_id_tipo_recurso end "
            + " and case when ?6 = 0 then 1 = 1 else ?6 = pmr.int_id_recurso end "
            + " and case when ?7 = 2 then 1 = 1 when ?7 = 1 then pm.bol_flg_vigente = TRUE else pm.bol_flg_vigente = FALSE end"
            + " and case when ?8 = 2 then 1 = 1 when ?8 = 1 then pm.bol_flg_borrador = TRUE else pm.bol_flg_borrador = FALSE end "
            + " and pm.bol_flg_eliminado = ?9 "
            + " group by pm.srl_id,ld.srl_id "
            + " order by pm.srl_id desc ",
            nativeQuery=true)
    Page<ManagementPlanDTO> search(String anpCode, int instrumentTypeId, String resolution, String name, int resourceTypeId, int resourceId,
                                   int flagValid, int flagDraft, boolean isDeleted, Pageable page);
    @Query(value="select pm.srl_id as id "
            + " ,pm.txt_cod_anp as anpCode "
            + " ,pm.txt_nom_anp as anpName "
            + " ,pm.txt_nombre as name "
            + " ,pm.txt_resolucion as resolution "
            + " ,pm.bol_flg_vigente as flagValidity "
            + " ,(select string_agg(ld2.txt_nom_corto, ', ') from od.t_pm_recurso as pmr" +
                " left join ge.t_listado_detalle as ld2 on ld2.srl_id = pmr.int_id_recurso" +
                " where pmr.int_id_plan_manejo = pm.srl_id) as resources"
            + " ,(case when pm.dte_fec_inicio isnull then '' else Cast(EXTRACT('year' from pm.dte_fec_inicio) as varchar) end) as startdate "
            + " ,(case when pm.dte_fec_fin isnull then '' else Cast(EXTRACT('year' from pm.dte_fec_fin) as varchar) end) as enddate "
            + " ,ld.srl_id as instrumentTypeId "
            + " ,ld.txt_nom_corto as instrumentTypeName "
            + " from od.t_plan_manejo as pm"
            + " left join ge.t_listado_detalle as ld on ld.srl_id = pm.int_id_tipo_instrumento "
            + " left join od.t_pm_recurso as pmr on pmr.int_id_plan_manejo = pm.srl_id"
            + " where case when ?1 = '' then 1 = 1 else pm.txt_cod_anp = ?1 end "
            + " and case when ?2 = 0 then 1 = 1 else pm.int_id_tipo_instrumento = ?2 end "
            + " and case when ?3 = '' then 1 = 1 else pm.txt_resolucion like '%' || ?3 || '%' end "
            + " and case when ?4 = '' then 1 = 1 else pm.txt_nombre like '%' || ?4 || '%' end "
            + " and case when ?5 = 0 then 1 = 1 else ?5 = pmr.int_id_tipo_recurso end "
            + " and case when ?6 = 0 then 1 = 1 else ?6 = pmr.int_id_recurso end "
            + " and case when ?7 = 2 then 1 = 1 when ?7 = 1 then pm.bol_flg_vigente = TRUE else pm.bol_flg_vigente = FALSE end"
            + " and case when ?8 = 2 then 1 = 1 when ?8 = 1 then pm.bol_flg_borrador = TRUE else pm.bol_flg_borrador = FALSE end "
            + " and pm.bol_flg_eliminado = ?9 "
            + " group by pm.srl_id,ld.srl_id ",
            nativeQuery=true)
    List<ManagementPlanDTO> search2(String anpCode, int instrumentTypeId, String resolution, String name, int resourceTypeId, int resourceId,
                                    int flagValid, int flagDraft, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_plan_manejo set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_plan_manejo set txt_resolucion_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateResolutionRouteDocumentId(int id, String resolutionRouteId);

    @Modifying
    @Query(value= "update od.t_plan_manejo set txt_pm_archivo_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateManagementPlanFileId(int id, String managementPlanFileId);

    @Modifying
    @Query(value= "update od.t_plan_manejo set txt_pm_informe_compatibilidad_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateCompatibilityReportFileId(int id, String compatibilityReportFileId);

    @Query(value= "select (case"
            + " when txt_resolucion_ruta = ?1 then txt_resolucion "
            + " when txt_pm_archivo_ruta = ?1 then txt_pm_archivo "
            + " else '' end) as fileName from od.t_plan_manejo"
            + " where txt_resolucion_ruta = ?1 or txt_pm_archivo_ruta = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);

    @Query(value= "select string_agg((c_nomb || ' ' || anp_nomb), ', ') from ge.v_tbt_anp "
            + " where anp_codi = any(string_to_array(?1, ','))",
            nativeQuery=true)
    String getAnpNames(String anpCodes);

    @Query(value= "select * from od.t_plan_manejo as pm "
            + " where pm.txt_cod_anp = any(string_to_array(?1, ','))",
            nativeQuery=true)
    List<ManagementPlan> findByAnpCode(String anpCodes);

    List<ManagementPlan> findByIsDeleted(boolean isDeleted);
}
