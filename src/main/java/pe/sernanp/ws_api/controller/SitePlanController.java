package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.SitePlanDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SitePlan;
import pe.sernanp.ws_api.service.SitePlanService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/site-plan")
@Api(description = "Controladora de servicio para plan de sitio", tags = "site-plan-Controller")
public class SitePlanController extends BaseController {
    @Autowired
    SitePlanService _service;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve listado de plan de sitio con filtros", notes = "")
    public ResponseEntity<SitePlanDTO> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<SitePlanDTO> response = new ResponseEntity<SitePlanDTO>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            SitePlan item2 = super.fromJson(item, SitePlan.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @GetMapping
    @ApiOperation(value = "Método que devuelve el listado de plan de sitio", notes = "")
    public ResponseEntity<SitePlan> list() throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<>();
        response = this._service.findAll();
        return response;
    }

    @GetMapping("/list-by-anpcode/{anpCodes}")
    @ApiOperation(value = "Método que devuelve el listado de plan de sitio", notes = "")
    public ResponseEntity<SitePlan> listByAnpCode(@PathVariable("anpCodes") String anpCodes) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<>();
        response = this._service.findByAnpCode(anpCodes);
        return response;
    }

    @GetMapping("/list-by-polygoncode/{polygonCode}")
    @ApiOperation(value = "Método que devuelve el listado de plan de sitio", notes = "")
    public ResponseEntity<SitePlan> findByPolygonCode(@PathVariable("polygonCode") String polygonCode) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<>();
        response = this._service.findByPolygonCode(polygonCode);
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un plan de sitio", notes = "")
    public ResponseEntity<SitePlan> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar el plan de sitio", notes = "")
    public ResponseEntity<SitePlan> save(@RequestParam("item") String item, @RequestParam("resolutionFile") MultipartFile resolutionFile, @RequestParam("instrumentFile") MultipartFile instrumentFile) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<>();
        SitePlan item2 = super.fromJson(item, SitePlan.class);
        response = this._service.save(item2, resolutionFile, instrumentFile);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar el plan de sitio", notes = "")
    public ResponseEntity<SitePlan> update(@RequestParam("item") String item, @RequestParam("resolutionFile") MultipartFile resolutionFile, @RequestParam("instrumentFile") MultipartFile instrumentFile) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<>();
        SitePlan item2 = super.fromJson(item, SitePlan.class);
        response = this._service.update(item2, resolutionFile, instrumentFile);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un plan de sitio", notes = "")
    public ResponseEntity<SitePlan> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve reporte en excel de plan de sitio", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@RequestBody SitePlan item) throws Exception{
        ByteArrayInputStream stream = this._service.export(item);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Plan_sitio_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

    @RequestMapping(value = "/download-file/{fileId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que el devuelve documento requisito de modalidad", notes = "")
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
