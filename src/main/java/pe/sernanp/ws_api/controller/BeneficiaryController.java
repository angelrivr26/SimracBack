package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Beneficiary;
import pe.sernanp.ws_api.service.BeneficiaryService;

@RestController
@RequestMapping(value="/beneficiary")
@Api(description = "Controladora de servicio de beneficiario", tags = "beneficiary-controller")
public class BeneficiaryController {
    @Autowired
    BeneficiaryService _service;

    @PostMapping("/find-by-document-number")
    @ApiOperation(value = "Método que devuelve el listado de una tabla maestra", notes = "")
    public ResponseEntity<Beneficiary> findByDocumentNumber(@RequestBody Beneficiary item) throws Exception {
        ResponseEntity<Beneficiary> response = new ResponseEntity<>();
        response = this._service.findByDocumentNumber(item);
        return response;
    }

    @GetMapping()
    @ApiOperation(value = "Método que devuelve el listado de beneficiarios", notes = "")
    public ResponseEntity<Beneficiary> list() throws Exception {
        ResponseEntity<Beneficiary> response = new ResponseEntity<>();
        response = this._service.findAll();
        return response;
    }
}
