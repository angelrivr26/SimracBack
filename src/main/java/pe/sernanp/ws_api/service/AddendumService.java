package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Addendum;

public interface AddendumService {
    ResponseEntity<Addendum> findByOdId(int odId) throws Exception;
    //    ResponseEntity<Addendum> findAll() throws Exception;
    ResponseEntity<Addendum> findById(int id) throws Exception;
    ResponseEntity<Addendum> save(Addendum item, MultipartFile file) throws Exception;
    ResponseEntity<Addendum> update(Addendum item, MultipartFile file) throws Exception;
    ResponseEntity<Addendum> delete(int id) throws Exception;
    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;
    public String getFileName (String fileId) throws Exception;
}
