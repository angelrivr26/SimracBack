package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MPPermittedActivity;
import pe.sernanp.ws_api.model.ManagementPlan;
import pe.sernanp.ws_api.service.MPPermittedActivityService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/management-plan/permitted-activity")
@Api(description = "Controladora de servicio para actividades permitidas de plan de manejo", tags = "management-plan-permitted-activity-controller")
public class MPPermittedActivityController  {
    @Autowired
    MPPermittedActivityService _service;

//    @GetMapping
//    public ResponseEntity<MPPermittedActivity> list() throws Exception {
//        ResponseEntity<MPPermittedActivity> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una actividad permitida", notes = "")
    public ResponseEntity<MPPermittedActivity> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la actividad permitida", notes = "")
    public ResponseEntity<MPPermittedActivity> save(@RequestBody MPPermittedActivity item) throws Exception {
        ResponseEntity<MPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la actividad permitida", notes = "")
    public ResponseEntity<MPPermittedActivity> update(@RequestBody MPPermittedActivity item) throws Exception {
        ResponseEntity<MPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una actividad permitida", notes = "")
    public ResponseEntity<MPPermittedActivity> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-pm/{id}")
    @ApiOperation(value = "Método que devuelve listado de actividades permitidas por id de plan de manejo", notes = "")
    public ResponseEntity<MPPermittedActivity> findByManagementPlanId(@PathVariable("id") int managementPlanId) throws Exception {
        ResponseEntity<MPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.findByManagementPlanId(managementPlanId);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de actividades permitidas para plan de manejo", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int managementPlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(managementPlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "PM_Actividades_Permitidas" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
