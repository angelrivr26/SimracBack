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
import pe.sernanp.ws_api.model.Addendum;
import pe.sernanp.ws_api.service.AddendumService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="od/addendum")
@Api(description = "Controladora de servicio de adenda de OD", tags = "grant-rights-addendum-controller")
public class AddendumController extends BaseController {
    @Autowired
    AddendumService _service;

//    @GetMapping
//    public ResponseEntity<Addendum> list() throws Exception {
//        ResponseEntity<Addendum> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una documento od", notes = "")
    public ResponseEntity<Addendum> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la documento od", notes = "")
    public ResponseEntity<Addendum> save(@RequestParam("item") String item, @RequestParam("file") MultipartFile file) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<>();
        Addendum item2 = super.fromJson(item, Addendum.class);
        response = this._service.save(item2, file);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la documento od", notes = "")
    public ResponseEntity<Addendum> update(@RequestParam("item") String item, @RequestParam("file") MultipartFile file) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<>();
        Addendum item2 = super.fromJson(item, Addendum.class);
        response = this._service.update(item2, file);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una documento od", notes = "")
    public ResponseEntity<Addendum> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-od/{id}")
    @ApiOperation(value = "Método que devuelve listado de documento od por id de od", notes = "")
    public ResponseEntity<Addendum> findByOdId(@PathVariable("id") int odId) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<>();
        response = this._service.findByOdId(odId);
        return response;
    }

    @RequestMapping(value = "/download-file/{fileId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que el devuelve documento documento de adenda", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity getFile(@PathVariable("fileId") String fileId) throws Exception{
        byte[] bytes = this._service.getFile(false, fileId);
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
