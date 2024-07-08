package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.model.ActivityAnpConfig;

import java.util.List;

@Repository
@Transactional
public interface ActivityAnpConfigRepository extends JpaRepository<ActivityAnpConfig, Integer> {
    @Query(value="select t_act.* from od.t_actividad_anp_config as t_act"
            + " where t_act.int_id_configanp in (:anpConfigId) "
            + " and t_act.bol_flg_eliminado = :isDeleted",
            nativeQuery=true)
    List<ActivityAnpConfig> findByAnpConfigIds(@Param("anpConfigId") int[] anpConfigIds, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_actividad_anp_config set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Query(value= "select t_aac.srl_id as id"
            + " ,(t_ca.txt_cod_anp || ' - ' || t_ld.txt_nom_corto) as name "
            + " from od.t_actividad_anp_config as t_aac"
            + " left join od.t_config_anp as t_ca on t_ca.srl_id = t_aac.int_id_configanp "
            + " left join ge.t_listado_detalle as t_ld on t_ld.srl_id = t_aac.int_id_actividad "
            + " where t_ca.txt_cod_anp in (:anpCodes) "
            + " and (case when :sectorCodes = '' then t_ca.var_cod_sector isnull else t_ca.var_cod_sector = any(string_to_array(:sectorCodes, ',')) end) "
            + " and (case when :polygonCodes = '' then t_ca.var_cod_poligono isnull else t_ca.var_cod_poligono = any(string_to_array(:polygonCodes, ',')) end) "
            + " and t_aac.int_id_tipo_actividad = :activityTypeId "
            + " and t_ca.int_id_tipo = :typeId "
            + " and t_aac.bol_flg_eliminado = false"
            + " and t_ca.bol_flg_eliminado = false ",
            nativeQuery=true)
    List<ListDTO> listActivityByTypeAndAnpConfig(@Param("typeId") int typeId, @Param("anpCodes") String[] anpCodes, @Param("sectorCodes") String sectorCodes, @Param("polygonCodes") String polygonCodes, @Param("activityTypeId") int activityTypeId);

    @Query(value= "select distinct t_ld.srl_id as id"
            + " ,t_ld.txt_nom_corto as name "
            + " from od.t_actividad_anp_config as t_aac"
            + " left join od.t_config_anp as t_ca on t_ca.srl_id = t_aac.int_id_configanp "
            + " left join ge.t_listado_detalle as t_ld on t_ld.srl_id = t_aac.int_id_tipo_actividad "
            + " where t_ca.txt_cod_anp in (:anpCodes) "
            + " and (case when :sectorCodes = '' then t_ca.var_cod_sector isnull else t_ca.var_cod_sector = any(string_to_array(:sectorCodes, ',')) end) "
            + " and (case when :polygonCodes = '' then t_ca.var_cod_poligono isnull else t_ca.var_cod_poligono = any(string_to_array(:polygonCodes, ',')) end) "
            + " and t_ca.int_id_tipo = :typeId "
            + " and t_aac.bol_flg_eliminado = false"
            + " and t_ca.bol_flg_eliminado = false ",
            nativeQuery=true)
    List<ListDTO> listActivityTypeByAnpConfig(@Param("typeId") int typeId, @Param("anpCodes") String[] anpCodes, @Param("sectorCodes") String sectorCodes, @Param("polygonCodes") String polygonCodes);
}
