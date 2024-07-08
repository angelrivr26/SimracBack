package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Modality;

import java.io.ByteArrayInputStream;

public interface ModalityService {
    ResponseEntity<ModalityDTO> search(Modality item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<Modality> findAll() throws Exception;
    ResponseEntity<Modality> findByTupa(boolean isTupa) throws Exception;
    ResponseEntity<Modality> findById(int id) throws Exception;
    ResponseEntity<Modality> save(Modality item, MultipartFile file) throws Exception;
    ResponseEntity<Modality> update(Modality item, MultipartFile file) throws Exception;
    ResponseEntity<Modality> delete(int id) throws Exception;
    ResponseEntity<Modality> listByAnpConfig(int typeId) throws Exception;
    ResponseEntity<Modality> listByType(int typeId) throws Exception;
    ByteArrayInputStream export(Modality item)  throws Exception;
    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;
    public String getFileName (String fileId) throws Exception;
}
