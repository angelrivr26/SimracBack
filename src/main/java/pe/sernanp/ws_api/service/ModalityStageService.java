package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ModalityStage;

import java.io.ByteArrayInputStream;

public interface ModalityStageService {
    ResponseEntity<ModalityStage> findByModalityId(int modalityId) throws Exception;
//    ResponseEntity<ModalityStage> findAll() throws Exception;
    ResponseEntity<ModalityStage> findById(int id) throws Exception;
    ResponseEntity<ModalityStage> save(ModalityStage item) throws Exception;
    ResponseEntity<ModalityStage> update(ModalityStage item) throws Exception;
    ResponseEntity<ModalityStage> delete(int id) throws Exception;
    ByteArrayInputStream export(int modalityId)  throws Exception;
}
