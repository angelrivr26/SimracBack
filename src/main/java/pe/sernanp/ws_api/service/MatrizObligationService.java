package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.dto.MatrizObligationDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MatrizObligation;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface MatrizObligationService {
    ResponseEntity<MatrizObligationDTO> search(MatrizObligation item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<MatrizObligation> findAll() throws Exception;
    ResponseEntity<ListDTO> listRecordCode() throws Exception;

    ResponseEntity<MatrizObligation> findById(int id) throws Exception;
    ResponseEntity<MatrizObligation> save(MatrizObligation item) throws Exception;
    ResponseEntity<MatrizObligation> saveFiscalObligation(MatrizObligation item) throws Exception;
    ResponseEntity<FiscalObligationDTO> listFiscalObligation(int id) throws Exception;
    ResponseEntity<MatrizObligation> deleteFiscalObligation(MatrizObligation item) throws Exception;
    ResponseEntity<MatrizObligation> deleteMassiveFiscalObligation(List<MatrizObligation> items) throws Exception;
    ResponseEntity<MatrizObligation> update(MatrizObligation item) throws Exception;
    ResponseEntity<MatrizObligation> delete(int id) throws Exception;
    ByteArrayInputStream export(MatrizObligation item)  throws Exception;
    ByteArrayInputStream exportFiscalObligation(int id)  throws Exception;

}
