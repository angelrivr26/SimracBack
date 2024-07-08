package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.VerifiedFactDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.OD;
import pe.sernanp.ws_api.model.SRVerifiedFact;
import pe.sernanp.ws_api.service.SRVerifiedFactService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value="/supervision-record/verified-fact")
@Api(description = "Controladora de servicio para hecho veridico de acta de supervision", tags = "supervision-record-veridic-fact-controller")
public class SRVerifiedFactController extends BaseController {
    @Autowired
    SRVerifiedFactService _service;

//    @GetMapping
//    public ResponseEntity<SRVeridicFact> list() throws Exception {
//        ResponseEntity<SRVeridicFact> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un hecho veridico", notes = "")
    public ResponseEntity<SRVerifiedFact> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar un hecho veridico", notes = "")
    public ResponseEntity<SRVerifiedFact> save(@RequestBody SRVerifiedFact item) throws Exception {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Método que permite registrar un hecho veridico", notes = "")
    public ResponseEntity<SRVerifiedFact> savewithfiles(@RequestParam("item") String item,@RequestParam("files") MultipartFile[] files,@RequestParam("folderId") String folderId) throws Exception {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<>();
        SRVerifiedFact item2 = super.fromJson(item, SRVerifiedFact.class);
        response = this._service.saveWithFiles(item2,files,folderId);
        return response;
    }
    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar un hecho veridico", notes = "")
    public ResponseEntity<SRVerifiedFact> update(@RequestBody SRVerifiedFact item) throws Exception {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un hecho veridico", notes = "")
    public ResponseEntity<SRVerifiedFact> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-sr/{id}")
    @ApiOperation(value = "Método que devuelve listado de hechos veridicos por id de acta de supervision", notes = "")
    public ResponseEntity<VerifiedFactDTO> findByMonitoringRecordId(@PathVariable("id") int supervisionRecordId) throws Exception {
        ResponseEntity<VerifiedFactDTO> response = new ResponseEntity<>();
        response = this._service.findBySupervisionRecordId(supervisionRecordId);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de hecho veridico para acta de supervision", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int managementPlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(managementPlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "ActaSupe_hechoVeridico" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
