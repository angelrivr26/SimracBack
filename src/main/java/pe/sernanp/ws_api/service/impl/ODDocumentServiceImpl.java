package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ODDocument;
import pe.sernanp.ws_api.repository.ODDocumentRepository;
import pe.sernanp.ws_api.service.ODDocumentService;

import java.util.List;
import java.util.Optional;

@Service
public class ODDocumentServiceImpl implements ODDocumentService {
    @Autowired
    ODDocumentRepository _repository;

    public ResponseEntity<ODDocument> findById(int id) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<ODDocument>();
        try {
            Optional<ODDocument> item = _repository.findById(id);
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

    public ResponseEntity<ODDocument> findByOdId(int odId) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<ODDocument>();
        try {
            List<ODDocument> items = _repository.findByOdIdAndIsDeleted(odId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ODDocument> save(ODDocument item) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<ODDocument>();
        try {
            ODDocument _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ODDocument> update(ODDocument item) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<ODDocument>();
        try {
            Optional<ODDocument> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                ODDocument _itemUpdate = _repository.save(item);
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

    public ResponseEntity<ODDocument> delete(int id) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<ODDocument>();
        try {
            Optional<ODDocument> item = _repository.findById(id);
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
