package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.model.BaseEntity;

public interface DocumentService {

//    DocumentoDTO saveFile (MultipartFile file, boolean isAlfresco, String folderId) throws Exception;
    DocumentoDTO saveFile (MultipartFile file, boolean isAlfresco, String folderId, String name) throws Exception;
    byte[] getFile(boolean isAlfresco, String fileId) throws Exception;
}
