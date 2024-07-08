package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FileMPV;
import pe.sernanp.ws_api.service.FileMPVService;

@RestController
@RequestMapping(value="/filemvp")
@Api(description = "Controlador de servicio de archivos de mesa de partes virtual", tags = "filemvp-controller")
public class FileMPVController extends BaseController{
    @Autowired
    FileMPVService _service;

    @GetMapping("/{procedureId}")
    @ApiOperation(value = "Método que devuelve el detalle de un Trámite", notes = "")
    public ResponseEntity<FileMPV> listByProcedure(@PathVariable("procedureId") int procedureId) throws Exception {
        ResponseEntity<FileMPV> response = new ResponseEntity<>();
        response = this._service.listByProcedure(procedureId);
        return response;
    }
}
