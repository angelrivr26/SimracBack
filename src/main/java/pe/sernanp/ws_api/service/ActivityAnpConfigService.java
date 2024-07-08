package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ActivityAnpConfig;

import java.io.ByteArrayInputStream;

public interface ActivityAnpConfigService {
    ResponseEntity<ActivityAnpConfig> findByAnpConfig(String anpConfigIds) throws Exception;
    ResponseEntity<ActivityAnpConfig> findById(int id) throws Exception;
    ResponseEntity<ActivityAnpConfig> save(ActivityAnpConfig item) throws Exception;
    ResponseEntity<ActivityAnpConfig> update(ActivityAnpConfig item) throws Exception;
    ResponseEntity<ActivityAnpConfig> delete(int id) throws Exception;
    ResponseEntity<ListDTO> listActivityByTypeAndAnpConfig(ActivityAnpConfig item) throws Exception;
    ResponseEntity<ListDTO> listActivityTypeByAnpConfig(ActivityAnpConfig item) throws Exception;

    ByteArrayInputStream export(int anpConfigId)  throws Exception;
}
