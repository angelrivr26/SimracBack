package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.AnpDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Anp;
import pe.sernanp.ws_api.service.AnpService;

@RestController
@RequestMapping(value="/anp")
@Api(description = "Controladora de servicio de ANP", tags = "anp-controller")
public class AnpController {
    @Autowired
    AnpService _service;

    @GetMapping
    @ApiOperation(value = "Método que devuelve listado de ANP no relacionados a ConfigAnp", notes = "")
    public ResponseEntity<AnpDTO> list() throws Exception {
        ResponseEntity<AnpDTO> response = new ResponseEntity<>();
        response = this._service.findAll();
        return response;
    }

    @RequestMapping(value = "/list-not-related2", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve listado de ANP no relacionados a ConfigAnp", notes = "")
    public ResponseEntity<AnpDTO> listNotRelatedAnpConfig2() throws Exception {
        ResponseEntity<AnpDTO> response = new ResponseEntity<>();
        response = this._service.listNotRelatedAnpConfig2();
        return response;
    }

    @RequestMapping(value = "/list-by-modality/{modalityId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve listado de ANP no relacionados a ConfigAnp", notes = "")
    public ResponseEntity<AnpDTO> listByModality(@PathVariable("modalityId") int modalityId) throws Exception {
        ResponseEntity<AnpDTO> response = new ResponseEntity<>();
        response = this._service.listByModality(modalityId);
        return response;
    }
}
