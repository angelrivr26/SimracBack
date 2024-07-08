package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.RecommendationDTO;
import pe.sernanp.ws_api.model.MRRecommendation;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MRRecommendationRepository extends JpaRepository<MRRecommendation, Integer> {
    List<MRRecommendation> findByMonitoringRecordIdAndIsDeleted(int monitoringRecordId, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_acta_seguim_recomendacion set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Query(value="select  \n" +
            "            t_acta_seguim_recomendacion.srl_id as id,\n" +
            "            ld1.txt_nom_corto as source,\n" +
            "            (case \n" +
            "                        when t_od.int_id_tipo = 18 then t_acta_seguim_cumplimiento.txt_actividad\n" +
            "             ElSE\n" +
            "             (CASE\n" +
            "                         when t_od_obligacion_fiscal.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end\n" +
            "            ) \n" +
            "             end) as compromiseName,\n" +
            "            t_acta_seguim_cumplimiento.srl_id as compromiseId,\n" +
            "            t_acta_seguim_recomendacion.txt_recomendacion as recommendation,\n" +
            "            t_acta_seguim_recomendacion.dte_fec_acordada as agreedDate,\n" +
            "            t_acta_seguim_recomendacion.txt_comentario as comment,\n" +
            "            t_acta_seguim_recomendacion.txt_cod_documento_evaluacion as evaluateDocumentCode,\n" +
            "            t_acta_seguim_recomendacion.txt_nom_documento_evaluacion as evaluateDocumentName,\n" +
            "            t_acta_seguim_recomendacion.txt_size_documento_evaluacion as evaluateDocumentSize,\n" +
            "            t_listado_detalle_tipo_cumplimiento.srl_id as complianceTypeId,\n" +
            "            t_listado_detalle_tipo_cumplimiento.txt_nom_corto as complianceTypeName,\n" +
            "\t\t\tt_listado_detalle_etapa.srl_id as stageTypeId,\n" +
            "\t\t\tt_listado_detalle_etapa.txt_nom_corto as stageTypeName\n" +
            "            from od.t_acta_seguim_recomendacion as t_acta_seguim_recomendacion\n" +
            "            inner join od.t_acta_seguim_cumplimiento as t_acta_seguim_cumplimiento \n" +
            "            \ton t_acta_seguim_cumplimiento.srl_id = t_acta_seguim_recomendacion.int_id_acta_seguim_cumplimiento\n" +
            "\t\t\tinner join od.t_acta_seguimiento as t_acta_seguimiento \n" +
            "\t\t\t\ton t_acta_seguimiento.srl_id = t_acta_seguim_cumplimiento.int_id_acta_seguimiento\n" +
            "\t\t\tinner join od.t_od as t_od \n" +
            "\t\t\t\ton t_od.srl_id = t_acta_seguimiento.int_id_od\n" +
            "            left join od.t_od_obligacion_fiscal as t_od_obligacion_fiscal on  t_od_obligacion_fiscal.srl_id = t_acta_seguim_cumplimiento.int_id_obligacion_fiscal\n" +
            "            left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_od_obligacion_fiscal.int_id_obligacion_fiscal\n" +
            "            left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_od_obligacion_fiscal.int_id_norma_anp_config\n" +
            "            left join ge.t_listado_detalle as ld1 on (ld1.srl_id = t_nac.int_id_fuente or ld1.srl_id = t_of.int_id_fuente)\n" +
            "            left join ge.t_listado_detalle as t_listado_detalle_tipo_cumplimiento on t_listado_detalle_tipo_cumplimiento.srl_id = t_acta_seguim_recomendacion.int_id_acta_seguim_tipo_cumplimiento\n" +
            "\t\t\tleft join ge.t_listado_detalle as t_listado_detalle_etapa \n" +
            "\t\t\t\ton t_listado_detalle_etapa.srl_id = t_acta_seguim_cumplimiento.int_id_tipo_etapa\n" +
            "            where t_acta_seguim_recomendacion.int_id_acta_seguimiento = ?1 " +
            "order by t_acta_seguim_recomendacion.srl_id desc",nativeQuery=true)
        List<RecommendationDTO> listByMonitoringRecordId(int monitoringRecordId);
    @Query(value="select  \n" +
            "            t_acta_seguim_recomendacion.srl_id as id,\n" +
            "            ld1.txt_nom_corto as source,\n" +
            "            (case \n" +
            "                        when t_od.int_id_tipo = 18 then t_acta_seguim_cumplimiento.txt_actividad\n" +
            "             ElSE\n" +
            "             (CASE\n" +
            "                         when t_od_obligacion_fiscal.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end\n" +
            "            ) \n" +
            "             end) as compromiseName,\n" +
            "            t_acta_seguim_cumplimiento.srl_id as compromiseId,\n" +
            "            t_acta_seguim_recomendacion.txt_recomendacion as recommendation,\n" +
            "            t_acta_seguim_recomendacion.dte_fec_acordada as agreedDate,\n" +
            "            t_acta_seguim_recomendacion.txt_comentario as comment,\n" +
            "            t_acta_seguim_recomendacion.txt_cod_documento_evaluacion as evaluateDocumentCode,\n" +
            "            t_acta_seguim_recomendacion.txt_nom_documento_evaluacion as evaluateDocumentName,\n" +
            "            t_acta_seguim_recomendacion.txt_size_documento_evaluacion as evaluateDocumentSize,\n" +
            "            t_listado_detalle_tipo_cumplimiento.srl_id as complianceTypeId,\n" +
            "            t_listado_detalle_tipo_cumplimiento.txt_nom_corto as complianceTypeName,\n" +
            "\t\t\tt_listado_detalle_etapa.srl_id as stageTypeId,\n" +
            "\t\t\tt_listado_detalle_etapa.txt_nom_corto as stageTypeName\n" +
            "            from od.t_acta_seguim_recomendacion as t_acta_seguim_recomendacion\n" +
            "            inner join od.t_acta_seguim_cumplimiento as t_acta_seguim_cumplimiento \n" +
            "            \ton t_acta_seguim_cumplimiento.srl_id = t_acta_seguim_recomendacion.int_id_acta_seguim_cumplimiento\n" +
            "\t\t\tinner join od.t_acta_seguimiento as t_acta_seguimiento \n" +
            "\t\t\t\ton t_acta_seguimiento.srl_id = t_acta_seguim_cumplimiento.int_id_acta_seguimiento\n" +
            "\t\t\tinner join od.t_od as t_od \n" +
            "\t\t\t\ton t_od.srl_id = t_acta_seguimiento.int_id_od\n" +
            "            left join od.t_od_obligacion_fiscal as t_od_obligacion_fiscal on  t_od_obligacion_fiscal.srl_id = t_acta_seguim_cumplimiento.int_id_obligacion_fiscal\n" +
            "            left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_od_obligacion_fiscal.int_id_obligacion_fiscal\n" +
            "            left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_od_obligacion_fiscal.int_id_norma_anp_config\n" +
            "            left join ge.t_listado_detalle as ld1 on (ld1.srl_id = t_nac.int_id_fuente or ld1.srl_id = t_of.int_id_fuente)\n" +
            "            left join ge.t_listado_detalle as t_listado_detalle_tipo_cumplimiento on t_listado_detalle_tipo_cumplimiento.srl_id = t_acta_seguim_recomendacion.int_id_acta_seguim_tipo_cumplimiento\n" +
            "\t\t\tleft join ge.t_listado_detalle as t_listado_detalle_etapa \n" +
            "\t\t\t\ton t_listado_detalle_etapa.srl_id = t_acta_seguim_cumplimiento.int_id_tipo_etapa\t\t\t\n" +
            "            where t_acta_seguim_recomendacion.srl_id = ?1 " ,nativeQuery=true)
            RecommendationDTO detailById(int id);



    List<MRRecommendation> findByMonitoringRecordId(int i);
}
