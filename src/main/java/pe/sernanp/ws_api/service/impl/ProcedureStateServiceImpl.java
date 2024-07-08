package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ProcedureState;
import pe.sernanp.ws_api.repository.ProcedureStateRepository;
import pe.sernanp.ws_api.service.ProcedureStateService;

import java.util.Optional;

@Service
public class ProcedureStateServiceImpl implements ProcedureStateService {

    @Autowired
    ProcedureStateRepository _repository;

    @Override
    public ResponseEntity<ProcedureState> save(ProcedureState item) throws Exception {
        ResponseEntity<ProcedureState> response = new ResponseEntity<ProcedureState>();
        try {
            ProcedureState _item = _repository.save(item);
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

    @Override
    public ResponseEntity<ProcedureState> listByTramite(int id) throws Exception {
        ResponseEntity<ProcedureState> response = new ResponseEntity<ProcedureState>();
        try {
            ProcedureState item = _repository.listbytramite(id);
            response.setItem(item);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<ProcedureState> update(ProcedureState item) throws Exception {
        ResponseEntity<ProcedureState> response = new ResponseEntity<ProcedureState>();
        try {
            Optional<ProcedureState> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                ProcedureState _itemUpdate = _repository.save(item);
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

    @Override
    public ResponseEntity<ProcedureState> listTramiteActivo(int id) throws Exception {
        ResponseEntity<ProcedureState> response = new ResponseEntity<ProcedureState>();
        try {
            ProcedureState item = _repository.listbytramiteactivo(id);
            response.setItem(item);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
}
