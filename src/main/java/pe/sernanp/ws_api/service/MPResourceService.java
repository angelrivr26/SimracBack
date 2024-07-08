package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MPResource;

import java.io.ByteArrayInputStream;

public interface MPResourceService {
    ResponseEntity<MPResource> findByManagementPlanId(int managementPlanId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<MPResource> findById(int id) throws Exception;
    ResponseEntity<MPResource> save(MPResource item) throws Exception;
    ResponseEntity<MPResource> update(MPResource item) throws Exception;
    ResponseEntity<MPResource> delete(int id) throws Exception;
    ByteArrayInputStream export(int managementPlanId)  throws Exception;
}
