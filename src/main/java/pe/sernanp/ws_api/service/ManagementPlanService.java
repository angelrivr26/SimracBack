package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ManagementPlanDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ManagementPlan;

import java.io.ByteArrayInputStream;

public interface ManagementPlanService {
    ResponseEntity<ManagementPlanDTO> search(ManagementPlan item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<ManagementPlan> findAll() throws Exception;
    ResponseEntity<ManagementPlan> findById(int id) throws Exception;
    ResponseEntity<ManagementPlan> findByAnpCode(String anpCodes) throws Exception;
    ResponseEntity<ManagementPlan> save(ManagementPlan item, MultipartFile resolutionFile, MultipartFile managementPlanFile, MultipartFile compatibilityReportFile) throws Exception;
    ResponseEntity<ManagementPlan> update(ManagementPlan item, MultipartFile resolutionFile, MultipartFile managementPlanFile, MultipartFile compatibilityReportFile) throws Exception;
    ResponseEntity<ManagementPlan> delete(int id) throws Exception;
    ByteArrayInputStream export(ManagementPlan item)  throws Exception;
    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;
    public String getFileName (String fileId) throws Exception;
}
