package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.ODActivity;

import java.util.List;

@Repository
@Transactional
public interface ODActivityRepository extends JpaRepository<ODActivity, Integer> {
    List<ODActivity> findByOdIdAndIsDeleted(int odId, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_od_actividad set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);
}
