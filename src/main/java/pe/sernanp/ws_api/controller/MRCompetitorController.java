package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRCompetitor;
import pe.sernanp.ws_api.model.SupervisionRecord;
import pe.sernanp.ws_api.service.MRCompetitorService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/monitoring-record/competitor")
@Api(description = "Controladora de servicio para participantes de acta de seguimiento", tags = "monitoring-record-competitor-controller")
public class MRCompetitorController {
    @Autowired
    MRCompetitorService _service;
    @GetMapping("/{id}")
    @ApiOperation(value = "Método que permite obtener el detalle de un participante por el id", notes = "")
    public ResponseEntity<MRCompetitor> detail (@PathVariable("id") int id) throws Exception{
        ResponseEntity<MRCompetitor> response = new ResponseEntity<>();
        response = this._service.detail(id);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un participante", notes = "")
    public ResponseEntity<MRCompetitor> delete (@PathVariable("id") int id) throws Exception{
        ResponseEntity<MRCompetitor> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar un participante", notes = "")
    public ResponseEntity<MRCompetitor> save(@RequestBody MRCompetitor item) throws Exception {
        ResponseEntity<MRCompetitor> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar un participante", notes = "")
    public ResponseEntity<MRCompetitor> update(@RequestBody MRCompetitor item) throws Exception {
        ResponseEntity<MRCompetitor> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @GetMapping("/list-by-sr/{id}")
    @ApiOperation(value = "Método que permite obtener los participantes enviando el id de acta de seguimiento", notes = "")
    public ResponseEntity<MRCompetitor> findByMonitoringRecordId (@PathVariable("id") int id) throws Exception{
        ResponseEntity<MRCompetitor> response = new ResponseEntity<>();
        response = this._service.findByMonitoringRecordId(id);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de participantes de acta de seguimiento", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int id) throws Exception{
        ByteArrayInputStream stream = this._service.export(id);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Acta_Segui_Participantes_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

}
