package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.SRFiscalObligationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRFiscalObligation;
import pe.sernanp.ws_api.service.SRFiscalObligationService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/supervision-report/fiscal-obligation")
@Api(description = "Controladora de servicio para obligaciones fiscales para informe de supervisión", tags = "supervision-report-fiscal-obligation-controller")
public class SRFiscalObligationController extends BaseController {
    @Autowired
    SRFiscalObligationService _service;

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una area de manejo", notes = "")
    public ResponseEntity<SRFiscalObligation> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SRFiscalObligation> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la area de manejo", notes = "")
    public ResponseEntity<SRFiscalObligation> save(@RequestBody SRFiscalObligation item) throws Exception {
        ResponseEntity<SRFiscalObligation> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la area de manejo", notes = "")
    public ResponseEntity<SRFiscalObligation> update(@RequestBody SRFiscalObligation item) throws Exception {
        ResponseEntity<SRFiscalObligation> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una area de manejo", notes = "")
    public ResponseEntity<SRFiscalObligation> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SRFiscalObligation> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-is/{supervisionReportId}")
    @ApiOperation(value = "Método que devuelve listado de areas de manejo por id de plan de manejo", notes = "")
    public ResponseEntity<SRFiscalObligationDTO> findBySupervisionReportId(@PathVariable("supervisionReportId") int supervisionReportId) throws Exception {
        ResponseEntity<SRFiscalObligationDTO> response = new ResponseEntity<>();
        response = this._service.findBySupervisionReportId(supervisionReportId);
        return response;
    }

    @GetMapping("/list-for-is/{supervisionReportId}")
    @ApiOperation(value = "Método que devuelve listado de areas de manejo por id de plan de manejo", notes = "")
    public ResponseEntity<ListDetailDTO> listFiscalObligationBySupervisionReportId(@PathVariable("supervisionReportId") int supervisionReportId) throws Exception {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<>();
        response = this._service.listFiscalObligationBySupervisionReportId(supervisionReportId);
        return response;
    }
}
