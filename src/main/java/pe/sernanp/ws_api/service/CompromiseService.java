package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Compromise;

public interface CompromiseService {
    ResponseEntity<Compromise> findMonetaryByOdId(int odId) throws Exception;
    ResponseEntity<Compromise> findNoMonetaryByOdId(int odId) throws Exception;
    //    ResponseEntity<Compromise> findAll() throws Exception;
    ResponseEntity<Compromise> findById(int id) throws Exception;
    ResponseEntity<Compromise> save(Compromise item) throws Exception;
    ResponseEntity<Compromise> update(Compromise item) throws Exception;
    ResponseEntity<Compromise> delete(int id) throws Exception;
}
