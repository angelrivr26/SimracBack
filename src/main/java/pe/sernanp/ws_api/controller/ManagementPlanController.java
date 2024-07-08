package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ManagementPlanDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ManagementPlan;
import pe.sernanp.ws_api.service.ManagementPlanService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/management-plan")
@Api(description = "Controladora de servicio para plan de manejo", tags = "management-plan-Controller")
public class ManagementPlanController extends BaseController {
    @Autowired
    ManagementPlanService _service;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve listado de plan de manejo con filtros", notes = "")
    public ResponseEntity<ManagementPlanDTO> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<ManagementPlanDTO> response = new ResponseEntity<ManagementPlanDTO>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            ManagementPlan item2 = super.fromJson(item, ManagementPlan.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @GetMapping
    @ApiOperation(value = "Método que devuelve el listado de plan de manejo", notes = "")
    public ResponseEntity<ManagementPlan> list() throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<>();
        response = this._service.findAll();
        return response;
    }

    @GetMapping("/list-by-anpcode/{anpCodes}")
    @ApiOperation(value = "Método que devuelve el listado de plan de manejo", notes = "")
    public ResponseEntity<ManagementPlan> listByAnpCode(@PathVariable("anpCodes") String anpCodes) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<>();
        response = this._service.findByAnpCode(anpCodes);
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un plan de manejo", notes = "")
    public ResponseEntity<ManagementPlan> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping("/save-draft")
    @ApiOperation(value = "Método que permite registrar el plan de manejo", notes = "")
    public ResponseEntity<ManagementPlan> saveDraft(@RequestParam("item") String item, @RequestParam("resolutionFile") MultipartFile resolutionFile,
                                                    @RequestParam("managementPlanFile") MultipartFile managementPlanFile, @RequestParam("compatibilityReportFile") MultipartFile compatibilityReportFile) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<>();
        ManagementPlan item2 = super.fromJson(item, ManagementPlan.class);
        item2.setFlagDraft(true);
        response = this._service.save(item2, resolutionFile, managementPlanFile, compatibilityReportFile);
        return response;
    }

    @PutMapping("/update-draft")
    @ApiOperation(value = "Método que permite actualizar el plan de manejo", notes = "")
    public ResponseEntity<ManagementPlan> updateDarft(@RequestParam("item") String item, @RequestParam("resolutionFile") MultipartFile resolutionFile,
                                                      @RequestParam("managementPlanFile") MultipartFile managementPlanFile, @RequestParam("compatibilityReportFile") MultipartFile compatibilityReportFile) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<>();
        ManagementPlan item2 = super.fromJson(item, ManagementPlan.class);
        item2.setFlagDraft(true);
        response = this._service.update(item2, resolutionFile, managementPlanFile, compatibilityReportFile);
        return response;
    }

    @PutMapping("/update-finalize")
    @ApiOperation(value = "Método que permite actualizar el plan de manejo", notes = "")
    public ResponseEntity<ManagementPlan> updateFinalize(@RequestParam("item") String item, @RequestParam("resolutionFile") MultipartFile resolutionFile,
                                                         @RequestParam("managementPlanFile") MultipartFile managementPlanFile, @RequestParam("compatibilityReportFile") MultipartFile compatibilityReportFile) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<>();
        ManagementPlan item2 = super.fromJson(item, ManagementPlan.class);
        item2.setFlagDraft(false);
        response = this._service.update(item2, resolutionFile, managementPlanFile, compatibilityReportFile);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un plan de manejo", notes = "")
    public ResponseEntity<ManagementPlan> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve reporte en excel de plan de manejo", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@RequestBody ManagementPlan item) throws Exception{
        ByteArrayInputStream stream = this._service.export(item);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Planes_Manejo" + dateActual.toString();
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
