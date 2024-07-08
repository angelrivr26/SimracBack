package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.DocumentationRequestDTO;
import pe.sernanp.ws_api.model.MRDocumentationRequested;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface MRDocumentationRequestedRepository extends JpaRepository<MRDocumentationRequested, Integer> {
    List<MRDocumentationRequested> findByMonitoringRecordIdAndIsDeleted(int monitoringRecordId, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_acta_seguim_docu_requerida set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);
    @Query(value= "select  \n" +
            "                        t_acta_seguim_docu_requerida.srl_id as id,\n" +
            "                        (case \n" +
            "                        \twhen t_od.int_id_tipo = 18 then t_acta_seguim_cumplimiento.txt_actividad\n" +
            "             \t\t\tElSE\n" +
            "             \t\t\t(CASE\n" +
            "                         \twhen t_od_obligacion_fiscal.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end\n" +
            "            \t\t\t) \n" +
            "             \t\t\tend) as compromiseName,\n" +
            "                        t_acta_seguim_cumplimiento.srl_id as compromiseId,\n" +
            "                        t_acta_seguim_recomendacion.txt_recomendacion as recommendation,\n" +
            "                        t_acta_seguim_docu_requerida.txt_documento_relacionado as descriptionDocument,\n" +
            "                        t_acta_seguim_docu_requerida.dte_fec_acordada as agreedDate,\n" +
            "                        t_acta_seguim_docu_requerida.txt_comentario as comment,\n" +
            "                        t_acta_seguim_docu_requerida.txt_cod_documento as documentCode,\n" +
            "                        t_acta_seguim_docu_requerida.txt_nom_documento as documentName,\n" +
            "                        t_acta_seguim_docu_requerida.txt_size_documento as documentSize,\n" +
            "                        t_listado_detalle_estado.srl_id as stateId,\n" +
            "                        t_listado_detalle_estado.txt_nom_corto as stateName,\n" +
            "                        t_listado_detalle_monetario.srl_id as monetaryId,\n" +
            "                        t_listado_detalle_monetario.txt_nom_corto as monetaryName,\n" +
            "                        t_listado_detalle_responsable.srl_id as responsableId,\n" +
            "                        t_listado_detalle_responsable.txt_nom_corto as responsableName,\n" +
            "\t\t\tt_listado_detalle_etapa.srl_id as stageTypeId,\n" +
            "\t\t\tt_listado_detalle_etapa.txt_nom_corto as stageTypeName\n" +
            "            from od.t_acta_seguim_docu_requerida as t_acta_seguim_docu_requerida\n" +
            "            inner join od.t_acta_seguim_cumplimiento as t_acta_seguim_cumplimiento\n" +
            "                on t_acta_seguim_cumplimiento.srl_id = t_acta_seguim_docu_requerida.int_id_acta_seguim_cumplimiento\n" +
            "            left join od.t_acta_seguim_recomendacion as t_acta_seguim_recomendacion\n" +
            "            \ton t_acta_seguim_recomendacion.int_id_acta_seguim_tipo_cumplimiento  = t_acta_seguim_cumplimiento.srl_id\n" +
            "\t\t\tinner join od.t_acta_seguimiento as t_acta_seguimiento \n" +
            "\t\t\t\ton t_acta_seguimiento.srl_id = t_acta_seguim_cumplimiento.int_id_acta_seguimiento\n" +
            "\t\t\tinner join od.t_od as t_od \n" +
            "\t\t\t\ton t_od.srl_id = t_acta_seguimiento.int_id_od\n" +
            "            left join od.t_od_obligacion_fiscal as t_od_obligacion_fiscal on  t_od_obligacion_fiscal.srl_id = t_acta_seguim_cumplimiento.int_id_obligacion_fiscal\n" +
            "            left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_od_obligacion_fiscal.int_id_obligacion_fiscal\n" +
            "            left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_od_obligacion_fiscal.int_id_norma_anp_config\n" +
            "            left join ge.t_listado_detalle as ld1 on (ld1.srl_id = t_nac.int_id_fuente or ld1.srl_id = t_of.int_id_fuente)\n" +
            "            left join ge.t_listado_detalle as t_listado_detalle_estado on t_listado_detalle_estado.srl_id = t_acta_seguim_docu_requerida.int_id_estado\n" +
            "            left join ge.t_listado_detalle as t_listado_detalle_monetario on t_listado_detalle_monetario.srl_id = t_of.int_id_tipo_compromiso_no_monetario\n" +
            "            left join ge.t_listado_detalle as t_listado_detalle_responsable on t_listado_detalle_responsable.srl_id = t_of.int_id_responsable\n" +
            "\t\t\tleft join ge.t_listado_detalle as t_listado_detalle_etapa \n" +
            "\t\t\t\ton t_listado_detalle_etapa.srl_id = t_acta_seguim_cumplimiento.int_id_tipo_etapa\n" +
            "            where t_acta_seguim_docu_requerida.int_id_acta_seguimiento = ?1 " +
            "order by t_acta_seguim_docu_requerida.srl_id desc",
            nativeQuery=true)
    List<DocumentationRequestDTO> listByMonitoringRecordId(int monitoringRecordId);

    List<MRDocumentationRequested> findByMonitoringRecordId(int id);
}
