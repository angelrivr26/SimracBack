package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FormsMPV;

public interface FormMPVService {
    ResponseEntity<FormsMPV> save(FormsMPV item);

}
