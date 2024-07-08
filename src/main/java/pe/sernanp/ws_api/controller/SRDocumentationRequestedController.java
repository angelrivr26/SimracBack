package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRDocumentationRequested;
import pe.sernanp.ws_api.service.SRDocumentationRequestedService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/supervision-record/documentation-requested")
@Api(description = "Controladora de servicio para documentacion requerida de acta de supervision", tags = "supervision-record-documentation-requested-controller")
public class SRDocumentationRequestedController extends BaseController {
    @Autowired
    SRDocumentationRequestedService _service;

//    @GetMapping
//    public ResponseEntity<SRDocumentationRequested> list() throws Exception {
//        ResponseEntity<SRDocumentationRequested> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una documentacion requerida", notes = "")
    public ResponseEntity<SRDocumentationRequested> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SRDocumentationRequested> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar una documentacion requerida", notes = "")
    public ResponseEntity<SRDocumentationRequested> save(@RequestBody SRDocumentationRequested item) throws Exception {
        ResponseEntity<SRDocumentationRequested> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar una documentacion requerida", notes = "")
    public ResponseEntity<SRDocumentationRequested> update(@RequestBody SRDocumentationRequested item) throws Exception {
        ResponseEntity<SRDocumentationRequested> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una documentacion requerida", notes = "")
    public ResponseEntity<SRDocumentationRequested> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SRDocumentationRequested> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-sr/{id}")
    @ApiOperation(value = "Método que devuelve listado de documentacion requerida por id de acta de supervision", notes = "")
    public ResponseEntity<SRDocumentationRequested> findBySupervisionRecordId(@PathVariable("id") int supervisionRecordId) throws Exception {
        ResponseEntity<SRDocumentationRequested> response = new ResponseEntity<>();
        response = this._service.findBySupervisionRecordId(supervisionRecordId);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de documentacion requerida para acta de supervision", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int managementPlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(managementPlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "ActaSupe_docRequerida" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
