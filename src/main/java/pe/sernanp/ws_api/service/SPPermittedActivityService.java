package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SPPermittedActivity;

import java.io.ByteArrayInputStream;

public interface SPPermittedActivityService {
    ResponseEntity<SPPermittedActivity> findBySitePlanId(int sitePlanId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<SPPermittedActivity> findById(int id) throws Exception;
    ResponseEntity<SPPermittedActivity> save(SPPermittedActivity item) throws Exception;
    ResponseEntity<SPPermittedActivity> update(SPPermittedActivity item) throws Exception;
    ResponseEntity<SPPermittedActivity> delete(int id) throws Exception;
    ByteArrayInputStream export(int sitePlanId)  throws Exception;
}
