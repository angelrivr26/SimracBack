package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.SRFiscalObligationDTO;
import pe.sernanp.ws_api.model.SRFiscalObligation;

import java.util.List;

@Repository
@Transactional
public interface SRFiscalObligationRepository extends JpaRepository<SRFiscalObligation, Integer> {

    @Query(value = "select t_iof.srl_id as id "
            + " ,(case when t_oof.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end) as description "
            + " ,t_iof.txt_plazo_ejecucion as executionTerm "
            + " ,coalesce(ld1.srl_id, 0) as breachId "
            + " ,ld1.txt_nom_corto as breachName "
            + " ,coalesce(ld2.srl_id, 0) as sourceId "
            + " ,ld2.txt_nom_corto as sourceName "
            + " from od.t_is_obligacion_fiscal as t_iof "
            + " inner join od.t_od_obligacion_fiscal as t_oof on t_oof.srl_id = t_iof.int_id_od_obligacion_fiscal "
            + " inner join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_oof.int_id_obligacion_fiscal "
            + " left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_oof.int_id_norma_anp_config  "
            + " left join ge.t_listado_detalle as ld1 on ld1.srl_id = t_iof.int_id_incumplimiento "
            + " left join ge.t_listado_detalle as ld2 on (ld2.srl_id = t_nac.int_id_fuente or ld2.srl_id = t_of.int_id_fuente) "
            + " where t_iof.int_id_informe_supervision = ?1 "
            + " and (t_of.bol_flg_eliminado = ?2 or t_of.bol_flg_eliminado is null) "
            + " order by sourceId, description",
            nativeQuery = true)
    List<SRFiscalObligationDTO> findBySupervisionReportIdAndIsDeleted(int supervisionReportId, boolean isDeleted);

    @Query(value = "select t_oof.srl_id as id "
            + " , (case when t_oof.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end) as name "
            + " from od.t_od_obligacion_fiscal as t_oof "
            + " left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_oof.int_id_obligacion_fiscal "
            + " left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_oof.int_id_norma_anp_config "
            + " inner join od.t_matriz_obligacion_od_obligacion_fiscal as t_moof on t_moof.int_id_od_obligacion_fiscal = t_oof.srl_id "
            + " inner join od.t_informe_supervision as t_is on t_is.int_id_matriz_obligacion = t_moof.int_id_matriz_obligacion "
            + " left JOIN od.t_is_obligacion_fiscal as t_iof on t_iof.int_id_od_obligacion_fiscal = t_oof.srl_id  "
            + " where t_is.srl_id = ?1 and t_iof.srl_id isnull"
            + " and (t_of.bol_flg_eliminado = false or t_of.bol_flg_eliminado is null)",
            nativeQuery = true)
    List<ListDetailDTO> listFiscalObligationBySupervisionReportId(int supervisionReportId);

    @Query(value = "insert into od.t_is_obligacion_fiscal(int_id_incumplimiento, int_id_od_obligacion_fiscal, int_id_informe_supervision) "
            + " select (case when coalesce(t_ashv.int_id_incumplimiento, 99) = 98 then 228 else 226 end) as int_id_incumplimiento"
            + " ,t_moof.int_id_od_obligacion_fiscal, ?1 "
            + " from od.t_matriz_obligacion_od_obligacion_fiscal as t_moof "
            + " left join od.t_acta_super_hecho_verificado as t_ashv on t_ashv.int_id_od_obligacion_fiscal = t_moof.int_id_od_obligacion_fiscal "
            + " where int_id_matriz_obligacion = ?2 "
            + " and t_ashv.int_id_acta_supervision = ?3 RETURNING 0;",
            nativeQuery = true)
    void generateMassive(int supervisionReportId, int matrizObligationId, int supervisionRecordId);
}
