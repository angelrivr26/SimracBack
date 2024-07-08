package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.NormAnpConfig;

import java.io.ByteArrayInputStream;

public interface NormAnpConfigService {
    ResponseEntity<NormAnpConfig> findByAnpConfig(String anpConfigIds) throws Exception;
//    ResponseEntity<NormAnpConfig> findAll() throws Exception;
    ResponseEntity<NormAnpConfig> findById(int id) throws Exception;
    ResponseEntity<NormAnpConfig> save(NormAnpConfig item) throws Exception;
    ResponseEntity<NormAnpConfig> update(NormAnpConfig item) throws Exception;
    ResponseEntity<NormAnpConfig> delete(int id) throws Exception;
    ByteArrayInputStream export(int anpConfigId)  throws Exception;
}
