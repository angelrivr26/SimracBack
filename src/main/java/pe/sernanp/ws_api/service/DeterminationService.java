package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.CriterionDTO;
import pe.sernanp.ws_api.dto.DeterminationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Criterion;

public interface DeterminationService {

    ResponseEntity<DeterminationDTO> findByType(int typeId, int modality, int procedureId) throws Exception;
}
