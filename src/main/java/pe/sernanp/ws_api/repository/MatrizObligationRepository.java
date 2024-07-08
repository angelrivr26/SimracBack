package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.dto.MatrizObligationDTO;
import pe.sernanp.ws_api.model.MatrizObligation;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MatrizObligationRepository extends JpaRepository<MatrizObligation, Integer> {

    @Query(value = " select srl_id from od.t_matriz_obligacion "
            + " where int_id_od = ?1 and txt_num_expediente = ?2 limit 1",
            nativeQuery = true)
    Integer findByOdIdAndFileNumber(int odId, String fileNumber);

    @Query(value="select t_mo.srl_id as id "
            + " ,t_mo.var_periodo as period"
            + " ,t_mo.txt_num_expediente as fileNumber "
            + " ,t_ld1.srl_id as typeId "
            + " ,t_ld1.txt_nom_corto as typeName "
            + " ,t_od.var_ids_anp_config as anpCodes "
            + " ,t_od.txt_nom_anps as anpNames "
            + " ,t_m.txt_nom_corto_titulo as titleEnables"
            + " ,t_od.txt_nom_recursos as resourceNames "
            + " ,t_ben.srl_id as beneficiaryId "
            + " ,t_ben.txt_nom as beneficiaryName "
            + " ,TO_CHAR(t_od.dte_fec_firma, 'yyyy-mm-dd') as signatureDate "
            + " ,t_ld2.srl_id as stateId "
            + " ,t_ld2.txt_nom_corto as stateName "
            + " from od.t_matriz_obligacion as t_mo "
            + " left join od.t_matriz_obligacion_od_obligacion_fiscal as t_moof on t_mo.srl_id = t_moof.int_id_matriz_obligacion "
            + " left join od.t_od as t_od on t_od.srl_id = t_mo.int_id_od "
            + " left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
            + " left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
            + " left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
            + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
            + " left join ge.t_listado_detalle as t_ld3 on cast(t_ld3.srl_id as varchar) = any(string_to_array(t_od.var_ids_recursos_anp, ',')) "
            + " where (case when ?1 = '' then 1 = 1 else ?1 = any(string_to_array(t_od.var_ids_anp_config, ',')) end) "
            + " and (case when ?2 > 0 then t_od.int_id_tipo = ?2 else 1 = 1 end) "
            + " and (case when ?3 > 0 then t_od.int_id_modalidad = ?3 else 1 = 1 end) "
            + " and (case when ?4 > 0 then t_od.int_id_beneficiario = ?4 else 1 = 1 end) "
            + " and (case when ?5 = '' then 1 = 1 else string_to_array(t_od.var_ids_recursos_anp, ',') && cast(?5 as text[]) end) "
            + " and (case when ?6 > 0 then t_od.int_id_estado = ?6 else 1 = 1 end) "
            + " and t_od.var_cod like '%'|| ?7 ||'%'"
            + " and (case when ?8 > 0 then t_ld3.int_id_listado_detalle = ?8 else 1 = 1 end) "
            + " and t_od.bol_flg_eliminado = ?9 "
            + " group by t_mo.srl_id, t_od.srl_id, t_m.srl_id, t_ben.srl_id, t_ld1.srl_id ,t_ld2.srl_id"
            + " order by t_mo.srl_id desc ",
            nativeQuery=true)
    Page<MatrizObligationDTO> search(String anpCodes, int typeId,int modalityId, int beneficiaryId, String resourceIds, int stateId, String odCode, int resourceTypeId, boolean isDeleted, Pageable page);

    @Query(value="select t_mo.srl_id as id "
            + " ,t_mo.var_periodo as period"
            + " ,t_mo.txt_num_expediente as fileNumber "
            + " ,t_ld1.srl_id as typeId "
            + " ,t_ld1.txt_nom_corto as typeName "
            + " ,t_od.var_ids_anp_config as anpCodes "
            + " ,t_od.txt_nom_anps as anpNames "
            + " ,t_m.txt_nom_corto_titulo as titleEnables"
            + " ,t_od.txt_nom_recursos as resourceNames "
            + " ,t_ben.srl_id as beneficiaryId "
            + " ,t_ben.txt_nom as beneficiaryName "
            + " ,TO_CHAR(t_od.dte_fec_firma, 'yyyy-mm-dd') as signatureDate "
            + " ,t_ld2.srl_id as stateId "
            + " ,t_ld2.txt_nom_corto as stateName "
            + " from od.t_matriz_obligacion as t_mo "
            + " inner join od.t_matriz_obligacion_od_obligacion_fiscal as t_moof on t_mo.srl_id = t_moof.int_id_matriz_obligacion "
            + " left join od.t_od as t_od on t_od.srl_id = t_mo.int_id_od "
            + " left join ge.t_listado_detalle as t_ld1 on t_ld1.srl_id = t_od.int_id_tipo "
            + " left join ge.t_listado_detalle as t_ld2 on t_ld2.srl_id = t_od.int_id_estado "
            + " left join od.t_beneficiario as t_ben on t_ben.srl_id = t_od.int_id_beneficiario "
            + " left join od.t_modalidad as t_m on t_m.srl_id = t_od.int_id_modalidad "
            + " left join ge.t_listado_detalle as t_ld3 on cast(t_ld3.srl_id as varchar) = any(string_to_array(t_od.var_ids_recursos_anp, ',')) "
            + " where (case when ?1 = '' then 1 = 1 else ?1 = any(string_to_array(t_od.var_ids_anp_config, ',')) end) "
            + " and (case when ?2 > 0 then t_od.int_id_tipo = ?2 else 1 = 1 end) "
            + " and (case when ?3 > 0 then t_od.int_id_modalidad = ?3 else 1 = 1 end) "
            + " and (case when ?4 > 0 then t_od.int_id_beneficiario = ?4 else 1 = 1 end) "
            + " and (case when ?5 = '' then 1 = 1 else string_to_array(t_od.var_ids_recursos_anp, ',') && '{?5}' end) "
            + " and (case when ?6 > 0 then t_od.int_id_estado = ?6 else 1 = 1 end) "
            + " and t_od.var_cod like '%'|| ?7 ||'%'"
            + " and (case when ?8 > 0 then t_ld3.int_id_listado_detalle = ?8 else 1 = 1 end) "
            + " and t_od.bol_flg_eliminado = ?9 "
            + " group by t_mo.srl_id, t_od.srl_id, t_m.srl_id, t_ben.srl_id, t_ld1.srl_id ,t_ld2.srl_id"
            + " order by t_od.srl_id desc ",
            nativeQuery=true)
    List<MatrizObligationDTO> search2(String anpCodes, int typeId, int modalityId, int beneficiaryId, String resourceIds, int stateId, String odCode, int resourceTypeId, boolean isDeleted);


    @Query(value = "select t_oof.srl_id as id"
            + ", (case when (t_of.int_id_od isnull and t_of.int_id_plan_sitio isnull and t_of.int_id_plan_manejo isnull) then true else false end) as updateable"
            + ", (case when t_oof.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end) as description"
            + ", (case when t_oof.int_id_norma_anp_config is null then t_of.txt_caracteristica else '' end) as characteristic"
            + ", (case when t_oof.int_id_norma_anp_config is null then t_of.num_plazo_ejecucion else 0 end) as executionTerm"
            + ", ld1.srl_id sourceId "
            + ", ld1.txt_nom_corto as sourceName"
            + ", ld2.srl_id as responsibleId "
            + ", ld2.txt_nom_corto as responsibleName"
            + " from od.t_matriz_obligacion_od_obligacion_fiscal as t_moof  "
            + " left join od.t_od_obligacion_fiscal as t_oof on t_oof.srl_id = t_moof.int_id_od_obligacion_fiscal"
            + " left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_oof.int_id_obligacion_fiscal"
            + " left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_oof.int_id_norma_anp_config"
            + " left join ge.t_listado_detalle as ld1 on (ld1.srl_id = t_nac.int_id_fuente or ld1.srl_id = t_of.int_id_fuente)"
            + " left join ge.t_listado_detalle as ld2 on (ld2.srl_id = t_nac.int_id_responsable or ld2.srl_id = t_of.int_id_responsable)"
            + " where t_moof.int_id_matriz_obligacion = ?1 "
            + " and (t_of.bol_flg_eliminado = false or t_of.bol_flg_eliminado is null) "
            + " order by sourceId, description",
            nativeQuery = true)
    List<FiscalObligationDTO> listForId(int odId);

    @Modifying
    @Query(value= "update od.t_obligacion_fiscal set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Modifying
    @Query(value= "insert into od.t_matriz_obligacion_od_obligacion_fiscal (int_id_matriz_obligacion, int_id_od_obligacion_fiscal)"
            + " values(?1, ?2)", nativeQuery=true)
    void insertMatriz(int id, int odFiscalObligationId);

    @Modifying
    @Query(value= "delete from od.t_matriz_obligacion_od_obligacion_fiscal as t_moof"
            + " where t_moof.int_id_matriz_obligacion = ?1  "
            + " and int_id_od_obligacion_fiscal = (select srl_id from od.t_od_obligacion_fiscal t_ where t_.int_id_obligacion_fiscal = ?2 limit 1)", nativeQuery=true)
    void deleteMatriz(int id, int fiscalObligationId);

    @Query(value= " select distinct t_mo.txt_num_expediente as id "
            + " ,t_mo.txt_num_expediente as name "
            + " from od.t_matriz_obligacion as t_mo "
            + " inner join od.t_matriz_obligacion_od_obligacion_fiscal as t_moof on t_moof.int_id_matriz_obligacion = t_mo.srl_id ", nativeQuery=true)
    List<ListDTO> listRecordCode();

    @Query(value= "select distinct int_id_matriz_obligacion as id "
            + " from od.t_matriz_obligacion_od_obligacion_fiscal "
            + " where int_id_matriz_obligacion = ?1 and int_id_od_obligacion_fiscal = ?2", nativeQuery=true)
    Integer findMatrizFiscalObligationById(int id, int fiscalObligationId);

    @Query(value= "delete from od.t_matriz_obligacion_od_obligacion_fiscal "
            + " where int_id_matriz_obligacion = ?1 and int_id_od_obligacion_fiscal = ?2 RETURNING 1", nativeQuery=true)
    void deleteMatrizFiscalObligation(int id, int fiscalObligationId);
}
