package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.AnpConfigDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.model.AnpConfig;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AnpConfigRepository extends JpaRepository<AnpConfig, Integer> {
    @Query(value="select t_canp.txt_cod_anp as code "
            + " ,t_canp.txt_nom as name "
            + " ,(select count(1) from od.t_actividad_anp_config t_aac inner join od.t_config_anp as t1 on t1.srl_id = t_aac.int_id_configanp and t1.txt_cod_anp = t_canp.txt_cod_anp) as activityAnpCount "
            + ",(select count(1) from od.t_modalidad_anp_config t_mod inner join od.t_config_anp as t2 on t2.srl_id = t_mod.int_id_configanp and t2.txt_cod_anp = t_canp.txt_cod_anp) as modalityAnpCount "
            + ",(select count(1) from od.t_norma_anp_config t_nor inner join od.t_config_anp as t3 on t3.srl_id = t_nor.int_id_configanp and t3.txt_cod_anp = t_canp.txt_cod_anp) as normAnpCount "
            + ",(select count(1) from od.t_recurso_anp_config t_res inner join od.t_config_anp as t4 on t4.srl_id = t_res.int_id_configanp and t4.txt_cod_anp = t_canp.txt_cod_anp) as resourceAnpCount "
            + " from od.t_config_anp as t_canp "
            + " where t_canp.bol_flg_eliminado = ?1 "
            + " group by t_canp.txt_cod_anp, t_canp.txt_nom "
            + " order by t_canp.txt_nom ",
            countQuery = "select count(1) from od.t_config_anp as t_canp "
            + " where t_canp.bol_flg_eliminado = ?1 "
            + " group by t_canp.txt_cod_anp, t_canp.txt_nom "
            + " order by t_canp.txt_nom ",
            nativeQuery=true)
    Page<AnpConfigDTO> search(boolean isDeleted, Pageable page);

    @Query(value="select t_canp.txt_cod_anp as id "
            + " ,t_canp.txt_nom as name "
            + " from od.t_config_anp as t_canp "
            + " where t_canp.bol_flg_eliminado = false "
            + " and t_canp.int_id_tipo = ?1 ",
            nativeQuery=true)
    List<ListDTO> searchRelated(int typeId);

    @Query(value="select t_canp.var_cod_sector as id "
            + " ,t_canp.var_nom_sector as name "
            + " from od.t_config_anp as t_canp "
            + " where t_canp.bol_flg_eliminado = false "
            + " and t_canp.int_id_tipo = ?1 "
            + " and t_canp.txt_cod_anp = ?2 ",
            nativeQuery=true)
    List<ListDTO> searchRelatedSector(int typeId, String anpCode);

    @Query(value="select t_canp.var_cod_poligono as id "
            + " ,t_canp.var_nom_poligono as name "
            + " from od.t_config_anp as t_canp "
            + " where t_canp.bol_flg_eliminado = false "
            + " and t_canp.int_id_tipo = ?1 "
            + " and t_canp.txt_cod_anp = ?2 "
            + " and t_canp.var_cod_sector = ?3 ",
            nativeQuery=true)
    List<ListDTO> searchRelatedPolygon(int typeId, String anpCode, String sectorCode);

    @Query(value="select t_canp.* "
            + " from od.t_config_anp as t_canp "
            + " where t_canp.int_id_tipo = ?1 "
            + " and t_canp.txt_cod_anp = ?2 "
            + " and (case when ?3 = '' then t_canp.var_cod_sector isnull else t_canp.var_cod_sector = ?3 end) "
            + " and (case when ?4 = '' then t_canp.var_cod_poligono isnull else t_canp.var_cod_poligono = ?4 end) ",
            nativeQuery=true)
    Optional<AnpConfig> findByAnpConfig(int typeId, String anpCode, String sectorCode, String polygonCode);

    List<AnpConfig> findByIsDeleted(boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_config_anp set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Query(value=" select string_agg(txt_cod_anp, ',') from od.t_config_anp", nativeQuery=true)
    String listRelatedAnpConfigCodes();

    @Query(value= " select distinct t_canp.txt_cod_anp as id "
            + " ,t_canp.txt_nom as name "
            + " from od.t_config_anp as t_canp "
            + " inner join od.t_modalidad_anp_config as t_mod_anp on t_mod_anp.int_id_configanp = t_canp.srl_id "
            + " inner join od.t_modalidad as t_mod on t_mod.srl_id = t_mod_anp.int_id_modalidad "
            + " where t_mod.srl_id = ?1 and t_canp.bol_flg_eliminado = ?2 and t_mod.bol_flg_eliminado = ?2 ",
            nativeQuery=true)
    List<ListDTO> findByModalityIdAndIsDeleted(int modalityId, boolean isDeleted);
}
