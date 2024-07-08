package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.model.ResourceAnpConfig;

import java.util.List;

@Repository
@Transactional
public interface ResourceAnpConfigRepository extends JpaRepository<ResourceAnpConfig, Integer> {
    @Query(value="select t_rec.* from od.t_recurso_anp_config as t_rec"
            + " where t_rec.int_id_configanp in (:anpConfigId) "
            + " and t_rec.bol_flg_eliminado = :isDeleted",
            nativeQuery=true)
    List<ResourceAnpConfig> findByAnpConfigIds(@Param("anpConfigId") int[] anpConfigIds, @Param("isDeleted") boolean isDeleted);

    @Query(value="select t_rec.* from od.t_recurso_anp_config as t_rec "
            + " inner join od.t_config_anp as t_canp on t_canp.srl_id = t_rec.int_id_configanp"
            //+ " where t_rec.int_id_configanp in (:anpConfigId) "
            + " where t_canp.txt_cod_anp in (:anpConfigCodes) "
            + " and t_rec.bol_flg_eliminado = :isDeleted",
            nativeQuery=true)
    List<ResourceAnpConfig> findByAnpConfigCode(@Param("anpConfigCodes") String[] anpConfigCodes, @Param("isDeleted") boolean isDeleted);

    @Query(value= "select t_rec.srl_id as id"
            + " ,(t_ca.txt_cod_anp || ' - ' || t_ld.txt_nom_corto) as name "
            + " from od.t_recurso_anp_config as t_rec"
            + " left join od.t_config_anp as t_ca on t_ca.srl_id = t_rec.int_id_configanp "
            + " left join ge.t_listado_detalle as t_ld on t_ld.srl_id = t_rec.int_id_recurso "
            + " where t_ca.txt_cod_anp in (:anpCodes) "
            + " and (case when :sectorCodes = '' then t_ca.var_cod_sector isnull else t_ca.var_cod_sector = any(string_to_array(:sectorCodes, ',')) end) "
            + " and (case when :polygonCodes = '' then t_ca.var_cod_poligono isnull else t_ca.var_cod_poligono = any(string_to_array(:polygonCodes, ',')) end) "
            + " and t_ca.int_id_tipo = :typeId "
            + " and t_rec.bol_flg_eliminado = false"
            + " and t_ca.bol_flg_eliminado = false ",
            nativeQuery=true)
    List<ListDTO> findByAnpConfig(@Param("typeId") int typeId, @Param("anpCodes") String[] anpCodes, @Param("sectorCodes") String sectorCodes, @Param("polygonCodes") String polygonCodes);

    @Modifying
    @Query(value= "update od.t_recurso_anp_config set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);
}
