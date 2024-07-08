package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ProcedureState;

public interface ProcedureStateService {

    ResponseEntity<ProcedureState> save(ProcedureState item) throws Exception;

    ResponseEntity<ProcedureState> listByTramite(int id) throws Exception;

    ResponseEntity<ProcedureState> update(ProcedureState item) throws Exception;

    ResponseEntity<ProcedureState> listTramiteActivo(int id) throws Exception;
}
