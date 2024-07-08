package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.ProcedureResponseDTO;
import pe.sernanp.ws_api.model.ProcedureAssign;
import pe.sernanp.ws_api.model.ProcedureMPV;

import java.util.List;

@Transactional
@Repository
public interface ProcedureRepository extends JpaRepository<ProcedureMPV,Integer> {
    ProcedureMPV findByProcedure(Integer procedure);

    @Query(value="select t_tr.srl_id as id"
            + " ,t_tr.int_id_tramite as procedure"
            + " ,t_tr.* "
            + " ,t_od.int_id_modalidad as modalityId "
            + " ,t_m.txt_nom_corto as modalityName "
            + " ,t_m.int_id_tipo as modalityTypeId "
            + " ,t_ld3.txt_nom_corto as modalityTypeName "
            + " ,t_b.txt_nom as beneficiaryName "
            + " ,(select string_agg( DISTINCT A.var_name, ', ') from od.t_anp_mpv as A where A.int_id_tramite = t_tr.srl_id) as anps"
            + " ,(select string_agg( DISTINCT X.var_codi, ', ') from od.t_anp_mpv as X where X.int_id_tramite = t_tr.srl_id) as codeAnps"
            + " ,(select string_agg(B.var_responsable, ', ') from od.t_tramite_asignacion as B where B.int_id_tramite = t_tr.srl_id) as responsible"
            + " ,t_tr.dte_fec_admision as dateAdmission "
            + " ,(select max(C.dte_fec_cre) from od.t_tramite_asignacion as C where C.int_id_tramite = t_tr.srl_id) as dateDerivation"
            + " ,t_od.srl_id as odId "
            + " ,t_od.var_cod as odCode "
            + " ,t_ld1.srl_id as odTypeId "
            + " ,t_ld1.txt_nom_corto as odTypeName "
            + " ,t_ld2.srl_id as odStateId "
            + " ,t_ld2.txt_nom_corto as odStateName "
            + " ,t_te.srl_id as typeEvaluationId "
            + " ,t_te.txt_nom_corto as typeEvaluationName "
            + " ,t_e.srl_id as stateId "
            + " ,t_ld4.txt_nom_corto as stateName "
            + " ,(select coalesce(sum(A.num_plazo), 0) from od.t_modalidad_etapa A where A.int_id_modalidad = t_tr.int_id_modalidad) as daysAdmission"
            + " from od.t_tramite as t_tr "
            + " inner join od.t_tramite_estado as t_e on t_e.int_id_tramite = t_tr.srl_id and t_e.bol_activo = true"
            + " left join od.t_od as t_od on t_od.srl_id = t_tr.int_id_od "
            + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
            + " left join od.t_beneficiario as t_b on t_b.srl_id = t_od.int_id_beneficiario "
            + " left join ge.t_listado_detalle as t_te on t_te.srl_id = t_tr.int_id_tipo_eva "
            + "	left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
            + "	left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
            + "	left join ge.t_listado_detalle as t_ld3 on t_ld3.srl_id = t_m.int_id_tipo "
            + "	left join ge.t_listado_detalle as t_ld4 on t_ld4.srl_id = t_e.int_id_estado "
            + " where var_cut is not null and UPPER(t_tr.var_cut) like '%' || UPPER(?1) || '%' "
            + " and var_cod is not null and UPPER(t_od.var_cod) like '%' || UPPER(?2) || '%' "
            + " and (?3 = 0 or t_od.int_id_modalidad = ?3) "
            + " and (?4 = 0 or t_e.int_id_estado = ?4) "
            + " and (?5 = 0 or t_m.int_id_tipo = ?5) ",

            countQuery = "select count(1) from t_tramite "
                    + " inner join od.t_tramite_estado as t_e on t_e.int_id_tramite = t_tr.srl_id and t_e.bol_activo = true"
                    + " left join od.t_od as t_od on t_od.srl_id = t_tr.int_id_od "
                    + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
                    + " left join od.t_beneficiario as t_b on t_b.srl_id = t_od.int_id_beneficiario "
                    + " left join ge.t_listado_detalle as t_te on t_te.srl_id = t_tr.int_id_tipo_eva "
                    + "	left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
                    + "	left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
                    + "	left join ge.t_listado_detalle as t_ld3 on t_ld3.srl_id = t_m.int_id_tipo "
                    + "	left join ge.t_listado_detalle as t_ld4 on t_ld4.srl_id = t_e.int_id_estado "
                    + " where var_cut is not null and UPPER(t_tr.var_cut) like '%' || UPPER(?1) || '%' "
                    + " and var_cod is not null and UPPER(t_od.var_cod) like '%' || UPPER(?2) || '%' "
                    + " and (?3 = 0 or t_od.int_id_modalidad = ?3) "
                    + " and (?4 = 0 or t_e.int_id_estado = ?4) "
                    + " and (?5 = 0 or t_m.int_id_tipo = ?5) ",

            nativeQuery=true)
    Page<ProcedureResponseDTO> search(String cut, String odCode, int modality, int stateId, int typeId, Pageable page);

