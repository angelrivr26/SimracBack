package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.AnpConfigDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.AnpConfig;

public interface AnpConfigService {
    ResponseEntity<AnpConfigDTO> search(AnpConfig item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<ListDTO> searchRelated(AnpConfig item) throws Exception;
    ResponseEntity<AnpConfig> findAll() throws Exception;
    ResponseEntity<AnpConfig> findById(int id) throws Exception;
    ResponseEntity<AnpConfig> findByAnpConfig(AnpConfig item) throws Exception;
    ResponseEntity<AnpConfig> save(AnpConfig item) throws Exception;
    ResponseEntity<AnpConfig> update(AnpConfig item) throws Exception;
    ResponseEntity<AnpConfig> delete(int id) throws Exception;
    ResponseEntity<ListDTO> listByModality(int modalityId) throws Exception;
}
