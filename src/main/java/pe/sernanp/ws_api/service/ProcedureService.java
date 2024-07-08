package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.*;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ProcedureAssign;
import pe.sernanp.ws_api.model.ProcedureMPV;

public interface ProcedureService {
    ResponseEntity<ProcedureMPV> save(ProcedureMPV item);
    ResponseEntity<ProcedureResponseDTO> search(ProcedureRequestDTO item, PaginatorEntity paginator) throws Exception;

    ResponseEntity<?> findById(int id) throws Exception;
    ResponseEntity<?> saveCriterion(CriterionResponseDTO item);

    ResponseEntity<ProcedureMPV> result(int id) throws Exception;
    ResponseEntity<?> saveDetermination(DeterminationResponseDTO item);
    ResponseEntity<?> saveAssign(ProcedureAssignDTO item);
    ResponseEntity<?> getAssign(int id) throws Exception;

    ResponseEntity<ProcedureAssign> getList(int id)throws Exception;

    ResponseEntity<ProcedureAssign> updateCut(int id, int record, int period)throws Exception;
}
