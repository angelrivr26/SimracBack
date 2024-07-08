package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.model.SPGrantingPolygon;

import java.util.List;

@Repository
public interface SPGrantingPolygonRepository extends JpaRepository<SPGrantingPolygon, Integer> {
    List<SPGrantingPolygon> findBySitePlanIdAndIsDeleted(int sitePlanId, boolean isDeleted);
    List<SPGrantingPolygon> findBySpTouristSectorIdAndIsDeleted(int touristSectorId, boolean isDeleted);

    @Query(value = "select t_pspo.* from od.t_ps_poligono_otorgamiento as t_pspo"
            + " inner join od.t_ps_sector_turistico as t_psst on t_psst.srl_id = t_pspo.int_id_sector_turistico "
            + " inner join od.t_sector as t_s on t_s.srl_id = t_psst.int_id_sector_area "
            + " inner join od.t_sector as t_s1 on t_s1.srl_id = t_s.int_id_sector "
            + " where t_pspo.int_id_plan_sitio = ?1 "
            + " and (case when ?2 = '' then 1 = 1 else t_s1.var_codigo = any(string_to_array(?2, ',')) end)",
            nativeQuery = true)
    List<SPGrantingPolygon> findByConfig(int sitePlanId, String sectorCodes);

//    @Query(value = "select distinct t_mod.srl_id as id"
//            + " ,t_mod.var_cod_modalidad as code "
//            + " ,t_mod.txt_nom_largo as description "
//            + " ,t_mod.txt_nom_corto_titulo as titleEnables"
//            + " ,t_mod.txt_nom_corto as shortName"
//            + " from od.t_modalidad as t_mod"
//            + " inner join od.t_ps_poligono_otorgamiento as t_pspo on cast(t_mod.srl_id as varchar) = any(string_to_array(t_pspo.var_ids_modalidad, ',')) "
//            + " where (case when ?1 = '' then 1 = 1 else cast(t_pspo.srl_id as varchar) = any(string_to_array(?1, ',')) end)",
//            nativeQuery = true)
//    List<ModalityDTO> listModalityById(String ids);

    @Query(value = "select distinct t_mod.srl_id as id"
            + " ,t_mod.var_cod_modalidad as code "
            + " ,t_mod.txt_nom_largo as description "
            + " ,t_mod.txt_nom_corto_titulo as titleEnables"
            + " ,t_mod.txt_nom_corto as shortName"
            + " from od.t_modalidad as t_mod"
            + " inner join od.t_ps_poligono_otorgamiento as t_pspo on cast(t_mod.srl_id as varchar) = any(string_to_array(t_pspo.var_ids_modalidad, ',')) "
            + " inner join od.t_ps_sector_turistico as t_psst on t_psst.srl_id = t_pspo.int_id_sector_turistico "
            + " inner join od.t_sector as t_s on t_s.srl_id = t_psst.int_id_sector_area "
            + " inner join od.t_sector as t_s1 on t_s1.srl_id = t_s.int_id_sector "
            + " inner join od.t_poligono as t_p on t_p.srl_id = t_pspo.int_id_poligono "
            + " where t_pspo.int_id_plan_sitio = ?1 "
            + " and (case when ?2 = '' then 1 = 1 else t_s1.var_codigo = any(string_to_array(?2, ',')) end)"
            + " and (case when ?3 = '' then 1 = 1 else t_p.var_codigo = any(string_to_array(?3, ',')) end)",
            nativeQuery = true)
    List<ModalityDTO> listModalityByConfig(int sitePlanId, String sectorCodes, String polygonCodes);
    @Query(value = "select distinct t_po.srl_id as id"
            + " ,t_p.var_codigo as name "
            + " from od.t_ps_poligono_otorgamiento as t_po"
            + " inner join od.t_poligono as t_p on t_p.srl_id = t_po.int_id_poligono "
            + " where t_po.int_id_plan_sitio = ?1"
            + " and cast(?2 as varchar) = any(string_to_array(t_po.var_ids_modalidad, ','))",
            nativeQuery = true)
    List<ListDetailDTO> listBySitePlanAndModality(int sitePlanId, int modalityId);

}
