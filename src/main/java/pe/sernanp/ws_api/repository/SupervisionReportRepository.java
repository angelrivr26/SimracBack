package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.SupervisionReportDTO;
import pe.sernanp.ws_api.model.SupervisionReport;

import java.util.List;

@Repository
@Transactional
public interface SupervisionReportRepository extends JpaRepository<SupervisionReport, Integer> {

    @Query(value="select t_is.srl_id as id "
            + " ,t_is.txt_num_informe as reportNumber "
            + " ,t_ld1.srl_id as typeId "
            + " ,t_ld1.txt_nom_corto as typeName "
            + " ,t_od.var_ids_anp_config as anpCodes "
            + " ,t_od.txt_nom_anps as anpNames "
            + " ,t_od.var_cod as odCode "
            + " ,t_m.txt_nom_corto_titulo as titleEnables "
            + " ,t_ben.srl_id as beneficiaryId "
            + " ,t_ben.txt_nom as beneficiaryName "
            + " ,TO_CHAR(t_as.dte_apertura, 'yyyy-mm-dd') as openingDate "
            + " ,t_ld2.srl_id as stateId "
            + " ,t_ld2.txt_nom_corto as stateName "
            + " ,(select count(1) from od.t_is_obligacion_fiscal as t_iof where t_iof.int_id_informe_supervision = t_is.srl_id) as obligationCount "
            + " from od.t_informe_supervision as t_is "
            + " left join od.t_acta_supervision as t_as on t_as.srl_id = t_is.int_id_acta_supervision"
            + " left join od.t_od as t_od on t_od.srl_id = t_as.int_id_od "
            + " left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
            + " left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
            + " left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
            + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
            + " where (case when ?1 = '' then 1 = 1 else ?1 = any(string_to_array(t_od.var_ids_anp_config, ',')) end) "
            + " and (case when ?2 > 0 then t_od.int_id_tipo = ?2 else 1 = 1 end) "
            + " and t_od.var_cod like '%'|| ?3 ||'%' "
            + " and (case when ?4 = 2 then 1 = 1 when ?4 = 1 then t_as.bol_flg_regular = true else t_as.bol_flg_regular = false end) "
            + " and (coalesce(t_as.dte_apertura, now()) between cast(?5 as date) and cast(?6 as date) or coalesce(t_as.dte_cierre, now()) between cast(?5 as date) and cast(?6 as date)) "
            + " and coalesce(t_is.txt_num_expediente, '') like '%'|| ?7 ||'%'"
            + " and (case when ?8 > 0 then t_od.int_id_beneficiario = ?8 else 1 = 1 end)"
            + " and coalesce(t_is.txt_num_informe, '') like '%'|| ?9 ||'%'"
            + " and t_od.bol_flg_eliminado = false"
            + " order by t_is.srl_id desc ",
            countQuery = "select count(1) "
                    + " from od.t_informe_supervision as t_is "
                    + " left join od.t_acta_supervision as t_as on t_as.srl_id = t_is.int_id_acta_supervision"
                    + " left join od.t_od as t_od on t_od.srl_id = t_as.int_id_od "
                    + " left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
                    + " left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
                    + " left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
                    + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
                    + " where (case when ?1 = '' then 1 = 1 else ?1 = any(string_to_array(t_od.var_ids_anp_config, ',')) end) "
                    + " and (case when ?2 > 0 then t_od.int_id_tipo = ?2 else 1 = 1 end) "
                    + " and t_od.var_cod like '%'|| ?3 ||'%' "
                    + " and (case when ?4 = 2 then 1 = 1 when ?4 = 1 then t_as.bol_flg_regular = true else t_as.bol_flg_regular = false end) "
                    + " and (coalesce(t_as.dte_apertura, now()) between cast(?5 as date) and cast(?6 as date) or coalesce(t_as.dte_cierre, now()) between cast(?5 as date) and cast(?6 as date)) "
                    + " and coalesce(t_is.txt_num_expediente, '') like '%'|| ?7 ||'%'"
                    + " and (case when ?8 > 0 then t_od.int_id_beneficiario = ?8 else 1 = 1 end)"
                    + " and coalesce(t_is.txt_num_informe, '') like '%'|| ?9 ||'%'"
                    + " and t_od.bol_flg_eliminado = false",
            nativeQuery=true)
    Page<SupervisionReportDTO> search(String anpCodes, int typeId, String odCode, int supervisionType, String startDate, String endDate, String recordCode, int beneficiaryId, String reportNumber, Pageable page);


