package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.AnpDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;

public interface AnpService {
    ResponseEntity<AnpDTO> findAll() throws Exception;
    ResponseEntity<AnpDTO> listNotRelatedAnpConfig2() throws Exception;
    ResponseEntity<AnpDTO> listByModality(int modalityId) throws Exception;

}
