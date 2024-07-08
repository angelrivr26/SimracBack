package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Modality;
import pe.sernanp.ws_api.model.ModalityStage;
import pe.sernanp.ws_api.service.ModalityStageService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/modality/stage")
@Api(description = "Controladora de servicio de Etapas de una Modalidad", tags = "modality-of-use-stage-controller")
public class ModalityStageController {
    @Autowired
    ModalityStageService _service;

//    @GetMapping
//    public ResponseEntity<ModalityStage> list() throws Exception {
//        ResponseEntity<ModalityStage> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un Etapa de una Modalidad", notes = "")
    public ResponseEntity<ModalityStage> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la Etapa de una Modalidad", notes = "")
    public ResponseEntity<ModalityStage> save(@RequestBody ModalityStage item) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la Etapa de una Modalidad", notes = "")
    public ResponseEntity<ModalityStage> update(@RequestBody ModalityStage item) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar la Etapa de una Modalidad", notes = "")
    public ResponseEntity<ModalityStage> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-modality/{id}")
    @ApiOperation(value = "Método que devuelve las Etapas de una Modalidad", notes = "")
    public ResponseEntity<ModalityStage> findByMoldality(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<>();
        response = this._service.findByModalityId(id);
        return response;
    }

    @RequestMapping(value = "/export/{modaliryId}", method = RequestMethod.GET)
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("modaliryId") int modaliryId) throws Exception{
        ByteArrayInputStream stream = this._service.export(modaliryId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Modalidad_Etapas" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
