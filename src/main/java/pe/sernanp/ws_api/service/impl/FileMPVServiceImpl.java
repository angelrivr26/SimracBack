package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FileMPV;
import pe.sernanp.ws_api.model.ProcedureMPV;
import pe.sernanp.ws_api.repository.FileMPVRepository;
import pe.sernanp.ws_api.service.FileMPVService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileMPVServiceImpl implements FileMPVService {
    @Autowired
    FileMPVRepository _repository;

    @Override
    public ResponseEntity<FileMPV> save2(List<FileMPV> items, ProcedureMPV procedure) throws Exception {
        ResponseEntity<FileMPV> response = new ResponseEntity<FileMPV>();
        try {
            for (FileMPV item: items) {
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
    public ResponseEntity<FileMPV> listByProcedure(int procedureId) throws Exception {
        ResponseEntity<FileMPV> response = new ResponseEntity<>();
        List<FileMPV> items = new ArrayList<>();
        try {
            items = _repository.findByProcedureId(procedureId);
        } catch (Exception ex){
            items = null;
        }
        response.setItems(items);
        return response;
    }
}