    @Query(value="select t_tr.srl_id as id"
            + " ,t_tr.int_id_tramite as procedure"
            + " ,t_tr.* "
            + " ,t_od.int_id_modalidad as modalityId "
            + " ,t_m.txt_nom_corto as modalityName "
            + " ,t_m.int_id_tipo as modalityTypeId "
            + " ,t_ld3.txt_nom_corto as modalityTypeName "
            + " ,t_b.txt_nom as beneficiaryName "
            + " ,(select string_agg( DISTINCT A.var_name, ', ') from od.t_anp_mpv as A where A.int_id_tramite = t_tr.srl_id) as anps"
            + " ,(select string_agg( DISTINCT X.var_codi, ', ') from od.t_anp_mpv as X where X.int_id_tramite = t_tr.srl_id) as codeAnps"
            + " ,(select string_agg(B.var_responsable, ', ') from od.t_tramite_asignacion as B where B.int_id_tramite = t_tr.srl_id) as responsible"
            + " ,t_tr.dte_fec_admision as dateAdmission "
            + " ,(select max(C.dte_fec_cre) from od.t_tramite_asignacion as C where C.int_id_tramite = t_tr.srl_id) as dateDerivation"
            + " ,t_od.srl_id as odId "
            + " ,t_od.var_cod as odCode "
            + " ,t_ld1.srl_id as odTypeId "
            + " ,t_ld1.txt_nom_corto as odTypeName "
            + " ,t_ld2.srl_id as odStateId "
            + " ,t_ld2.txt_nom_corto as odStateName "
            + " ,t_te.srl_id as typeEvaluationId "
            + " ,t_te.txt_nom_corto as typeEvaluationName "
            + " ,t_e.int_id_estado as stateId "
            + " ,t_ld4.txt_nom_corto as stateName "
            + " ,(select coalesce(sum(A.num_plazo), 0) from od.t_modalidad_etapa A where A.int_id_modalidad = t_tr.int_id_modalidad) as daysAdmission"
            + " from od.t_tramite as t_tr "
            + " inner join od.t_tramite_estado as t_e on t_e.int_id_tramite = t_tr.srl_id and t_e.bol_activo = true"
            + " left join od.t_od as t_od on t_od.srl_id = t_tr.int_id_od "
            + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
            + " left join od.t_beneficiario as t_b on t_b.srl_id = t_od.int_id_beneficiario "
            + " left join ge.t_listado_detalle as t_te on t_te.srl_id = t_tr.int_id_tipo_eva "
            + "	left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
            + "	left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
            + "	left join ge.t_listado_detalle as t_ld3 on t_ld3.srl_id = t_m.int_id_tipo "
            + "	left join ge.t_listado_detalle as t_ld4 on t_ld4.srl_id = t_e.int_id_estado "
            + " where t_tr.srl_id = ?1 ",
            nativeQuery=true)
    ProcedureResponseDTO findById(int id);

    @Query(value = "SELECT * FROM od.t_tramite_asignacion WHERE int_id_tramite = :id", nativeQuery = true)
    List<ProcedureAssign> listByIdProcedure(int id);

    @Modifying
    @Query(value = "UPDATE od.t_tramite SET var_cut = ?2, int_id_tipo_eva = 300, dte_fec_admision=now() WHERE int_id_tramite = ?1", nativeQuery = true)
    int updateCut(int procedure, String cut);
}
