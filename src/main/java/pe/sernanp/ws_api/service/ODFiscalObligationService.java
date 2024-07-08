package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ODFiscalObligation;

public interface ODFiscalObligationService {
    ResponseEntity<ODFiscalObligation> save(ODFiscalObligation item) throws Exception;
    ResponseEntity<FiscalObligationDTO> listByOd(int odId) throws Exception;
    ResponseEntity<ODFiscalObligation> delete(int id) throws Exception;
}
