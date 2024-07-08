package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.dto.SupervisionRecordDTO;
import pe.sernanp.ws_api.dto.SupervisionRecordRequestDTO;
import pe.sernanp.ws_api.model.SupervisionRecord;

import java.util.Date;
import java.util.List;

@Repository
public interface SupervisionRecordRepository extends JpaRepository<SupervisionRecord, Integer> {
    @Query(value = "select distinct" +
            " t_acta_supervision.srl_id as id" +
            " ,t_acta_supervision.txt_num_acta as acta" +
            " ,t_listado_detalle_tipo.txt_nom_corto as tipo" +
            " ,t_acta_supervision.txt_nom_anp as anp" +
            " ,t_od.var_cod as otorgamiento" +
            " ,t_modalidad.txt_nom_corto_titulo as titulo" +
            " ,t_acta_supervision.dte_apertura as fechaApertura" +
            " ,t_beneficiario.txt_nom as beneficiario" +
            " ,t_listado_detalle_estado.txt_nom_corto as estado" +
            " ,count(t_acta_super_hecho_verificado.srl_id) as hechos" +
            " ,count(CASE WHEN t_listado_detalle_hecho_verificado_estado.txt_nom_corto = 'SI' THEN 1 ELSE NULL END) as hechosIncumplidos" +
            " from od.t_acta_supervision as t_acta_supervision" +
            " inner join od.t_od as t_od on t_od.srl_id = t_acta_supervision.int_id_od" +
            " left join od.t_beneficiario as t_beneficiario on  t_beneficiario.srl_id = t_od.int_id_beneficiario" +
            " left join od.t_modalidad as t_modalidad on t_modalidad.srl_id = t_od.int_id_modalidad" +
            " left join ge.t_listado_detalle as t_listado_detalle_tipo on t_listado_detalle_tipo.srl_id = t_od.int_id_tipo" +
//            "left join ge.t_listado_detalle as t_listado_detalle_estado on t_listado_detalle_estado.srl_id = t_acta_supervision.int_id_estado" +
            " left join ge.t_listado_detalle as t_listado_detalle_estado on t_listado_detalle_estado.srl_id = t_od.int_id_estado" +
            " left join od.t_acta_super_hecho_verificado as t_acta_super_hecho_verificado on t_acta_super_hecho_verificado.int_id_acta_supervision = t_acta_supervision.srl_id" +
            " left join ge.t_listado_detalle as t_listado_detalle_hecho_verificado_estado " +
            " on t_listado_detalle_hecho_verificado_estado.srl_id = t_acta_super_hecho_verificado.int_id_incumplimiento" +
            " where " +
            " t_od.var_cod like ?3 || '%'" +
            " and t_acta_supervision.txt_num_expe like ?7 || '%'" +
            " and (COALESCE(t_beneficiario.txt_num_documento,'') || COALESCE(UPPER(t_beneficiario.txt_nom),'') ) like '%' || UPPER(?8) || '%'" +
            " and (CASE WHEN '0' = ?1 THEN 1 = 1 ELSE t_acta_supervision.txt_cod_anp = ?1 END)" +
            " and (CASE WHEN 0 = ?2 THEN 1 = 1 ELSE t_listado_detalle_tipo.srl_id = ?2 END)" +
            " and (CASE WHEN 0 = ?4 THEN 1 = 1" +
            "   WHEN 1 = ?4 THEN t_acta_supervision.bol_flg_regular = true " +
            "   ELSE t_acta_supervision.bol_flg_regular = false " +
            " END)" +
            " and (1 = ?9 OR (t_acta_supervision.dte_apertura >= ?5)) " +
            "   and (1 = ?10 OR (t_acta_supervision.dte_cierre <= ?6)) " +
            " group by " +
            " t_acta_supervision.srl_id" +
            " ,t_listado_detalle_tipo.txt_nom_corto" +
            " ,t_acta_supervision.txt_nom_anp" +
            " ,t_od.var_cod" +
            " ,t_modalidad.txt_nom_corto_titulo" +
            " ,t_acta_supervision.dte_apertura" +
            " ,t_acta_supervision.txt_nom_anp" +
            " ,t_beneficiario.txt_nom" +
            " ,t_listado_detalle_estado.txt_nom_corto"
            + " order by t_acta_supervision.srl_id desc",nativeQuery = true)
    Page<SupervisionRecordDTO> search(@Param("anp") String anp,
                                      @Param("typeId") int typeId,
                                      @Param("od") String od,
                                      @Param("supervisionType")int supervisionType,
                                      @Param("openingDate") Date openingDate,
                                      @Param("closingDate") Date closingDate,
                                      @Param("recordNumber") String recordNumber,
                                      @Param("beneficiary") String beneficiary,
                                      @Param("iStartDate") int iStartDate,
                                      @Param("iEndDate") int iEndDate,
                                      Pageable page);

    List<SupervisionRecord> findByOdTypeId(int typeId);
   }
