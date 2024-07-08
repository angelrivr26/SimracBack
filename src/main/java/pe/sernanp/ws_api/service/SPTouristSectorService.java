package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SPTouristSector;

import java.io.ByteArrayInputStream;

public interface SPTouristSectorService {
    ResponseEntity<SPTouristSector> findBySitePlanId(int sitePlanId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<SPTouristSector> findById(int id) throws Exception;
    ResponseEntity<SPTouristSector> save(SPTouristSector item) throws Exception;
    ResponseEntity<SPTouristSector> update(SPTouristSector item) throws Exception;
    ResponseEntity<SPTouristSector> delete(int id) throws Exception;
//    ResponseEntity<ModalityDTO> findModalityBySectorCode(SPTouristSector item) throws Exception;

    ByteArrayInputStream export(int sitePlanId)  throws Exception;
}
