package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.MonitoringReportDTO;
import pe.sernanp.ws_api.model.MonitoringReport;

import java.util.List;

@Repository
@Transactional
public interface MonitoringReportRepository extends JpaRepository<MonitoringReport, Integer> {
    @Query(value="select t_rs.srl_id as id "
            + " ,t_rs.txt_num_informe as reportNumber "
            + " ,coalesce(ARRAY_LENGTH(string_to_array(t_rs.var_ids_acta_seguimiento, ','), 1), 0) as recordsTotal "
            + " ,(select count(distinct t_od.srl_id ) from od.t_od as t_od "
            + "     inner join od.t_acta_seguimiento as t_as on t_as.int_id_od = t_od.srl_id "
            + "     where cast(t_as.srl_id as varchar) = any(string_to_array(t_rs.var_ids_acta_seguimiento, ',')) ) as odsTotal"
            + " ,t_rs.int_obligacion_cumplida as obligationsFulfilled "
            + " ,t_rs.int_obligacion_total as obligationsTotal "
            + " ,t_ld1.srl_id as typeId "
            + " ,t_ld1.txt_nom_corto as typeName "
            + " from od.t_reporte_seguimiento as t_rs "
            + " left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_rs.int_id_tipo "
            + " left join od.t_acta_seguimiento as t_as on cast(t_as.srl_id as varchar) = any(string_to_array(t_rs.var_ids_acta_seguimiento, ',')) "
            + " left join od.t_od as t_od on t_od.srl_id = t_as.int_id_od "
            + " left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
            + " where (case when ?1 = '' then 1 = 1 else ?1 = any(string_to_array(t_od.var_ids_anp_config, ',')) end) "
            + " and (case when ?2 > 0 then t_od.int_id_tipo = ?2 else 1 = 1 end) "
            + " and t_od.var_cod like '%'|| ?3 ||'%' "
            + " and (coalesce(t_as.dte_apertura, now()) between cast(?4 as date) and cast(?5 as date) or coalesce(t_as.dte_cierre, now()) between cast(?4 as date) and cast(?5 as date)) "
//            + " and coalesce(t_as.txt_num_expediente, '') like '%'|| ?6 ||'%'"
            + " and (case when ?6 > 0 then t_od.int_id_beneficiario = ?6 else 1 = 1 end)"
            + " and coalesce(t_rs.txt_num_informe, '') like '%'|| ?7 ||'%'"
            + " group by t_rs.srl_id, t_ld1.srl_id "
            + " order by t_rs.srl_id desc",
            countQuery = "select count(t_rs.srl_id) "
                    + " from od.t_reporte_seguimiento as t_rs "
                    + " left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_rs.int_id_tipo "
                    + " left join od.t_acta_seguimiento as t_as on cast(t_as.srl_id as varchar) = any(string_to_array(t_rs.var_ids_acta_seguimiento, ',')) "
                    + " left join od.t_od as t_od on t_od.srl_id = t_as.int_id_od "
                    + " left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
                    + " where (case when ?1 = '' then 1 = 1 else ?1 = any(string_to_array(t_od.var_ids_anp_config, ',')) end) "
                    + " and (case when ?2 > 0 then t_od.int_id_tipo = ?2 else 1 = 1 end) "
                    + " and t_od.var_cod like '%'|| ?3 ||'%' "
                    + " and (coalesce(t_as.dte_apertura, now()) between cast(?4 as date) and cast(?5 as date) or coalesce(t_as.dte_cierre, now()) between cast(?4 as date) and cast(?5 as date)) "
//            + " and coalesce(t_as.txt_num_expediente, '') like '%'|| ?6 ||'%'"
                    + " and (case when ?6 > 0 then t_od.int_id_beneficiario = ?6 else 1 = 1 end)"
                    + " and coalesce(t_rs.txt_num_informe, '') like '%'|| ?7 ||'%'"
                    + " group by t_rs.srl_id, t_ld1.srl_id "
                    + " order by t_rs.srl_id desc",
            nativeQuery = true)
    Page<MonitoringReportDTO> search(String anpCodes, int typeId, String odCode, String startDate, String endDate, int beneficiaryId, String reportNumber, Pageable page);

