package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.SupervisionReportDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SupervisionReport;

import java.io.ByteArrayInputStream;

public interface SupervisionReportService {
    ResponseEntity<SupervisionReportDTO> search(SupervisionReport item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<SupervisionReport> findAll() throws Exception;
    ResponseEntity<SupervisionReport> findById(int id) throws Exception;
    ResponseEntity<SupervisionReport> calculateEvaluation(int id) throws Exception;
    ResponseEntity<SupervisionReport> save(SupervisionReport item, MultipartFile documentReport) throws Exception;
    ResponseEntity<SupervisionReport> update(SupervisionReport item, MultipartFile documentReport) throws Exception;
    ResponseEntity<SupervisionReport> delete(int id) throws Exception;
    ByteArrayInputStream export(SupervisionReport item) throws Exception;
    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;
    public String getFileName (String fileId) throws Exception;
}
