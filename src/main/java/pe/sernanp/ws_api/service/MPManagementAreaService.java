package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MPManagementArea;

import java.io.ByteArrayInputStream;

public interface MPManagementAreaService {
    ResponseEntity<MPManagementArea> findByManagementPlanId(int managementPlanId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<MPManagementArea> findById(int id) throws Exception;
    ResponseEntity<MPManagementArea> save(MPManagementArea item) throws Exception;
    ResponseEntity<MPManagementArea> update(MPManagementArea item) throws Exception;
    ResponseEntity<MPManagementArea> delete(int id) throws Exception;
    ByteArrayInputStream export(int managementPlanId)  throws Exception;
}
