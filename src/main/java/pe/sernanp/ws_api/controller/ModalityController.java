package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.model.Modality;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.ModalityService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/modality")
@Api(description = "Controladora de servicio de Modalidades", tags = "modality-of-use-controller")
public class ModalityController extends BaseController {
    @Autowired
    ModalityService _service;
    @Autowired
    DocumentService _serviceDocument;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve listado de Modalidades con filtros", notes = "")
    public ResponseEntity<ModalityDTO> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<ModalityDTO> response = new ResponseEntity<ModalityDTO>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            Modality item2 = super.fromJson(item, Modality.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @GetMapping
    @ApiOperation(value = "Método que devuelve listado de Modalidades", notes = "")
    public ResponseEntity<Modality> list() throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<>();
        response = this._service.findAll();
        return response;
    }

    @GetMapping ("/list-by-tupa/{isTupa}")
    @ApiOperation(value = "Método que devuelve listado de Modalidades relacionadas en AnpConfig", notes = "")
    public ResponseEntity<Modality> findByTupa(@PathVariable("isTupa") boolean isTupa) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<>();
        response = this._service.findByTupa(isTupa);
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una Modalidad", notes = "")
    public ResponseEntity<Modality> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar una Modalidad", notes = "")
    public ResponseEntity<Modality> saveDraft(@RequestParam("item") String item, @RequestParam("file") MultipartFile file) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<>();
        Modality item2 = super.fromJson(item, Modality.class);
        response = this._service.save(item2, file);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar una Modalidad", notes = "")
    public ResponseEntity<Modality> update(@RequestParam("item") String item, @RequestParam("file") MultipartFile file) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<>();
        Modality item2 = super.fromJson(item, Modality.class);
        response = this._service.update(item2, file);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una Modalidad", notes = "")
    public ResponseEntity<Modality> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping ("/list-related-anpconfig/{typeId}")
    @ApiOperation(value = "Método que devuelve listado de Modalidades relacionadas en AnpConfig", notes = "")
    public ResponseEntity<Modality> listByAnpConfig(@PathVariable("typeId") int typeId) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<>();
        response = this._service.listByAnpConfig(typeId);
        return response;
    }

    @GetMapping ("/list-by-type/{typeId}")
    @ApiOperation(value = "Método que devuelve listado de Modalidades relacionadas en AnpConfig", notes = "")
    public ResponseEntity<Modality> listByType(@PathVariable("typeId") int typeId) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<>();
        response = this._service.listByType(typeId);
        return response;
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve reporte en excel de modalidades", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@RequestBody Modality item) throws Exception{
        ByteArrayInputStream stream = this._service.export(item);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Modalidades" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

    @RequestMapping(value = "/download-file/{fileId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve documento de modalidad", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity getFile(@PathVariable("fileId") String fileId) throws Exception{
        byte[] bytes = this._service.getFile(true, fileId);
        String fileName = _service.getFileName(fileId);
        LocalDate dateActual = LocalDate.now();
        fileName = fileName == "" ? "Archivo_sin_Nombre_" + dateActual : fileName.replaceAll("\\s", "_").replace("\u00a0","_");
        if (fileName != "" && bytes != null && bytes.length > 0) {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename*=utf-8''"+  fileName);
            return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
        }
        else
            return new org.springframework.http.ResponseEntity<String>("El documento " + (fileName == "" ? "solicitado" : fileName) + " no existe o esta vacio.", HttpStatus.OK);
    }
}
