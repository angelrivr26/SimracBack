package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ResourceAnpConfig;

import java.io.ByteArrayInputStream;

public interface ResourceAnpConfigService {
    ResponseEntity<ResourceAnpConfig> findByAnpConfig(String anpConfigIds) throws Exception;
    ResponseEntity<ResourceAnpConfig> findByAnpConfigCode(String anpConfigCode) throws Exception;
//    ResponseEntity<ResourceAnpConfig> findAll() throws Exception;
    ResponseEntity<ResourceAnpConfig> findById(int id) throws Exception;
    ResponseEntity<ResourceAnpConfig> save(ResourceAnpConfig item) throws Exception;
    ResponseEntity<ListDTO> listByAnpConfig(ResourceAnpConfig item) throws Exception;
    ResponseEntity<ResourceAnpConfig> update(ResourceAnpConfig item) throws Exception;
    ResponseEntity<ResourceAnpConfig> delete(int id) throws Exception;
    ByteArrayInputStream export(int anpConfigId)  throws Exception;
}
