package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ModalityAnpConfig;

import java.io.ByteArrayInputStream;

public interface ModalityAnpConfigService {

    ResponseEntity<ModalityAnpConfig> findByAnpConfig(String anpConfigIds) throws Exception;
//    ResponseEntity<ModalityAnpConfig> findAll() throws Exception;
    ResponseEntity<ModalityAnpConfig> findById(int id) throws Exception;
    ResponseEntity<ModalityAnpConfig> save(ModalityAnpConfig item) throws Exception;
    ResponseEntity<ModalityAnpConfig> update(ModalityAnpConfig item) throws Exception;
    ResponseEntity<ModalityAnpConfig> delete(int id) throws Exception;
    ResponseEntity<ListDTO> listSectorByTypeModalityAndAnpCode(ModalityAnpConfig item) throws Exception;
    ResponseEntity<ListDTO> listPolygonByTypeModalityAndAnpSector(ModalityAnpConfig item) throws Exception;
    ByteArrayInputStream export(int anpConfigId)  throws Exception;
}
