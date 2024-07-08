package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ComplianceEntity;
import pe.sernanp.ws_api.dto.DocumentationRequestDTO;
import pe.sernanp.ws_api.dto.DocumentationRequestEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRCompliance;
import pe.sernanp.ws_api.model.MRDocumentationRequested;
import pe.sernanp.ws_api.model.SRDocumentationRequested;
import pe.sernanp.ws_api.service.MRDocumentationRequestedService;
import pe.sernanp.ws_api.service.SRDocumentationRequestedService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/monitoring-record/documentation-requested")
@Api(description = "Controladora de servicio para documentacion requerida de acta de seguimiento", tags = "monitoring-record-documentation-requested-controller")
public class MRDocumentationRequestedController extends BaseController {
    @Autowired
    MRDocumentationRequestedService _service;


    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una documentacion requerida", notes = "")
    public ResponseEntity<MRDocumentationRequested> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar una documentacion requerida", notes = "")
    public ResponseEntity<MRDocumentationRequested> save(@RequestBody MRDocumentationRequested item) throws Exception {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar una documentacion requerida", notes = "")
    public ResponseEntity<MRDocumentationRequested> update(@RequestBody MRDocumentationRequested item) throws Exception {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una documentacion requerida", notes = "")
    public ResponseEntity<MRDocumentationRequested> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<>();
        response = this._service.delete(id);
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

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Método que permite registrar un cumplimiento con documentos", notes = "")
    public ResponseEntity<MRDocumentationRequested> savewithfiles(@RequestParam("item") String item, @RequestParam("file") MultipartFile file, @RequestParam("folderId") String folderId) throws Exception {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<>();
        MRDocumentationRequested item2 = super.fromJson(item, MRDocumentationRequested.class);
        response = this._service.saveWithFiles(item2,file,folderId);
        return response;
    }
    @GetMapping("/list-by-mr/{id}")
    @ApiOperation(value = "Método que devuelve listado de cumplimientos por id de acta de seguimiento", notes = "")
    public ResponseEntity<DocumentationRequestDTO> findByMonitoringRecordId(@PathVariable("id") int monitoringRecordId) throws Exception {
        ResponseEntity<DocumentationRequestDTO> response = new ResponseEntity<>();
        response = this._service.findByMonitoringRecordId(monitoringRecordId);
        return response;
    }
}
