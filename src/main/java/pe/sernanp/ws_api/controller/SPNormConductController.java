package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FiscalObligation;
import pe.sernanp.ws_api.service.FiscalObligationService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/site-plan/norm-conduct")
@Api(description = "Controladora de servicio para norma de conducta de plan de sitio", tags = "site-plan-norm-conduct-controller")
public class SPNormConductController  {
    @Autowired
//    SPNormConductService _service;
    FiscalObligationService _service;

//    @GetMapping
//    public ResponseEntity<SPNormConduct> list() throws Exception {
//        ResponseEntity<SPNormConduct> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una norma de conducta", notes = "")
    public ResponseEntity<FiscalObligation> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la norma de conducta", notes = "")
    public ResponseEntity<FiscalObligation> save(@RequestBody FiscalObligation item) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la norma de conducta", notes = "")
    public ResponseEntity<FiscalObligation> update(@RequestBody FiscalObligation item) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una norma de conducta", notes = "")
    public ResponseEntity<FiscalObligation> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-ps/{sitePlanId}")
    @ApiOperation(value = "Método que devuelve listado de normas de conducta por id de plan de sitio", notes = "")
    public ResponseEntity<FiscalObligation> findBySitePlanId(@PathVariable("sitePlanId") int sitePlanId) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<>();
        response = this._service.findBySitePlanId(sitePlanId);
        return response;
    }

    @RequestMapping(value = "/export/{sitePlanId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de normas de conducta para plan de sitio", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("sitePlanId") int sitePlanId) throws Exception{
        ByteArrayInputStream stream = this._service.exportBySitePlan(sitePlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "PS_Norma_Conducta_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
