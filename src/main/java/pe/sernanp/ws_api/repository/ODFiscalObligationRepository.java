package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.model. ODFiscalObligation;

import java.util.List;

@Repository
public interface ODFiscalObligationRepository extends JpaRepository< ODFiscalObligation, Integer> {
    @Query(value = "select t_oof.srl_id as id"
            + ", (case when t_oof.int_id_norma_anp_config is null then t_of.txt_descripcion else t_nac.txt_nom_corto end) as description"
            + ", ld1.srl_id sourceId "
            + ", ld1.txt_nom_corto as sourceName"
            + ", ld2.srl_id as responsibleId "
            + ", ld2.txt_nom_corto as responsibleName"
            + " from od.t_od_obligacion_fiscal as t_oof "
            + " left join od.t_obligacion_fiscal as t_of on t_of.srl_id = t_oof.int_id_obligacion_fiscal"
            + " left join od.t_norma_anp_config as t_nac on t_nac.srl_id = t_oof.int_id_norma_anp_config"
            + " left join ge.t_listado_detalle as ld1 on (ld1.srl_id = t_nac.int_id_fuente or ld1.srl_id = t_of.int_id_fuente)"
            + " left join ge.t_listado_detalle as ld2 on (ld2.srl_id = t_nac.int_id_responsable or ld2.srl_id = t_of.int_id_responsable)"
            + " where t_oof.int_id_od = ?1"
            + " and (t_of.bol_flg_eliminado = false or t_of.bol_flg_eliminado is null) ",
            nativeQuery = true)
    List<FiscalObligationDTO> listByODId(int odId, boolean isDelete);

    List<ODFiscalObligation> findByOdId(int odId);
}
