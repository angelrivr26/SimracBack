package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.ComplianceDTO;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.model.MRCompliance;

import java.util.List;

@Repository
@Transactional
public interface MRComplianceRepository extends JpaRepository<MRCompliance, Integer> {

    List<MRCompliance> findByMonitoringRecordIdAndIsDeleted(int monitoringRecordId, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_acta_seguim_cumplimiento set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Query(value= "select \n" +
            "\tt_acta_seguim_cumplimiento.srl_id as id,\n" +
            "\tt_acta_seguim_cumplimiento.bol_cumplimiento_seleccionado as complianceSelected,\n" +
            "\tt_acta_seguim_cumplimiento.txt_comentario as comment,\n" +
            "\tt_acta_seguim_cumplimiento.txt_actividad as activity,\n" +
            "\tt_acta_seguim_cumplimiento.txt_descripcion as description,\n" +
            "\tt_listado_detalle_compliance_type.srl_id as complianceTypeId,\n" +
            "\tt_listado_detalle_compliance_type.txt_nom_corto as complianceTypeName,\n" +
            "\tld2.srl_id as stageTypeId,\n" +
            "\tld2.txt_nom_corto as stageTypeName,\n" +
            "\t(case when t_od_obligacion_fiscal.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end) as fiscalObligation,\n" +
            "\tld1.txt_nom_corto as source\n" +
            "from od.t_acta_seguim_cumplimiento as t_acta_seguim_cumplimiento\n" +
            "left join ge.t_listado_detalle as t_listado_detalle_compliance_type \n" +
            "\ton t_listado_detalle_compliance_type.srl_id = t_acta_seguim_cumplimiento.int_id_tipo_cumplimiento\n" +
            "left join od.t_od_obligacion_fiscal as t_od_obligacion_fiscal on  t_od_obligacion_fiscal.srl_id = t_acta_seguim_cumplimiento.int_id_obligacion_fiscal\n" +
            "left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_od_obligacion_fiscal.int_id_obligacion_fiscal\n" +
            "left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_od_obligacion_fiscal.int_id_norma_anp_config\n" +
            "left join ge.t_listado_detalle as ld1 on (ld1.srl_id = t_nac.int_id_fuente or ld1.srl_id = t_of.int_id_fuente)\n" +
            "left join ge.t_listado_detalle as ld2 on ld2.srl_id = t_acta_seguim_cumplimiento.int_id_tipo_etapa\n" +
            "where t_acta_seguim_cumplimiento.int_id_acta_seguimiento = ?1",
            nativeQuery=true)
    List<ComplianceDTO> findByMonitoringRecordId(int monitoringRecordId);

    @Query(value = "select \n" +
            "            t_acta_seguim_cumplimiento.srl_id as id,\n" +
            "            (case \n" +
            "            \twhen t_od.int_id_tipo = 18 then t_acta_seguim_cumplimiento.txt_actividad\n" +
            "\t\t\t \tElSE\n" +
            "\t\t\t \t(CASE\n" +
            "             \t\twhen t_od_obligacion_fiscal.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end\n" +
            "\t\t\t\t) \n" +
            "\t\t\t end) as name\n" +
            "            from od.t_acta_seguim_cumplimiento as t_acta_seguim_cumplimiento\n" +
            "\t\t\tinner join od.t_acta_seguimiento as t_acta_seguimiento on t_acta_seguimiento.srl_id = t_acta_seguim_cumplimiento.int_id_acta_seguimiento\n" +
            "\t\t\tinner join od.t_od as t_od on t_od.srl_id = t_acta_seguimiento.int_id_od\n" +
            "            left join od.t_od_obligacion_fiscal as t_od_obligacion_fiscal on  t_od_obligacion_fiscal.srl_id = t_acta_seguim_cumplimiento.int_id_obligacion_fiscal\n" +
            "            left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_od_obligacion_fiscal.int_id_obligacion_fiscal\n" +
            "            left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_od_obligacion_fiscal.int_id_norma_anp_config\n" +
            "            left join ge.t_listado_detalle as ld1 on (ld1.srl_id = t_nac.int_id_fuente or ld1.srl_id = t_of.int_id_fuente)\n" +
            "            where t_acta_seguim_cumplimiento.int_id_acta_seguimiento = ?1",nativeQuery = true)
    List<ListDetailDTO> complianceForListByMonitoringRecordId(int monitoringRecordId);

    List<MRCompliance> findByMonitoringRecordIdAndOdFiscalObligationId(int id, Integer compromiseNoMonetaryTypeId);
}
