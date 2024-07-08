package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRDocumentationRequested;

import java.io.ByteArrayInputStream;

public interface SRDocumentationRequestedService {
    ResponseEntity<SRDocumentationRequested> findBySupervisionRecordId(int supervisionRecordId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<SRDocumentationRequested> findById(int id) throws Exception;
    ResponseEntity<SRDocumentationRequested> save(SRDocumentationRequested item) throws Exception;
    ResponseEntity<SRDocumentationRequested> update(SRDocumentationRequested item) throws Exception;
    ResponseEntity<SRDocumentationRequested> delete(int id) throws Exception;
    ByteArrayInputStream export(int supervisionRecordId)  throws Exception;
}
