package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.SitePlanDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SitePlan;

import java.io.ByteArrayInputStream;

public interface SitePlanService {
    ResponseEntity<SitePlanDTO> search(SitePlan item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<SitePlan> findAll() throws Exception;
    ResponseEntity<SitePlan> findById(int id) throws Exception;
    ResponseEntity<SitePlan> findByAnpCode(String anpCodes) throws Exception;
    ResponseEntity<SitePlan> findByPolygonCode(String polygonCode) throws Exception;
    ResponseEntity<SitePlan> save(SitePlan item, MultipartFile resolutionFile, MultipartFile instrumentFile) throws Exception;
    ResponseEntity<SitePlan> update(SitePlan item, MultipartFile resolutionFile, MultipartFile instrumentFile) throws Exception;
    ResponseEntity<SitePlan> delete(int id) throws Exception;
    ByteArrayInputStream export(SitePlan item)  throws Exception;
    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;
    public String getFileName (String fileId) throws Exception;
}
