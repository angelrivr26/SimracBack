package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ComplianceEntity;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRCompliance;

import java.io.ByteArrayInputStream;

public interface MRComplianceService {
    ResponseEntity<ComplianceEntity> findByMonitoringRecordId(int monitoringRecordId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<ComplianceEntity> findById(int id) throws Exception;
    ResponseEntity<MRCompliance> save(MRCompliance item) throws Exception;
    ResponseEntity<MRCompliance> update(MRCompliance item) throws Exception;
    ResponseEntity<MRCompliance> delete(int id) throws Exception;
    ByteArrayInputStream export(int monitoringRecordId)  throws Exception;

    ResponseEntity<MRCompliance> saveWithFiles(MRCompliance item2, MultipartFile[] files, String folderId);

    ResponseEntity<ListDetailDTO> complianceForList(int monitoringRecordId);

    ResponseEntity<MRCompliance> findAll();
}
