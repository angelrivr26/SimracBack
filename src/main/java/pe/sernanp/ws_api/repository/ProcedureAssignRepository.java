package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.ProcedureAssign;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProcedureAssignRepository extends JpaRepository<ProcedureAssign,Integer> {
    List<ProcedureAssign> findByProcedureId(Integer procedureId);
    @Transactional
    void deleteByProcedureId(int procedureId);

    ProcedureAssign findByProcedureIdAndRoleEvaluationId(Integer procedureId, Integer roleEvaluationId);


}
