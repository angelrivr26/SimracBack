package pe.sernanp.ws_api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.PdaSectores;
import pe.sernanp.ws_api.model.SitePlan;
import pe.sernanp.ws_api.service.PdaSectoresService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping("/anpSectores")
public class PdaSectoresController extends BaseController {

    @Autowired
    PdaSectoresService _service;

    @RequestMapping(value = "/list-by-anp/{idAnp}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve listado de pda sectores por idAnp", notes = "")
    public ResponseEntity<PdaSectores> listByAnp(@PathVariable("idAnp") int idAnp) throws Exception {
        ResponseEntity<PdaSectores> response = new ResponseEntity<>();
        response = this._service.listByAnp(idAnp);
        return response;
    }

    @RequestMapping(value = "/list-by-anps/{anpIds}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve listado de pda sectores por idAnp", notes = "")
    public ResponseEntity<PdaSectores> listByAnp(@PathVariable("anpIds") String anpIds) throws Exception {
        ResponseEntity<PdaSectores> response = new ResponseEntity<>();
        response = this._service.listByAnps(anpIds);
        return response;
    }

    @RequestMapping(value = "/export-by-anps/{anpIds}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de pda sectores por idAnp", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("anpIds") String anpIds) throws Exception{
        ByteArrayInputStream stream = this._service.export(anpIds);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Pda_Sectores_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
