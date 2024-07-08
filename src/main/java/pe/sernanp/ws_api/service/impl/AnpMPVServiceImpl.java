package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.AnpMPV;
import pe.sernanp.ws_api.model.ProcedureMPV;
import pe.sernanp.ws_api.repository.AnpMPVRepository;
import pe.sernanp.ws_api.service.AnpMPVService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnpMPVServiceImpl implements AnpMPVService {
    @Autowired
    AnpMPVRepository _repository;

    @Override
    public ResponseEntity<AnpMPV> save2(List<AnpMPV> items, ProcedureMPV procedure) throws Exception {
        ResponseEntity<AnpMPV> response = new ResponseEntity<AnpMPV>();
        try {
            for (AnpMPV item: items) {
                item.setProcedure(procedure);
                item = _repository.save(item);
            }
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error.");
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public List<AnpMPV> listByProcedure(int procedureId) throws Exception {
        List<AnpMPV> response = new ArrayList<>();
        try {
            response = _repository.findByProcedureId(procedureId);
        } catch (Exception ex){
            response = null;
        }
        return response;
    }
}
