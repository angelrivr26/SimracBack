package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SPGrantingPolygon;
import pe.sernanp.ws_api.model.SPPermittedActivity;

import java.io.ByteArrayInputStream;

public interface SPGrantingPolygonService {
    ResponseEntity<SPGrantingPolygon> findBySitePlanId(int sitePlanId) throws Exception;
    ResponseEntity<SPGrantingPolygon> findBySouristSectorId(int touristSectorId) throws Exception;
    ResponseEntity<SPGrantingPolygon> findById(int id) throws Exception;
    ResponseEntity<SPGrantingPolygon> save(SPGrantingPolygon item) throws Exception;
    ResponseEntity<SPGrantingPolygon> update(SPGrantingPolygon item) throws Exception;
    ResponseEntity<SPGrantingPolygon> delete(int id) throws Exception;
    ResponseEntity<SPGrantingPolygon> findByConfig(SPGrantingPolygon item) throws Exception;
    ResponseEntity<ModalityDTO> listModalityByConfig(SPGrantingPolygon item) throws Exception;
//    ResponseEntity<ModalityDTO> listModalityById(String ids) throws Exception;
    ResponseEntity<ListDetailDTO> listByModality(SPPermittedActivity itemActivity) throws Exception;
    ByteArrayInputStream export(int sitePlanId)  throws Exception;
}
