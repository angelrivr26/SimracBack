package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.MonitoringReportDTO;
import pe.sernanp.ws_api.dto.SupervisionReportDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MonitoringRecord;
import pe.sernanp.ws_api.model.MonitoringReport;
import pe.sernanp.ws_api.model.SupervisionReport;

import java.io.ByteArrayInputStream;

public interface MonitoringReportService {
    ResponseEntity<MonitoringReportDTO> search(MonitoringReport item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<MonitoringReport> findAll() throws Exception;
    ResponseEntity<MonitoringReport> findById(int id) throws Exception;
    ResponseEntity<MonitoringRecord> listMonitoringRecordById(int id) throws Exception;
    ResponseEntity<MonitoringReport> save(MonitoringReport item, MultipartFile documentReport) throws Exception;
    ResponseEntity<MonitoringReport> update(MonitoringReport item, MultipartFile documentReport) throws Exception;
    ResponseEntity<MonitoringReport> updateConclusion(MonitoringReport item) throws Exception;
    ResponseEntity<MonitoringReport> delete(int id) throws Exception;
    ByteArrayInputStream export(MonitoringReport item) throws Exception;
    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;
    public String getFileName (String fileId) throws Exception;
}
