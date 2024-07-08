package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.model.ListHeader;

import java.util.List;

@Repository
public interface ListHeaderRepository extends JpaRepository<ListHeader, Integer>  {
    @Query(value=" select * "
            + " from ge.t_listado "
            + " where int_cod_num = (case when ?1 = 0 then int_cod_num else ?1 end) "
            + " and UPPER(txt_nom_corto) like '%' || UPPER(?2) || '%' "
            + " and bol_flg_eliminado = ?3 ", nativeQuery = true)
    Page<ListHeader> search(int code, String name, boolean isDeleted, Pageable page);

    @Query(value=" select int_cod_num as id " +
            " ,txt_nom_corto as name " +
            " ,txt_nom_largo as description " +
            " from ge.t_listado", nativeQuery=true)
    List<ListDetailDTO> listAll();

    @Query(value = "SELECT (last_value +1) FROM ge.t_listado_srl_id_seq", nativeQuery = true)
    public int getCurrentValSequence();
}
