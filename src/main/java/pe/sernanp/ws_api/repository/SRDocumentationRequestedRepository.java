package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.SRDocumentationRequested;

import java.util.List;

@Repository
@Transactional
public interface SRDocumentationRequestedRepository extends JpaRepository<SRDocumentationRequested, Integer> {
    List<SRDocumentationRequested> findBySupervisionRecordIdAndIsDeleted(int supervisionRecordId, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_acta_super_docu_requerida set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);
}
