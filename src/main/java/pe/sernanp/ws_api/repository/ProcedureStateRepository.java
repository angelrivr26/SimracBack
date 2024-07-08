package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.ProcedureState;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ProcedureStateRepository extends JpaRepository<ProcedureState, Integer> {

    @Query(value="SELECT * FROM od.t_tramite_estado WHERE int_id_tramite = :id", nativeQuery=true)
    ProcedureState listbytramite(int id);

    @Modifying
    @Query(value= "update od.t_tramite_estado set bol_activo = false where int_id_tramite = :id", nativeQuery=true)
    int update(int id);

    @Query(value="SELECT * FROM od.t_tramite_estado WHERE int_id_tramite = :id and int_id_estado = :state", nativeQuery=true)
    ProcedureState findState(int id, int state);

    @Query(value="SELECT * FROM od.t_tramite_estado WHERE int_id_tramite = :id and bol_activo = true", nativeQuery=true)
    ProcedureState listbytramiteactivo(int id);
}
