package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MPResource;
import pe.sernanp.ws_api.service.MPResourceService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/management-plan/resource")
@Api(description = "Controladora de servicio para recurso de plan de manejo", tags = "management-plan-resource-controller")
public class MPResourceController {
    @Autowired
    MPResourceService _service;

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un recurso", notes = "")
    public ResponseEntity<MPResource> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar el recurso", notes = "")
    public ResponseEntity<MPResource> save(@RequestBody MPResource item) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar el recurso", notes = "")
    public ResponseEntity<MPResource> update(@RequestBody MPResource item) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un recurso", notes = "")
    public ResponseEntity<MPResource> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-pm/{managementPlanId}")
    @ApiOperation(value = "Método que devuelve listado de recursos por id de plan de manejo", notes = "")
    public ResponseEntity<MPResource> findByManagementPlanId(@PathVariable("managementPlanId") int managementPlanId) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<>();
        response = this._service.findByManagementPlanId(managementPlanId);
        return response;
    }

    @RequestMapping(value = "/export/{managementPlanId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de recursos para plan de manejo", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("managementPlanId") int managementPlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(managementPlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "PM_Recursos" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
