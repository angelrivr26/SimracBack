package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Modality;
import pe.sernanp.ws_api.model.ModalityRequirement;
import pe.sernanp.ws_api.service.ModalityRequirementService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/modality/requirement")
@Api(description = "Controladora de servicio de Requisitos de una Modalidad", tags = "modality-of-use-requirement-controller")
public class ModalityRequirementController extends BaseController {
    @Autowired
    ModalityRequirementService _service;

//    @GetMapping
//    public ResponseEntity<ModalityRequirement> list() throws Exception {
//        ResponseEntity<ModalityRequirement> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un Requisito de una Modalidad", notes = "")
    public ResponseEntity<ModalityRequirement> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

//    @PostMapping()
//    @ApiOperation(value = "Método que permite registrar un Requisito de una Modalidad", notes = "")
//    public ResponseEntity<ModalityRequirement> save(@RequestParam("item") String item, @RequestParam("file") MultipartFile file) throws Exception {
//        ResponseEntity<ModalityRequirement> response = new ResponseEntity<>();
//        ModalityRequirement item2 = super.fromJson(item,ModalityRequirement.class);
//        response = this._service.save(item2, file);
//        return response;
//    }

//    @PutMapping()
//    @ApiOperation(value = "Método que permite actualizar un Requisito de una Modalidad", notes = "")
//    public ResponseEntity<ModalityRequirement> update(@RequestParam("item") String item, @RequestParam("file") MultipartFile file) throws Exception {
//        ResponseEntity<ModalityRequirement> response = new ResponseEntity<>();
//        ModalityRequirement item2 = super.fromJson(item,ModalityRequirement.class);
//        response = this._service.update(item2, file);
//        return response;
//    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar un Requisito de una Modalidad", notes = "")
    public ResponseEntity<ModalityRequirement> save(@RequestParam("item") String item, @RequestParam("documentFile") MultipartFile documentFile, @RequestParam("templateFile") MultipartFile templateFile) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<>();
        ModalityRequirement item2 = super.fromJson(item,ModalityRequirement.class);
        response = this._service.save(item2, documentFile, templateFile);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar un Requisito de una Modalidad", notes = "")
    public ResponseEntity<ModalityRequirement> update(@RequestParam("item") String item, @RequestParam("documentFile") MultipartFile documentFile, @RequestParam("templateFile") MultipartFile templateFile) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<>();
        ModalityRequirement item2 = super.fromJson(item,ModalityRequirement.class);
        response = this._service.update(item2, documentFile, templateFile);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un Requisito de una Modalidad", notes = "")
    public ResponseEntity<ModalityRequirement> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-modality/{id}")
    @ApiOperation(value = "Método que devuelve los Requisitos de una Modalidad", notes = "")
    public ResponseEntity<ModalityRequirement> findByModalityId(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<>();
        response = this._service.findByModalityId(id);
        return response;
    }

    @RequestMapping(value = "/export/{modaliryId}", method = RequestMethod.GET)
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("modaliryId") int modaliryId) throws Exception{
        ByteArrayInputStream stream = this._service.export(modaliryId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Modalidad_PreRequisitos" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

    @RequestMapping(value = "/download-file/{fileId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que el devuelve documento requisito de modalidad", notes = "")
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
