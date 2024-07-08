package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MPPermittedActivity;

import java.io.ByteArrayInputStream;

public interface MPPermittedActivityService {
    ResponseEntity<MPPermittedActivity> findByManagementPlanId(int managementPlanId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<MPPermittedActivity> findById(int id) throws Exception;
    ResponseEntity<MPPermittedActivity> save(MPPermittedActivity item) throws Exception;
    ResponseEntity<MPPermittedActivity> update(MPPermittedActivity item) throws Exception;
    ResponseEntity<MPPermittedActivity> delete(int id) throws Exception;
    ByteArrayInputStream export(int managementPlanId)  throws Exception;
}
