package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.EvaluationCompatible;

public interface EvaluationCompatibleService {

    ResponseEntity<EvaluationCompatible> save(EvaluationCompatible item, MultipartFile file) throws Exception;
    ResponseEntity<EvaluationCompatible> update(EvaluationCompatible item, MultipartFile file) throws Exception;
    ResponseEntity<EvaluationCompatible> listByTramite(int id) throws Exception;

    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;

    public String getFileName (String fileId) throws Exception;

}
