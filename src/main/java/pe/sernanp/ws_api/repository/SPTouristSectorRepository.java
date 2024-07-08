package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.model.SPTouristSector;

import java.util.List;

@Repository
public interface SPTouristSectorRepository extends JpaRepository<SPTouristSector, Integer> {
    List<SPTouristSector> findBySitePlanIdAndIsDeleted(int sitePlanId, boolean isDeleted);

//    @Query(value="select distinct t_mod.srl_id as id"
//            + " ,t_mod.var_cod_modalidad as code "
//            + " ,t_mod.txt_nom_largo as description "
//            + " ,t_mod.txt_nom_corto_titulo as titleEnables"
//            + " ,t_mod.txt_nom_corto as shortName"
//            +" from od.t_modalidad as t_mod"
//            + " inner join od.t_ps_sector_turistico as t_st on t_st.int_id_modalidad = t_mod.srl_id"
//            + " where t_st.txt_codigo = ?1 "
//            + " and t_st.int_id_plan_sitio = ?2",
//            nativeQuery=true)
//    List<ModalityDTO> findModalityBySectorCode(String sectorCode, int sitePlanId);
}
