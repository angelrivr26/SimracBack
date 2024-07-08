package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Polygon;

public interface PolygonService {
    ResponseEntity<Polygon> search(Polygon item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<Polygon> findAll() throws Exception;
    ResponseEntity<Polygon> findById(int id) throws Exception;
    ResponseEntity<Polygon> findByAnpCode(String anpCodes) throws Exception;
    ResponseEntity<Polygon> findBySectorId(int sectorId) throws Exception;
    ResponseEntity<Polygon> save(Polygon item) throws Exception;
    ResponseEntity<Polygon> update(Polygon item) throws Exception;
    ResponseEntity<Polygon> delete(int id) throws Exception;
}
