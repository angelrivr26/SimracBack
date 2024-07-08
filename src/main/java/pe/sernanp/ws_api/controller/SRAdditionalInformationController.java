package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRAdditionalInformation;
import pe.sernanp.ws_api.service.SRAdditionalInformationService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/supervision-record/additional-information")
@Api(description = "Controladora de servicio para informacion adicional de acta de supervision", tags = "supervision-record-additional-information-controller")
public class SRAdditionalInformationController extends BaseController {
    @Autowired
    SRAdditionalInformationService _service;

//    @GetMapping
//    public ResponseEntity<SRAdditionalInformation> list() throws Exception {
//        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una informacion adicional", notes = "")
    public ResponseEntity<SRAdditionalInformation> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar una informacion adicional", notes = "")
    public ResponseEntity<SRAdditionalInformation> save(@RequestBody SRAdditionalInformation item) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar una informacion adicional", notes = "")
    public ResponseEntity<SRAdditionalInformation> update(@RequestBody SRAdditionalInformation item) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una informacion adicional", notes = "")
    public ResponseEntity<SRAdditionalInformation> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-sr/{id}")
    @ApiOperation(value = "Método que devuelve listado de informacion adicional por id de acta de supervision", notes = "")
    public ResponseEntity<SRAdditionalInformation> findByMonitoringRecordId(@PathVariable("id") int supervisionRecordId) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<>();
        response = this._service.findBySupervisionRecordId(supervisionRecordId);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de informacion adicional para acta de supervision", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int managementPlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(managementPlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "ActaSupe_infoAdicional" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
