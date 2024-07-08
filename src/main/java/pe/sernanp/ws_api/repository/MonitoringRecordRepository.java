package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.dto.MonitoringRecordDTO;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.MonitoringRecord;

import java.util.Date;
import java.util.List;

@Repository
public interface MonitoringRecordRepository extends JpaRepository<MonitoringRecord, Integer> {

    List<MonitoringRecord> findByOdTypeId(int typeId);

    @Query(value="select distinct" +
            " t_acta_seguimiento.srl_id as id" +
            " ,t_acta_seguimiento.txt_num_acta as acta" +
            " ,t_listado_detalle_tipo.txt_nom_corto as tipo" +
            " ,t_acta_seguimiento.txt_nom_anp as anp" +
            " ,t_od.var_cod as otorgamiento" +
            " ,t_modalidad.txt_nom_corto_titulo as titulo" +
            " ,t_acta_seguimiento.dte_apertura as fechaApertura" +
            " ,t_beneficiario.txt_nom as beneficiario" +
            " ,t_listado_detalle_estado.txt_nom_corto as estado" +
            " ,count(distinct t_acta_seguim_cumplimiento.srl_id) as compromisos" +
            " ,count(distinct t_acta_seguim_recomendacion.srl_id) as recomendaciones" +
            " from od.t_acta_seguimiento as t_acta_seguimiento" +
            " inner join od.t_od as t_od on t_od.srl_id = t_acta_seguimiento.int_id_od" +
            " left join od.t_beneficiario as t_beneficiario on  t_beneficiario.srl_id = t_od.int_id_beneficiario" +
            " left join od.t_modalidad as t_modalidad on t_modalidad.srl_id = t_od.int_id_modalidad" +
            " left join ge.t_listado_detalle as t_listado_detalle_tipo on t_listado_detalle_tipo.srl_id = t_od.int_id_tipo" +
            //" left join ge.t_listado_detalle as t_listado_detalle_estado on t_listado_detalle_estado.srl_id = t_acta_seguimiento.int_id_estado" +
            " left join ge.t_listado_detalle as t_listado_detalle_estado on t_listado_detalle_estado.srl_id = t_od.int_id_estado" +
            " left join od.t_acta_seguim_cumplimiento as t_acta_seguim_cumplimiento on t_acta_seguim_cumplimiento.int_id_acta_seguimiento = t_acta_seguimiento.srl_id" +
            " left join od.t_acta_seguim_recomendacion as t_acta_seguim_recomendacion on t_acta_seguim_recomendacion.int_id_acta_seguimiento = t_acta_seguimiento.srl_id" +
            " where t_od.var_cod like ?1 || '%'" +
            " and (CASE WHEN '0' = ?2 THEN 1 = 1 ELSE t_acta_seguimiento.txt_cod_anp = ?2 END)" +
            " and (CASE WHEN 0 = ?3 THEN 1 = 1 ELSE t_listado_detalle_tipo.srl_id = ?3 END)" +
            " and (CASE WHEN 0 = ?4 THEN 1 = 1 ELSE t_acta_seguimiento.int_id_estado = ?4 END)" +
            " and (COALESCE(t_beneficiario.txt_num_documento,'') || COALESCE(UPPER(t_beneficiario.txt_nom),'') ) like '%' || UPPER(?5) || '%'" +
            " and t_modalidad.txt_nom_corto_titulo like '%' || ?6 || '%'" +
            " and (1 = ?9 OR (t_acta_seguimiento.dte_apertura >= ?7)) " +
            " and (1 = ?10 OR (t_acta_seguimiento.dte_cierre <= ?8))" +
            " group by " +
            " t_acta_seguimiento.srl_id" +
            " ,t_listado_detalle_tipo.txt_nom_corto" +
            " ,t_acta_seguimiento.txt_nom_anp" +
            " ,t_od.var_cod" +
            " ,t_modalidad.txt_nom_corto_titulo" +
            " ,t_acta_seguimiento.dte_apertura" +
            " ,t_acta_seguimiento.txt_nom_anp" +
            " ,t_beneficiario.txt_nom" +
            " ,t_listado_detalle_estado.txt_nom_corto",nativeQuery = true)
    Page<MonitoringRecordDTO> search(String od, String anp, int typeId, int state, String beneficiary, String titleName, Date openingDate, Date closingDate, int iStartDate, int iEndDate, Pageable page);

    @Query(value = "select t_as.* from od.t_acta_seguimiento as t_as "
            + " inner join od.t_reporte_seguimiento as t_rs on cast(t_as.srl_id as varchar) = any(string_to_array(t_rs.var_ids_acta_seguimiento, ',')) "
            + " where t_rs.srl_id = ?1 ",
            nativeQuery = true)
    List<MonitoringRecord> findByMonitoringReportId(int monitoringReportId);

    @Query(value = "select t_as.* from od.t_acta_seguimiento as t_as"
            + " inner join od.t_od as t_od on t_od.srl_id = t_as.int_id_od "
            + " where cast(?1 as varchar) = any(string_to_array(t_od.var_ids_recursos_anp, ',')) ",
            nativeQuery = true)
    List<MonitoringRecord> findByResouceId (int resouceId);

    @Query(value = "select string_agg(cast(srl_id as varchar), ',') from od.t_acta_seguimiento "
            + " where int_id_od = ?1",
            nativeQuery = true)
    String getIdsByOdId(int odId);
}
