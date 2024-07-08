package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Sector;

public interface SectorService {
    ResponseEntity<Sector> search(Sector item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<Sector> findAll() throws Exception;
    ResponseEntity<Sector> findById(int id) throws Exception;
    ResponseEntity<Sector> findBySectorId(int sectorId) throws Exception;
    ResponseEntity<Sector> findByAreaCode(String areaCode) throws Exception;
    ResponseEntity<Sector> findAreaByPolygonCode(String polygonCode) throws Exception;
    ResponseEntity<Sector> findByAnpCode(String anpCode) throws Exception;
    ResponseEntity<Sector> save(Sector item) throws Exception;
    ResponseEntity<Sector> update(Sector item) throws Exception;
    ResponseEntity<Sector> delete(int id) throws Exception;
}
