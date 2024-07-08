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
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRCompliance;
import pe.sernanp.ws_api.model.SRVerifiedFact;
import pe.sernanp.ws_api.service.MRComplianceService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/monitoring-record/compliance")
@Api(description = "Controladora de servicio para cumplimientos de acta de seguimiento", tags = "monitoring-record-compliance-controller")
public class MRComplianceController extends BaseController {
    @Autowired
    MRComplianceService _service;

    @GetMapping
    public ResponseEntity<MRCompliance> list() throws Exception {
        ResponseEntity<MRCompliance> response = new ResponseEntity<>();
        response = this._service.findAll();
        return response;
    }
    @GetMapping("/listForComboByMonitoringRecordId/{monitoringRecordId}")
    public ResponseEntity<ListDetailDTO> listForCombo(@PathVariable("monitoringRecordId") int monitoringRecordId) throws Exception {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<>();
        response = this._service.complianceForList(monitoringRecordId);
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un cumplimiento", notes = "")
    public ResponseEntity<ComplianceEntity> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ComplianceEntity> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar un cumplimiento", notes = "")
    public ResponseEntity<MRCompliance> save(@RequestBody MRCompliance item) throws Exception {
        ResponseEntity<MRCompliance> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar un cumplimiento", notes = "")
    public ResponseEntity<MRCompliance> update(@RequestBody MRCompliance item) throws Exception {
        ResponseEntity<MRCompliance> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un cumplimiento", notes = "")
    public ResponseEntity<MRCompliance> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MRCompliance> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de cumplimientos para acta de seguimiento", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int managementPlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(managementPlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "ActaSeg_compromisos" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Método que permite registrar un cumplimiento con documentos", notes = "")
    public ResponseEntity<MRCompliance> savewithfiles(@RequestParam("item") String item, @RequestParam("files") MultipartFile[] files, @RequestParam("folderId") String folderId) throws Exception {
        ResponseEntity<MRCompliance> response = new ResponseEntity<>();
        MRCompliance item2 = super.fromJson(item, MRCompliance.class);
        response = this._service.saveWithFiles(item2,files,folderId);
        return response;
    }
    @GetMapping("/list-by-mr/{id}")
    @ApiOperation(value = "Método que devuelve listado de cumplimientos por id de acta de seguimiento", notes = "")
    public ResponseEntity<ComplianceEntity> findByMonitoringRecordId(@PathVariable("id") int monitoringRecordId) throws Exception {
        ResponseEntity<ComplianceEntity> response = new ResponseEntity<>();
        response = this._service.findByMonitoringRecordId(monitoringRecordId);
        return response;
    }
}
