package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ODActivity;

public interface ODActivityService {
    ResponseEntity<ODActivity> findByOdId(int odId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<ODActivity> findById(int id) throws Exception;
    ResponseEntity<ODActivity> save(ODActivity item) throws Exception;
    ResponseEntity<ODActivity> update(ODActivity item) throws Exception;
    ResponseEntity<ODActivity> delete(int id) throws Exception;
}
