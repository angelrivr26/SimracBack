package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ODDocument;

public interface ODDocumentService {
    ResponseEntity<ODDocument> findByOdId(int odId) throws Exception;
    //    ResponseEntity<ODDocument> findAll() throws Exception;
    ResponseEntity<ODDocument> findById(int id) throws Exception;
    ResponseEntity<ODDocument> save(ODDocument item) throws Exception;
    ResponseEntity<ODDocument> update(ODDocument item) throws Exception;
    ResponseEntity<ODDocument> delete(int id) throws Exception;
}
