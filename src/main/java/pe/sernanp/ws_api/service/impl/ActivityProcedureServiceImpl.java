package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ActivityProcedure;
import pe.sernanp.ws_api.repository.ActivityProcedureRepository;
import pe.sernanp.ws_api.service.ActivityProcedureService;

import java.util.List;

@Service
public class ActivityProcedureServiceImpl implements ActivityProcedureService {

    @Autowired
    ActivityProcedureRepository _repository;

    public ResponseEntity<ActivityProcedure> save(ActivityProcedure item) {
        ResponseEntity<ActivityProcedure> response = new ResponseEntity<ActivityProcedure>();
        try {
            ActivityProcedure _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");
            response.setSuccess(true);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ActivityProcedure> listByIdProcedure(int id) throws Exception {
        ResponseEntity<ActivityProcedure> response = new ResponseEntity<ActivityProcedure>();
        try {
            List<ActivityProcedure> items = _repository.listIdProcedure(id);
            response.setItems(items);
        }catch (Exception ex){
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

}
