package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.SupervisionReportDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SupervisionReport;
import pe.sernanp.ws_api.service.SupervisionReportService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/supervision-report")
@Api(description = "Controladora de servicio de informe de supervisión")
public class SupervisionReportController extends BaseController {
    @Autowired
    SupervisionReportService _service;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve listado de informes de supervisión con filtros", notes = "")
    public ResponseEntity<SupervisionReportDTO> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<SupervisionReportDTO> response = new ResponseEntity<SupervisionReportDTO>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            SupervisionReport item2 = super.fromJson(item, SupervisionReport.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @GetMapping
    @ApiOperation(value = "Método que devuelve el listado de informes de supervisión", notes = "")
    public ResponseEntity<SupervisionReport> list() throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<>();
        response = this._service.findAll();
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un informe de supervisión", notes = "")
    public ResponseEntity<SupervisionReport> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @GetMapping("/calculate/{id}")
    @ApiOperation(value = "Método que calcula la evaluación de un informe de supervisión", notes = "")
    public ResponseEntity<SupervisionReport> calculateEvaluation(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<>();
        response = this._service.calculateEvaluation(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método para guardar un informe de supervisión", notes = "")
    public ResponseEntity<SupervisionReport> save(@RequestParam("item") String item, @RequestParam("documentReport") MultipartFile documentReport) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<>();
        SupervisionReport item2 = super.fromJson(item, SupervisionReport.class);
        response = this._service.save(item2, documentReport);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método para actualizar un informe de supervisión", notes = "")
    public ResponseEntity<SupervisionReport> update(@RequestParam("item") String item, @RequestParam("documentReport") MultipartFile documentReport) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<>();
        SupervisionReport item2 = super.fromJson(item, SupervisionReport.class);
        response = this._service.update(item2, documentReport);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método para eliminar un informe de supervisión", notes = "")
    public ResponseEntity<SupervisionReport> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve reporte en excel de informe de supervision", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@RequestBody SupervisionReport item) throws Exception{
        ByteArrayInputStream stream = this._service.export(item);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Inf_Sup_Obligaciones" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

    @RequestMapping(value = "/download-file/{fileId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que el devuelve documento de inform de supervisión", notes = "")
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
