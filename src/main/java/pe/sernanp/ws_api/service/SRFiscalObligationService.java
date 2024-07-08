package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.SRFiscalObligationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRFiscalObligation;

import java.io.ByteArrayInputStream;

public interface SRFiscalObligationService {
    ResponseEntity<SRFiscalObligationDTO> findBySupervisionReportId(int supervisionReportId) throws Exception;
    ResponseEntity<ListDetailDTO> listFiscalObligationBySupervisionReportId(int supervisionReportId) throws Exception;
    ResponseEntity<SRFiscalObligation> findById(int id) throws Exception;
    ResponseEntity<SRFiscalObligation> save(SRFiscalObligation item) throws Exception;
    ResponseEntity<SRFiscalObligation> update(SRFiscalObligation item) throws Exception;
    ResponseEntity<SRFiscalObligation> delete(int id) throws Exception;
}