    @Query(value="select t_is.srl_id as id "
            + " ,t_is.txt_num_informe as reportNumber "
            + " ,t_ld1.srl_id as typeId "
            + " ,t_ld1.txt_nom_corto as typeName "
            + " ,t_od.var_ids_anp_config as anpCodes "
            + " ,t_od.txt_nom_anps as anpNames "
            + " ,t_od.var_cod as odCode "
            + " ,t_m.txt_nom_corto_titulo as titleEnables "
            + " ,t_ben.srl_id as beneficiaryId "
            + " ,t_ben.txt_nom as beneficiaryName "
            + " ,TO_CHAR(t_as.dte_apertura, 'yyyy-mm-dd') as openingDate "
            + " ,t_ld2.srl_id as stateId "
            + " ,t_ld2.txt_nom_corto as stateName "
            + " ,(select count(1) from od.t_is_obligacion_fiscal as t_iof where t_iof.int_id_informe_supervision = t_is.srl_id) as obligationCount "
            + " from od.t_informe_supervision as t_is "
            + " left join od.t_acta_supervision as t_as on t_as.srl_id = t_is.int_id_acta_supervision"
            + " left join od.t_od as t_od on t_od.srl_id = t_as.int_id_od "
            + " left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
            + " left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
            + " left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
            + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
            + " where (case when ?1 = '' then 1 = 1 else ?1 = any(string_to_array(t_od.var_ids_anp_config, ',')) end) "
            + " and (case when ?2 > 0 then t_od.int_id_tipo = ?2 else 1 = 1 end) "
            + " and t_od.var_cod like '%'|| ?3 ||'%' "
            + " and (case when ?4 = 2 then 1 = 1 when ?4 = 1 then t_as.bol_flg_regular = true else t_as.bol_flg_regular = false end) "
            + " and (coalesce(t_as.dte_apertura, now()) between cast(?5 as date) and cast(?6 as date) or coalesce(t_as.dte_cierre, now()) between cast(?5 as date) and cast(?6 as date)) "
            + " and coalesce(t_is.txt_num_expediente, '') like '%'|| ?7 ||'%'"
            + " and (case when ?8 > 0 then t_od.int_id_beneficiario = ?8 else 1 = 1 end)"
            + " and coalesce(t_is.txt_num_informe, '') like '%'|| ?9 ||'%'"
            + " and t_od.bol_flg_eliminado = false",
            nativeQuery = true)
    List<SupervisionReportDTO> search(String anpCodes, int typeId, String odCode, int supervisionType, String startDate, String endDate, String recordCode, int beneficiaryId, String reportNumber);

    @Modifying
    @Query(value = "update od.t_informe_supervision "
            + " set int_obligacion_cumplida = (select count(1) from od.t_is_obligacion_fiscal as t_iof where t_iof.int_id_informe_supervision = ?1 and t_iof.int_id_incumplimiento = ?2),"
            + " int_obligacion_total = (select count(1) from od.t_is_obligacion_fiscal as t_iof where t_iof.int_id_informe_supervision = ?1) "
            + " where srl_id = ?1 ",
            nativeQuery = true)
    void calculateEvaluation(int id, int breachId);

    @Modifying
    @Query(value= "update od.t_informe_supervision set txt_ruta_documento = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateDocumentReporId(int id, String documentReporId);

    @Query(value= "select txt_nom_documento from od.t_informe_supervision"
            + " where txt_ruta_documento = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);
}
