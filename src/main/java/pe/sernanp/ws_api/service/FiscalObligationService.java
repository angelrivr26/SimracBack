package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FiscalObligation;

import java.io.ByteArrayInputStream;

public interface FiscalObligationService {
//    ResponseEntity<FiscalObligation> findAll() throws Exception;
    ResponseEntity<FiscalObligation> findById(int id) throws Exception;
    ResponseEntity<FiscalObligation> findByManagementPlanId(int managementPlanId) throws Exception;
    ResponseEntity<FiscalObligation> findBySitePlanId(int sitePlanId) throws Exception;
    ResponseEntity<FiscalObligationDTO> listForOd(int odId) throws Exception;
    ResponseEntity<FiscalObligation> save(FiscalObligation item) throws Exception;
    ResponseEntity<FiscalObligation> update(FiscalObligation item) throws Exception;
    ResponseEntity<FiscalObligation> delete(int id) throws Exception;
    ByteArrayInputStream exportBySitePlan(int sitePlanId)  throws Exception;
    ByteArrayInputStream exportByManagementPlan(int managementPlanId)  throws Exception;
}
