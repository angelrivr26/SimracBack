package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.sernanp.ws_api.dto.CriterionDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Criterion;
import pe.sernanp.ws_api.service.CriterionService;

@RestController
@RequestMapping(value="/criterion")
@Api(description = "Controladora de servicio de Criterios", tags = "criterion-controller")
public class CriterionController extends BaseController {
    @Autowired
    CriterionService _service;

    @GetMapping("/list/{typeId}/{component}")
    @ApiOperation(value = "Método que devuelve listado de Criterios", notes = "")
    public ResponseEntity<Criterion> list(@PathVariable("typeId") int typeId, @PathVariable("component") int component) throws Exception {
        ResponseEntity<Criterion> response = new ResponseEntity<>();
        response = this._service.findAll(typeId, component);
        return response;
    }

    @GetMapping("/listByType/{typeId}/{modality}/{procedure}")
    @ApiOperation(value = "Método que devuelve listado de Criterios Por Tipo", notes = "")
    public ResponseEntity<CriterionDTO> listbyType(@PathVariable("typeId") int typeId, @PathVariable("modality") int modality, @PathVariable("procedure") int procedureId) throws Exception {
        ResponseEntity<CriterionDTO> response = new ResponseEntity<>();
        response = this._service.findByType(typeId, modality, procedureId);
        return response;
    }
}
