package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.AnpMPV;
import pe.sernanp.ws_api.model.ProcedureMPV;

import java.util.List;

public interface AnpMPVService {
    ResponseEntity<AnpMPV> save2 (List<AnpMPV> items, ProcedureMPV procedure) throws Exception;
    List<AnpMPV> listByProcedure (int procedureId) throws Exception;
}
