package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.model.FiscalObligation;

import java.util.List;

@Repository
@Transactional
public interface FiscalObligationRepository extends JpaRepository<FiscalObligation, Integer> {
    List<FiscalObligation> findByManagementPlanIdAndIsDeleted(int managementPlanId, boolean isDeleted);
    List<FiscalObligation> findBySitePlanIdAndIsDeleted(int sitePlanId, boolean isDeleted);

    @Query(value = "select (case when t_of.int_id_od is null then 0 else t_of.srl_id end) as id"
            + ", (case when t_oof.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end) as description"
            + ", ld1.srl_id sourceId "
            + ", ld1.txt_nom_corto as sourceName"
            + ", ld2.srl_id as responsibleId "
            + ", ld2.txt_nom_corto as responsibleName"
            + ", ld2.srl_id as compromiseNoMonetaryTypeId "
            + ", ld2.txt_nom_corto as compromiseNoMonetaryTypeName"
            + " from od.t_od_obligacion_fiscal as t_oof "
            + " left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_oof.int_id_obligacion_fiscal"
            + " left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_oof.int_id_norma_anp_config"
            + " left join ge.t_listado_detalle as ld1 on (ld1.srl_id = t_nac.int_id_fuente or ld1.srl_id = t_of.int_id_fuente)"
            + " left join ge.t_listado_detalle as ld2 on (ld2.srl_id = t_nac.int_id_responsable or ld2.srl_id = t_of.int_id_responsable)"
            + " left join ge.t_listado_detalle as ld3 on (ld3.srl_id = t_of.int_id_tipo_compromiso_no_monetario)"
            + " where t_oof.int_id_od = ?1"
            + " and (t_of.bol_flg_eliminado = ?2 or t_of.bol_flg_eliminado is null) ",
            nativeQuery = true)
    List<FiscalObligationDTO> listForOd(int odId, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_obligacion_fiscal set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Modifying
    @Query(value= "insert into od.t_od_obligacion_fiscal (int_id_obligacion_fiscal, int_id_od)"
            + " values(?1, ?2)", nativeQuery=true)
    void insertMatriz(int id, int odId);

    @Modifying
    @Query(value= "delete from od.t_od_obligacion_fiscal"
            + " where int_id_obligacion_fiscal = ?1", nativeQuery=true)
    void deleteMatriz(int id);
}
