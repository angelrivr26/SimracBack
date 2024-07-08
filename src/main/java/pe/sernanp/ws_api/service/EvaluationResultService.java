package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.EvaluationResult;

import java.io.ByteArrayInputStream;

public interface EvaluationResultService {

    ResponseEntity<EvaluationResult> save(EvaluationResult item) throws Exception;
    ResponseEntity<EvaluationResult> update(EvaluationResult item) throws Exception;
    ResponseEntity<EvaluationResult> listByTramite(int id) throws Exception;
    ByteArrayInputStream export(int procedureId)  throws Exception;

}
