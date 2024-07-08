package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRAdditionalInformation;

import java.io.ByteArrayInputStream;

public interface SRAdditionalInformationService {
    ResponseEntity<SRAdditionalInformation> findBySupervisionRecordId(int supervisionRecordId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<SRAdditionalInformation> findById(int id) throws Exception;
    ResponseEntity<SRAdditionalInformation> save(SRAdditionalInformation item) throws Exception;
    ResponseEntity<SRAdditionalInformation> update(SRAdditionalInformation item) throws Exception;
    ResponseEntity<SRAdditionalInformation> delete(int id) throws Exception;
    ByteArrayInputStream export(int supervisionRecordId)  throws Exception;
}
