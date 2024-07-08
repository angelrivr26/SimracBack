package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Compromise;
import pe.sernanp.ws_api.repository.CompromiseRepository;
import pe.sernanp.ws_api.service.CompromiseService;

import java.util.List;
import java.util.Optional;

@Service
public class CompromiseServiceImpl implements CompromiseService {
    @Autowired
    CompromiseRepository _repository;

//    public ResponseEntity<Compromise> findAll() throws Exception {
//        ResponseEntity<Compromise> response = new ResponseEntity<Compromise>();
//        try {
//            List<Compromise> items = _repository.findAll();
//            response.setItems(items);
//        } catch (Exception ex) {
//            response.setMessage("Ocurrio un error al listar");
//            response.setSuccess(false);
//            response.setExtra(ex.getMessage());
//        }
//        return response;
//    }

    public ResponseEntity<Compromise> findById(int id) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<Compromise>();
        try {
            Optional<Compromise> item = _repository.findById(id);
            if (item.isPresent())
                response.setItem(item.get());
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Compromise> findMonetaryByOdId(int odId) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<Compromise>();
        try {
            List<Compromise> items = _repository.findByOdIdAndIsDeleted(odId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Compromise> findNoMonetaryByOdId(int odId) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<Compromise>();
        try {
            List<Compromise> items = _repository.findByOdIdAndIsDeleted(odId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Compromise> save(Compromise item) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<Compromise>();
        try {
            Compromise _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Compromise> update(Compromise item) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<Compromise>();
        try {
            Optional<Compromise> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                Compromise _itemUpdate = _repository.save(item);
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

    public ResponseEntity<Compromise> delete(int id) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<Compromise>();
        try {
            Optional<Compromise> item = _repository.findById(id);
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
