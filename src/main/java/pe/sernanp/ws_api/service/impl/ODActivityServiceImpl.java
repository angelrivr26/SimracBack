package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ODActivity;
import pe.sernanp.ws_api.repository.ODActivityRepository;
import pe.sernanp.ws_api.service.ODActivityService;

import java.util.List;
import java.util.Optional;

@Service
public class ODActivityServiceImpl implements ODActivityService {
    @Autowired
    ODActivityRepository _repository;

    public ResponseEntity<ODActivity> findById(int id) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<ODActivity>();
        try {
            Optional<ODActivity> item = _repository.findById(id);
            if (item.isPresent())
                response.setItem(item.get());
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ODActivity> findByOdId(int odId) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<ODActivity>();
        try {
            List<ODActivity> items = _repository.findByOdIdAndIsDeleted(odId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ODActivity> save(ODActivity item) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<ODActivity>();
        try {
            ODActivity _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ODActivity> update(ODActivity item) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<ODActivity>();
        try {
            Optional<ODActivity> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                ODActivity _itemUpdate = _repository.save(item);
                response.setItem(_itemUpdate);
                response.setMessage("Se actualizó el registro correctamente");
            }
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al actualizar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ODActivity> delete(int id) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<ODActivity>();
        try {
            Optional<ODActivity> item = _repository.findById(id);
            if (item.isPresent()){
                _repository.deleteById(id);
                //_repository.updateIsDeleted(id, true);
                response.setMessage("Se elimino el registro correctamente.");
            }
            else{
                response.setMessage("El registro no existe.");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al eliminar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
}
