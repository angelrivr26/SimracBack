package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.AnpConfigDTO;
import pe.sernanp.ws_api.dto.CriterionDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.AnpConfig;
import pe.sernanp.ws_api.model.Criterion;

public interface CriterionService {

    ResponseEntity<Criterion> findAll(int typeId, int componente) throws Exception;
    ResponseEntity<CriterionDTO> findByType(int typeId, int modality, int procedureId) throws Exception;
}
