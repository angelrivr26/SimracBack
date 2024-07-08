package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ModalityRequirement;

import java.io.ByteArrayInputStream;

public interface ModalityRequirementService {
    ResponseEntity<ModalityRequirement> findByModalityId(int modalityId) throws Exception;
//    ResponseEntity<ModalityRequirement> findAll() throws Exception;
    ResponseEntity<ModalityRequirement> findById(int id) throws Exception;
    ResponseEntity<ModalityRequirement> save(ModalityRequirement item, MultipartFile documentFile, MultipartFile templateFile) throws Exception;
    ResponseEntity<ModalityRequirement> update(ModalityRequirement item, MultipartFile documentFile, MultipartFile templateFile) throws Exception;
    ResponseEntity<ModalityRequirement> delete(int id) throws Exception;
    ByteArrayInputStream export(int modalityId)  throws Exception;
    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;
    public String getFileName (String fileId) throws Exception;
}
