package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.MonitoringReportDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MonitoringRecord;
import pe.sernanp.ws_api.model.MonitoringReport;
import pe.sernanp.ws_api.model.SupervisionReport;
import pe.sernanp.ws_api.service.MonitoringReportService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/monitoring-report")
@Api(description = "Controladora de servicio de informe de seguimiento")
public class MonitoringReportController extends BaseController {
    @Autowired
    MonitoringReportService _service;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve listado de informes de seguimiento con filtros", notes = "")
    public ResponseEntity<MonitoringReportDTO> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<MonitoringReportDTO> response = new ResponseEntity<MonitoringReportDTO>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            MonitoringReport item2 = super.fromJson(item, MonitoringReport.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @GetMapping("/list-monitoring-record-by-id/{id}")
    @ApiOperation(value = "Método que devuelve el listado de informe de seguimiento", notes = "")
    public ResponseEntity<MonitoringRecord> list(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        response = this._service.listMonitoringRecordById(id);
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un informe de seguimiento", notes = "")
    public ResponseEntity<MonitoringReport> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método para guardar un informe de seguimiento", notes = "")
    public ResponseEntity<MonitoringReport> save(@RequestParam("item") String item, @RequestParam("documentReport") MultipartFile documentReport) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<>();
        MonitoringReport item2 = super.fromJson(item, MonitoringReport.class);
        response = this._service.save(item2, documentReport);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método para actualizar un informe de seguimiento", notes = "")
    public ResponseEntity<MonitoringReport> update(@RequestParam("item") String item, @RequestParam("documentReport") MultipartFile documentReport) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<>();
        MonitoringReport item2 = super.fromJson(item, MonitoringReport.class);
        response = this._service.update(item2, documentReport);
        return response;
    }

    @PostMapping("/update-conclusion")
    @ApiOperation(value = "Método para actualizar un informe de seguimiento", notes = "")
    public ResponseEntity<MonitoringReport> updateConclusion(@RequestBody MonitoringReport item) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<>();
        response = this._service.updateConclusion(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método para eliminar un informe de seguimiento", notes = "")
    public ResponseEntity<MonitoringReport> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve reporte en excel de informe de seguimiento", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@RequestBody MonitoringReport item) throws Exception{
        ByteArrayInputStream stream = this._service.export(item);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Inf_Segui_Obligaciones" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

    @RequestMapping(value = "/download-file/{fileId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que el devuelve documento de inform de seguimiento", notes = "")
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
