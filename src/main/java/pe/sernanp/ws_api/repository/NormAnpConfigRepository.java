package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.NormAnpConfig;

import java.util.List;

@Repository
@Transactional
public interface NormAnpConfigRepository extends JpaRepository<NormAnpConfig, Integer> {
    @Query(value="select t_nor.* from od.t_norma_anp_config as t_nor"
            + " where t_nor.int_id_configanp in (:anpConfigId) "
            + " and t_nor.bol_flg_eliminado = :isDeleted",
            nativeQuery=true)
    List<NormAnpConfig> findByAnpConfigIds(@Param("anpConfigId") int[] anpConfigIds, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_norma_anp_config set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);
}
