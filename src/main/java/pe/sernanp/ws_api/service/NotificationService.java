package pe.sernanp.ws_api.service;


import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Notification;

public interface NotificationService {

    ResponseEntity<Notification> save(Notification item, MultipartFile file) throws Exception;

    ResponseEntity<Notification> listByTramite(int id) throws Exception;

    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;

    public String getFileName (String fileId) throws Exception;
}
