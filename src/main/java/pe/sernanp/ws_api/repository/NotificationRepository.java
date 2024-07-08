package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.Notification;

@Repository
@Transactional
public interface NotificationRepository  extends JpaRepository<Notification, Integer> {

    @Query(value="SELECT * FROM od.t_notificacion WHERE int_id_tramite = :id", nativeQuery=true)
    Notification listbytramite(int id);

    @Modifying
    @Query(value= "update od.t_notificacion set var_documento_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateDocumetId(int id, String documentRouteId);

    @Query(value= "select var_nom_documento "
            + " from od.t_notificacion"
            + " where var_documento_ruta = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);
}
