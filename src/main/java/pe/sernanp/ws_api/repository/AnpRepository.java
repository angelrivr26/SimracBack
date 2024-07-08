package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.dto.AnpDTO;
import pe.sernanp.ws_api.model.Anp;

import java.util.List;

@Repository
public interface AnpRepository extends JpaRepository<Anp, Integer> {

    @Query(value=" select *, nombre as name from ge.v_tbt_anp "
            , nativeQuery=true)
    List<AnpDTO> findAll2();

    @Query(value=" select *, nombre as name from ge.v_tbt_anp "
            + " where anp_codi not in (select txt_cod_anp from od.t_config_anp)"
            , nativeQuery=true)
    List<AnpDTO> listRelatedAnpConfig();

    @Query(value="select distinct t_anp.*, nombre as name "
            + " from ge.v_tbt_anp as t_anp "
            + " left join od.t_plan_sitio as t_ps on t_ps.txt_cod_anp = t_anp.anp_codi "
            + " left join od.t_ps_poligono_otorgamiento as t_po on t_po.int_id_plan_sitio = t_ps.srl_id "
            + " left join od.t_plan_manejo as t_pm on trim(t_anp.anp_codi) = any(string_to_array(t_pm.txt_cod_anp, ',')) "
            + " left join od.t_pm_area_manejo as t_am on t_am.int_id_plan_manejo = t_pm.srl_id "
            + " where (cast(?1 as varchar) = any(string_to_array(t_po.var_ids_modalidad, ','))) "
            + " or (t_am.int_id_modalidad = ?1) "
            , nativeQuery=true)
    List<AnpDTO> listByModality(int modalityId);

}
