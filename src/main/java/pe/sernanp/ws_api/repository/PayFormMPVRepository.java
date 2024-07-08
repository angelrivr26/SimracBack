package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.PayFormMPV;

import javax.transaction.Transactional;

@Repository
public interface PayFormMPVRepository extends JpaRepository<PayFormMPV, Integer> {
    PayFormMPV findByProcedureId(Integer procedureId);
    @Modifying
    @Transactional
    @Query(value = "delete from od.t_form_pago where int_id_tramite = ?1 ", nativeQuery = true)
    void deleteByProcedureId(Integer procedureId);

}
