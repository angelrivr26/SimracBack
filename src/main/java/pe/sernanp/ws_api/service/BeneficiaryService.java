package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Beneficiary;

public interface BeneficiaryService {
    ResponseEntity<Beneficiary> findAll() throws Exception;
    ResponseEntity<Beneficiary> findByDocumentNumber(Beneficiary item) throws Exception;
}
