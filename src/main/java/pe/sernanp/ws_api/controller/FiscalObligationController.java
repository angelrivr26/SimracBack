package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FiscalObligation;
import pe.sernanp.ws_api.model.ODFiscalObligation;
import pe.sernanp.ws_api.service.FiscalObligationService;
import pe.sernanp.ws_api.service.ODFiscalObligationService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
@RestController
@RequestMapping(value="/fiscal-obligation")
@Api(description = "Controladora de servicio para obligación fiscal", tags = "fiscal-obligation-controller")
public class FiscalObligationController extends BaseController {
    @Autowired
    FiscalObligationService _service;
    @Autowired
    ODFiscalObligationService _serviceODFiscalObligation;

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una obligación fiscal", notes = "")
    public ResponseEntity<FiscalObligation> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar una obligación fiscal", notes = "")
    public ResponseEntity<FiscalObligation> save(@RequestBody FiscalObligation item) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar una obligación fiscal", notes = "")
    public ResponseEntity<FiscalObligation> update(@RequestBody FiscalObligation item) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una obligación fiscal", notes = "")
    public ResponseEntity<FiscalObligation> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-pm/{managementPlanId}")
    @ApiOperation(value = "Método que devuelve listado de obligaciones fiscales por id de plan de manejo", notes = "")
    public ResponseEntity<FiscalObligation> findByManagementPlanId(@PathVariable("managementPlanId") int managementPlanId) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.findByManagementPlanId(managementPlanId);
        return response;
    }

    @GetMapping("/list-by-ps/{sitePlanId}")
    @ApiOperation(value = "Método que devuelve listado de obligaciones fiscales por id de plan de sitio", notes = "")
    public ResponseEntity<FiscalObligation> findBySitePlanId(@PathVariable("sitePlanId") int sitePlanId) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.findBySitePlanId(sitePlanId);
        return response;
    }

    @GetMapping("/list-for-od/{odId}")
    @ApiOperation(value = "Método que devuelve listado de obligaciones fiscales para otorgamiento", notes = "")
    public ResponseEntity<FiscalObligationDTO> listForOd(@PathVariable("odId") int odId) throws Exception {
        ResponseEntity<FiscalObligationDTO> response = new ResponseEntity<>();
        response = this._service.listForOd(odId);
        return response;
    }

    @GetMapping("/list-by-od/{odId}")
    @ApiOperation(value = "Método que devuelve listado de obligaciones fiscales por id de otorgamiento", notes = "")
    public ResponseEntity<FiscalObligationDTO> listByOd(@PathVariable("odId") int odId) throws Exception {
        ResponseEntity<FiscalObligationDTO> response = new ResponseEntity<>();
        response = this._serviceODFiscalObligation.listByOd(odId);
        return response;
    }

    @RequestMapping(value = "/export-by-ps/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de actividades permitidas para plan de manejo", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> exportBySitePlan(@PathVariable("id") int sitePlanId) throws Exception{
        ByteArrayInputStream stream = this._service.exportBySitePlan(sitePlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "PS_Obligacion_Fiscal_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

    @RequestMapping(value = "/export-by-pm/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de actividades permitidas para plan de manejo", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> exportByManagementPlan(@PathVariable("id") int managementPlanId) throws Exception{
        ByteArrayInputStream stream = this._service.exportByManagementPlan(managementPlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "PM_Obligacion_Fiscal_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
