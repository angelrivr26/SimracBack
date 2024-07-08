package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.model.ModalityAnpConfig;

import java.util.List;

@Repository
@Transactional
public interface ModalityAnpConfigRepository extends JpaRepository<ModalityAnpConfig, Integer> {
    @Query(value="select t_mod.* from od.t_modalidad_anp_config as t_mod"
            + " where t_mod.int_id_configanp in (:anpConfigId) "
            + " and t_mod.bol_flg_eliminado = :isDeleted",
            nativeQuery=true)
    List<ModalityAnpConfig> findByAnpConfigIds(@Param("anpConfigId") int[] anpConfigIds, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_modalidad_anp_config set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Query(value= "select distinct t_ca.var_cod_sector as id"
            + " ,t_ca.var_nom_sector as name "
            + " from od.t_modalidad_anp_config as t_mac"
            + " left join od.t_modalidad as t_mod on t_mod.srl_id = t_mac.int_id_modalidad "
            + " left join od.t_config_anp as t_ca on t_ca.srl_id = t_mac.int_id_configanp "
            + " where t_mod.srl_id = :modalityId "
            + " and t_mod.int_id_tipo = :typeId and t_ca.int_id_tipo = :typeId "
            + " and t_ca.txt_cod_anp in (:anpCodes) "
            + " and t_mac.bol_flg_eliminado = false"
            + " and t_mod.bol_flg_borrador = false ",
            nativeQuery=true)
    List<ListDTO> listSectorByTypeModalityAndAnpCode(@Param("typeId") int typeId, @Param("modalityId") int modalityId, @Param("anpCodes") String[] anpCodes);

    @Query(value= "select distinct t_ca.var_cod_poligono as id"
            + " ,t_ca.var_nom_poligono as name "
            + " from od.t_modalidad_anp_config as t_mac"
            + " left join od.t_modalidad as t_mod on t_mod.srl_id = t_mac.int_id_modalidad "
            + " left join od.t_config_anp as t_ca on t_ca.srl_id = t_mac.int_id_configanp "
            + " where t_mod.srl_id = :modalityId "
            + " and t_mod.int_id_tipo = :typeId and t_ca.int_id_tipo = :typeId "
            + " and t_ca.txt_cod_anp in (:anpCodes) "
            + " and t_ca.var_cod_sector in (:sectorCodes) "
            + " and t_mac.bol_flg_eliminado = false",
            nativeQuery=true)
    List<ListDTO> listPolygonByTypeModalityAndAnpSector(@Param("typeId") int typeId, @Param("modalityId") int modalityId, @Param("anpCodes") String[] anpCodes, @Param("sectorCodes") String[] sectorCodes);
}
