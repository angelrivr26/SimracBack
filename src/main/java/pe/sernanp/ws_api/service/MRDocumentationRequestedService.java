package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentationRequestDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRDocumentationRequested;

import java.io.ByteArrayInputStream;

public interface MRDocumentationRequestedService {
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<MRDocumentationRequested> findById(int id) throws Exception;
    ResponseEntity<MRDocumentationRequested> save(MRDocumentationRequested item) throws Exception;
    ResponseEntity<MRDocumentationRequested> update(MRDocumentationRequested item) throws Exception;
    ResponseEntity<MRDocumentationRequested> delete(int id) throws Exception;
    ByteArrayInputStream export(int monitoringRecordId)  throws Exception;

    ResponseEntity<MRDocumentationRequested> saveWithFiles(MRDocumentationRequested item2, MultipartFile file, String folderId);

    ResponseEntity<DocumentationRequestDTO> findByMonitoringRecordId(int monitoringRecordId);
}
