package pe.sernanp.ws_api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.EvaluationEconomic;
import pe.sernanp.ws_api.service.EvaluationEconomicService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping("/evaluationeconomic")
public class EvaluationEconomicController extends BaseController {

    @Autowired
    EvaluationEconomicService _service;

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la evaluacion economica", notes = "")
    public ResponseEntity<EvaluationEconomic> save(@RequestParam("item") String item, @RequestParam("file") MultipartFile file) throws Exception {
        ResponseEntity<EvaluationEconomic> response = new ResponseEntity<>();
        EvaluationEconomic item2 = super.fromJson(item, EvaluationEconomic.class);
        response = this._service.save(item2, file);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la evaluacion economica", notes = "")
    public ResponseEntity<EvaluationEconomic> update(@RequestParam("item") String item, @RequestParam("file") MultipartFile file) throws Exception {
        ResponseEntity<EvaluationEconomic> response = new ResponseEntity<>();
        EvaluationEconomic item2 = super.fromJson(item, EvaluationEconomic.class);
        response = this._service.update(item2, file);
        return response;
    }

    @RequestMapping(value = "/list-by-procedure/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve listado de evaluacion economica por idTramite", notes = "")
    public ResponseEntity<EvaluationEconomic> listByTramite(@PathVariable("id") int id) throws Exception {
        ResponseEntity<EvaluationEconomic> response = new ResponseEntity<>();
        response = this._service.listByTramite(id);
        return response;
    }

    @RequestMapping(value = "/download-file/{fileId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que el devuelve documento de evaluacion economica", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity getFile(@PathVariable("fileId") String fileId) throws Exception{
        byte[] bytes = this._service.getFile(true, fileId);
        String fileName = _service.getFileName(fileId);
        LocalDate dateActual = LocalDate.now();
        fileName = fileName == "" ? "Archivo_sin_Nombre_" + dateActual : fileName.replaceAll("\\s", "_").replace("\u00a0","_");
        if (fileName != "" && bytes != null && bytes.length > 0) {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename*=utf-8''" + fileName);
            return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
        }
        else
            return new org.springframework.http.ResponseEntity<String>("El documento " + (fileName == "" ? "solicitado" : fileName) + " no existe o esta vacio.", HttpStatus.OK);
    }

}
