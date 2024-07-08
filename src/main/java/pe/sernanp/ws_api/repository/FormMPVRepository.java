package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pe.sernanp.ws_api.model.FormsMPV;

import javax.transaction.Transactional;

public interface FormMPVRepository extends JpaRepository<FormsMPV,Integer> {
    FormsMPV findByProcedureIdAndForm(Integer procedureId, String form);
    @Modifying
    @Transactional
    @Query(value = "delete from od.t_formulario where int_id_tramite = ?1 ", nativeQuery = true)
    void deleteByProcedure(Integer procedureId);

}
