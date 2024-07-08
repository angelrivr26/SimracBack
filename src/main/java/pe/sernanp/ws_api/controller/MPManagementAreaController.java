package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MPManagementArea;
import pe.sernanp.ws_api.service.MPManagementAreaService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/management-plan/management-area")
@Api(description = "Controladora de servicio para area de manejo de plan de manejo", tags = "management-plan-management-area-controller")
public class MPManagementAreaController  {
    @Autowired
    MPManagementAreaService _service;

//    @GetMapping
//    public ResponseEntity<MPManagementArea> list() throws Exception {
//        ResponseEntity<MPManagementArea> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una area de manejo", notes = "")
    public ResponseEntity<MPManagementArea> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la area de manejo", notes = "")
    public ResponseEntity<MPManagementArea> save(@RequestBody MPManagementArea item) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la area de manejo", notes = "")
    public ResponseEntity<MPManagementArea> update(@RequestBody MPManagementArea item) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una area de manejo", notes = "")
    public ResponseEntity<MPManagementArea> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-pm/{managementPlanId}")
    @ApiOperation(value = "Método que devuelve listado de areas de manejo por id de plan de manejo", notes = "")
    public ResponseEntity<MPManagementArea> findByManagementPlanId(@PathVariable("managementPlanId") int managementPlanId) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<>();
        response = this._service.findByManagementPlanId(managementPlanId);
        return response;
    }

    @RequestMapping(value = "/export/{managementPlanId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de areas de manejo para plan de manejo", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("managementPlanId") int managementPlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(managementPlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "PM_Areas_Manejo" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
