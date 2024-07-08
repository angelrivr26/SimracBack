package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.PdaSectores;

import java.io.ByteArrayInputStream;

public interface PdaSectoresService {
    ResponseEntity<PdaSectores> listByAnp(int idAnp) throws Exception;
    ResponseEntity<PdaSectores> listByAnps(String anpIds) throws Exception;
    ByteArrayInputStream export(String anpIds)  throws Exception;
}
