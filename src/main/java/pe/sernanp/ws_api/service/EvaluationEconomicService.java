package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.EvaluationEconomic;

public interface EvaluationEconomicService {

    ResponseEntity<EvaluationEconomic> save(EvaluationEconomic item, MultipartFile file) throws Exception;
    ResponseEntity<EvaluationEconomic> update(EvaluationEconomic item, MultipartFile file) throws Exception;
    ResponseEntity<EvaluationEconomic> listByTramite(int id) throws Exception;

    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;

    public String getFileName (String fileId) throws Exception;

}
