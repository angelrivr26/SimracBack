package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.Addendum;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AddendumRepository extends JpaRepository<Addendum, Integer> {
    List<Addendum> findByOdIdAndIsDeletedOrderByOrderAsc(int odId, boolean isDeleted);
    Optional<Addendum> findByOdIdAndOrder(int odId, int order);

    @Modifying
    @Query(value= "update od.t_od_adenda set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_od_adenda set txt_documento_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateDocumentId(int id, String documentId);

    @Query(value= "select txt_nom_documento from od.t_od_adenda"
            + " where txt_documento_ruta = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);
}
