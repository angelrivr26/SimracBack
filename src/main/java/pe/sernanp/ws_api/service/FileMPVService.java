package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FileMPV;
import pe.sernanp.ws_api.model.ProcedureMPV;

import java.util.List;

public interface FileMPVService {
    ResponseEntity<FileMPV> save2 (List<FileMPV> items, ProcedureMPV procedure) throws Exception;
    ResponseEntity<FileMPV> listByProcedure (int procedureId) throws Exception;
}
