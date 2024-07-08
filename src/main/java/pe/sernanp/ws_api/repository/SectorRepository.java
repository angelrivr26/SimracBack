package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.Sector;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SectorRepository extends JpaRepository<Sector, Integer> {
    @Query(value="select * from od.t_sector"
            + " where UPPER(var_cod_anp) like '%' || UPPER(?1) || '%' "
            + " and UPPER(var_codigo) like '%' || UPPER(?2) || '%' "
            + " and UPPER(var_nom) like '%' || UPPER(?3) || '%' "
            + " and int_id_sector is NULL "
            + " and bol_flg_eliminado = ?4 "
            + " order by srl_id desc ",
            nativeQuery=true)
    Page<Sector> search(String anpCode, String code, String name, boolean isDeleted, Pageable page);

    @Query(value="select * from od.t_sector"
            + " where UPPER(var_codigo) like '%' || UPPER(?1) || '%' "
            + " and UPPER(var_nom) like '%' || UPPER(?2) || '%' "
            + " and int_id_sector = ?3 "
            + " and bol_flg_eliminado = ?4 "
            + " order by srl_id desc ",
            nativeQuery=true)
    Page<Sector> searchArea(String code, String name, int sectorId, boolean isDeleted, Pageable page);

    @Override
    @Query(value = "select * from od.t_sector", nativeQuery = true)
    List<Sector> findAll();

    @Override
    @Query(value = "select * from od.t_sector "
            + " where srl_id = ?1 ", nativeQuery = true)
    Optional<Sector> findById(Integer id);

    @Query(value = "select * from od.t_sector "
            + " where int_id_sector = ?1 ", nativeQuery = true)
    List<Sector> findBySectorId(Integer id);

    @Query(value = "select t_s.* from od.t_sector as t_s "
            + " where t_s.var_codigo = ?1 and t_s.int_id_sector is null", nativeQuery = true)
    List<Sector> findByAreaCode(String code);

    @Query(value = "select t_s.* from od.t_sector as t_s "
            + " inner join od.t_poligono as t_p on t_p.int_id_sector = t_s.srl_id"
            + " where t_p.var_codigo = ?1 and t_s.int_id_sector is not null", nativeQuery = true)
    List<Sector> findAreaByPolygonCode(String polygonCode);

    @Query(value = "select * from od.t_sector "
            + " where var_cod_anp = ?1 and int_id_sector is null", nativeQuery = true)
    List<Sector> findByAnpCode(String anpCode);

    @Modifying
    @Query(value = "update od.t_sector "
            + " set num_area = (select sum(num_area) from od.t_sector where int_id_sector = ?1) "
            + " where srl_id = ?1 ", nativeQuery = true)
    int updateTotalArea(Integer id);
}
