package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ODFiscalObligation;
import pe.sernanp.ws_api.repository.ODFiscalObligationRepository;
import pe.sernanp.ws_api.service.ODFiscalObligationService;

import java.util.List;
import java.util.Optional;

@Service
public class ODFiscalObligationServiceImpl implements ODFiscalObligationService {
    @Autowired
    ODFiscalObligationRepository _repository;

    public ResponseEntity<ODFiscalObligation> save(ODFiscalObligation item) throws Exception {
        ResponseEntity<ODFiscalObligation> response = new ResponseEntity<ODFiscalObligation>();
        try {
            ODFiscalObligation _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<FiscalObligationDTO> listByOd(int odId) throws Exception {
        ResponseEntity<FiscalObligationDTO> response = new ResponseEntity<FiscalObligationDTO>();
        try {
            List<FiscalObligationDTO> items = _repository.listByODId(odId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ODFiscalObligation> delete(int id) throws Exception {
        ResponseEntity<ODFiscalObligation> response = new ResponseEntity<ODFiscalObligation>();
        try {
            Optional<ODFiscalObligation> item = _repository.findById(id);
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