    @Query(value="select t_rs.srl_id as id "
            + " ,t_rs.txt_num_informe as reportNumber "
            + " ,coalesce(ARRAY_LENGTH(string_to_array(t_rs.var_ids_acta_seguimiento, ','), 1), 0) as recordsTotal "
            + " ,(select count(distinct t_od.srl_id ) from od.t_od as t_od "
            + "     inner join od.t_acta_seguimiento as t_as on t_as.int_id_od = t_od.srl_id "
            + "     where cast(t_as.srl_id as varchar) = any(string_to_array(t_rs.var_ids_acta_seguimiento, ',')) ) as odsTotal"
            + " ,t_rs.int_obligacion_cumplida as obligationsFulfilled "
            + " ,t_rs.int_obligacion_total as obligationsTotal "
            + " ,t_ld1.srl_id as typeId "
            + " ,t_ld1.txt_nom_corto as typeName "
            + " from od.t_reporte_seguimiento as t_rs "
            + " left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_rs.int_id_tipo "
            + " left join od.t_acta_seguimiento as t_as on cast(t_as.srl_id as varchar) = any(string_to_array(t_rs.var_ids_acta_seguimiento, ',')) "
            + " left join od.t_od as t_od on t_od.srl_id = t_as.int_id_od "
            + " left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
            + " where (case when ?1 = '' then 1 = 1 else ?1 = any(string_to_array(t_od.var_ids_anp_config, ',')) end) "
            + " and (case when ?2 > 0 then t_od.int_id_tipo = ?2 else 1 = 1 end) "
            + " and t_od.var_cod like '%'|| ?3 ||'%' "
            + " and (coalesce(t_as.dte_apertura, now()) between cast(?4 as date) and cast(?5 as date) or coalesce(t_as.dte_cierre, now()) between cast(?4 as date) and cast(?5 as date)) "
//            + " and coalesce(t_as.txt_num_expediente, '') like '%'|| ?6 ||'%'"
            + " and (case when ?6 > 0 then t_od.int_id_beneficiario = ?6 else 1 = 1 end)"
            + " and coalesce(t_rs.txt_num_informe, '') like '%'|| ?7 ||'%'"
            + " group by t_rs.srl_id, t_ld1.srl_id "
            + " order by t_rs.srl_id desc",
            nativeQuery = true)
    List<MonitoringReportDTO> search(String anpCodes, int typeId, String odCode, String startDate, String endDate, int beneficiaryId, String reportNumber);

    @Modifying
    @Query(value= "update od.t_reporte_seguimiento set txt_ruta_documento = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateDocumentReporId(int id, String documentReporId);

    @Modifying
    @Query(value= "update od.t_reporte_seguimiento set "
            + " txt_recomendaciones = ?2 "
            + " ,txt_recomendaciones_adicionales = ?3 "
            + " ,txt_conclusiones = ?4 "
            + " where srl_id = ?1",
            nativeQuery = true)
    int updateConclusion(int id, String recommendations, String additionalRecommendations, String conclusions);

    @Modifying
    @Query(value= "update od.t_reporte_seguimiento as t_rs set "
            + " int_obligacion_cumplida = (select count(1) from od.t_acta_seguim_cumplimiento as t_asc where cast(t_asc.int_id_acta_seguimiento as varchar) = any(string_to_array(t_rs.var_ids_acta_seguimiento, ','))"
                                            + " and t_asc.int_id_tipo_cumplimiento = 97) "
            + " ,int_obligacion_total = (select count(1) from od.t_acta_seguim_cumplimiento as t_asc where cast(t_asc.int_id_acta_seguimiento as varchar) = any(string_to_array(t_rs.var_ids_acta_seguimiento, ','))) "
            + " where t_rs.srl_id = ?1",
            nativeQuery = true)
    int calculateEvaluation(int id);

        @Query(value= "select txt_nom_documento from od.t_reporte_seguimiento"
            + " where txt_ruta_documento = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);
}
