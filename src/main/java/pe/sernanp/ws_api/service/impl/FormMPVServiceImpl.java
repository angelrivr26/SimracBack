package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FormsMPV;
import pe.sernanp.ws_api.repository.FormMPVRepository;
import pe.sernanp.ws_api.service.FormMPVService;

@Service
public class FormMPVServiceImpl implements FormMPVService {
    @Autowired
    FormMPVRepository _repository;

    @Override
    public ResponseEntity<FormsMPV> save(FormsMPV item) {
        ResponseEntity<FormsMPV> response = new ResponseEntity<FormsMPV>();
        try {
            FormsMPV _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

}
