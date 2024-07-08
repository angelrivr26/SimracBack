package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ActivityProcedure;

public interface ActivityProcedureService {
    ResponseEntity<ActivityProcedure> save(ActivityProcedure item) throws Exception;
    ResponseEntity<ActivityProcedure> listByIdProcedure(int id) throws Exception;
}
