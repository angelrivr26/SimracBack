package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.RecommendationEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRCompliance;
import pe.sernanp.ws_api.model.MRRecommendation;
import pe.sernanp.ws_api.service.MRRecommendationService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/monitoring-record/recommendation")
@Api(description = "Controladora de servicio para recomendaciones de acta de seguimiento", tags = "monitoring-record-recommendation-controller")
public class MRRecommendationController extends BaseController {
    @Autowired
    MRRecommendationService _service;

//    @GetMapping
//    public ResponseEntity<MRRecommendation> list() throws Exception {
//        ResponseEntity<MRRecommendation> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una recomendacion", notes = "")
    public ResponseEntity<RecommendationEntity> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<RecommendationEntity> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar una recomendacion", notes = "")
    public ResponseEntity<MRRecommendation> save(@RequestBody MRRecommendation item) throws Exception {
        ResponseEntity<MRRecommendation> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar una recomendacion", notes = "")
    public ResponseEntity<MRRecommendation> update(@RequestBody MRRecommendation item) throws Exception {
        ResponseEntity<MRRecommendation> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una recomendacion", notes = "")
    public ResponseEntity<MRRecommendation> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MRRecommendation> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-mr/{id}")
    @ApiOperation(value = "Método que devuelve listado de recomendaciones por id de acta de seguimiento", notes = "")
    public ResponseEntity<RecommendationEntity> findByMonitoringRecordId(@PathVariable("id") int monitoringRecordId) throws Exception {
        ResponseEntity<RecommendationEntity> response = new ResponseEntity<>();
        response = this._service.findByMonitoringRecordId(monitoringRecordId);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de recomendaciones para acta de seguimiento", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int managementPlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(managementPlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "ActaSeg_Recomendaciones" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Método que permite registrar un cumplimiento con documentos", notes = "")
    public ResponseEntity<MRRecommendation> savewithfiles(@RequestParam("item") String item, @RequestParam("fileEvaluation") MultipartFile fileEvaluation, @RequestParam("files") MultipartFile[] files, @RequestParam("folderId") String folderId) throws Exception {
        ResponseEntity<MRRecommendation> response = new ResponseEntity<>();
        MRRecommendation item2 = super.fromJson(item, MRRecommendation.class);
        response = this._service.saveWithFiles(item2,fileEvaluation,files,folderId);
        return response;
    }
}
