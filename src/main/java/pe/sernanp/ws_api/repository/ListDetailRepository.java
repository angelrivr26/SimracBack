package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.model.ListDetail;

import java.util.List;

@Repository
public interface ListDetailRepository extends JpaRepository<ListDetail, Integer> {
    @Query(value=" select * "
            + " from ge.t_listado_detalle "
            + " where coalesce(int_id_tipo_listado, 0) = (case when ?1 = 0 then coalesce(int_id_tipo_listado, 0) else ?1 end) "
            + " and coalesce(int_id_listado_detalle, 0) = (case when ?2 = 0 then coalesce(int_id_listado_detalle, 0) else ?2 end)"
            + " and UPPER(txt_nom_corto) like '%' || UPPER(?3) || '%' "
            + " and bol_flg_eliminado = ?4 ", nativeQuery = true)
    Page<ListDetail> search(int listTypeId, int listDetailId, String name, boolean isDeleted, Pageable page);

    @Query(value=" select t_ld.srl_id as id " +
            " ,t_ld.txt_nom_corto as name " +
            " ,t_ld.txt_nom_largo as description " +
            " from ge.t_listado_detalle as t_ld " +
            " left join ge.t_listado as t_l on t_l.srl_id = t_ld.int_id_tipo_listado " +
            " where t_l.int_cod_num = ?1 and t_ld.bol_flg_activo = ?2",
            nativeQuery=true)
    List<ListDetailDTO> findByListTypeCodeAndFlagActive(int code,boolean isActive);

    @Query(value=" select srl_id as id " +
            " ,txt_nom_corto as name " +
            " ,txt_nom_largo as description " +
            " from ge.t_listado_detalle where int_id_listado_detalle = ?1",
            nativeQuery=true)
    List<ListDetailDTO> findByListDetailId(int id);

}
