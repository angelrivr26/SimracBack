package pe.sernanp.ws_api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ProcedureState;
import pe.sernanp.ws_api.service.ProcedureStateService;

@RestController
@RequestMapping("/procedurestate")
public class ProcedureStateController extends BaseController {

    @Autowired
    ProcedureStateService _service;

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar el estado del tramite", notes = "")
    public ResponseEntity<ProcedureState> save(@RequestBody ProcedureState item) throws Exception {
        ResponseEntity<ProcedureState> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @RequestMapping(value = "/list-by-procedure/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve el estado del tramite por idTramite", notes = "")
    public ResponseEntity<ProcedureState> listByTramite(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ProcedureState> response = new ResponseEntity<>();
        response = this._service.listByTramite(id);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar el estado del estado del tramite", notes = "")
    public ResponseEntity<ProcedureState> update(@RequestBody ProcedureState item) throws Exception {
        ResponseEntity<ProcedureState> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @RequestMapping(value = "/list-procedureactivo/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve list de tramite activo por idTramite", notes = "")
    public ResponseEntity<ProcedureState> listTramiteActivo(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ProcedureState> response = new ResponseEntity<>();
        response = this._service.listTramiteActivo(id);
        return response;
    }

}
