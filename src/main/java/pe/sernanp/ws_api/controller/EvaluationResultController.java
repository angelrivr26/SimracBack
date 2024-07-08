package pe.sernanp.ws_api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.EvaluationResult;
import pe.sernanp.ws_api.service.EvaluationResultService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping("/evaluationresult")
public class EvaluationResultController {

    @Autowired
    EvaluationResultService _service;

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la evaluacion economica", notes = "")
    public ResponseEntity<EvaluationResult> save(@RequestBody EvaluationResult item) throws Exception {
        ResponseEntity<EvaluationResult> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la evaluacion economica", notes = "")
    public ResponseEntity<EvaluationResult> update(@RequestBody EvaluationResult item) throws Exception {
        ResponseEntity<EvaluationResult> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @RequestMapping(value = "/list-by-procedure/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve listado de evaluacion economica por idTramite", notes = "")
    public ResponseEntity<EvaluationResult> listByTramite(@PathVariable("id") int id) throws Exception {
        ResponseEntity<EvaluationResult> response = new ResponseEntity<>();
        response = this._service.listByTramite(id);
        return response;
    }

    @RequestMapping(value = "/export-by-procedure/{procedureId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de pda sectores por idAnp", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("procedureId") int procedureId) throws Exception{
        ByteArrayInputStream stream = this._service.export(procedureId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Resultado_Evaluacion_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

}
