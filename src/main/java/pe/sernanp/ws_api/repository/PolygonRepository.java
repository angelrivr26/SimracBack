package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.Polygon;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PolygonRepository extends JpaRepository<Polygon, Integer>  {
    @Query(value="select * from od.t_poligono as t_p"
            + " inner join od.t_sector as t_s on t_s.srl_id = t_p.int_id_sector"
            + " where UPPER(t_s.var_cod_anp) like '%' || UPPER(?1) || '%' "
            + " and UPPER(t_p.var_codigo) like '%' || UPPER(?2) || '%' "
            + " and UPPER(t_p.var_nom) like '%' || UPPER(?3) || '%' "
            + " and t_p.int_id_sector = (case when ?4 = 0 then t_p.int_id_sector else ?4 end) "
            + " and t_p.bol_flg_eliminado = ?5 "
            + " order by t_p.srl_id desc ",
            nativeQuery=true)
    Page<Polygon> search(String anpCode, String code, String name, int sectorId, boolean isDeleted, Pageable page);

    @Query(value = "select * from od.t_poligono "
            + " where int_id_sector = ?1 ", nativeQuery = true)
    List<Polygon> findBySectorId(Integer id);

    @Query(value = "select t_p.* from od.t_poligono as t_p "
            + " inner join od.t_sector as t_sa on t_sa.srl_id = t_p.int_id_sector "
            + " inner join od.t_sector as t_s on t_s.srl_id = t_sa.int_id_sector "
            + " where t_s.var_cod_anp in (:anpCodes) ", nativeQuery = true)
    List<Polygon> findByAnpCode(@Param("anpCodes") String[] anpCodes);
    //List<Polygon> findByAnpCode(String anpCode);
}
