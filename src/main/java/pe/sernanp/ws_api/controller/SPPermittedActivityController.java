package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SPPermittedActivity;
import pe.sernanp.ws_api.service.SPPermittedActivityService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/site-plan/permitted-activity")
@Api(description = "Controladora de servicio para actividad permitida de plan de sitio", tags = "site-plan-permitted-activity-controller")
public class SPPermittedActivityController  {
    @Autowired
    SPPermittedActivityService _service;

//    @GetMapping
//    public ResponseEntity<SPPermittedActivity> list() throws Exception {
//        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una actividad permitida", notes = "")
    public ResponseEntity<SPPermittedActivity> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la actividad permitida", notes = "")
    public ResponseEntity<SPPermittedActivity> save(@RequestBody SPPermittedActivity item) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la actividad permitida", notes = "")
    public ResponseEntity<SPPermittedActivity> update(@RequestBody SPPermittedActivity item) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una actividad permitida", notes = "")
    public ResponseEntity<SPPermittedActivity> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-ps/{sitePlanId}")
    @ApiOperation(value = "Método que devuelve listado de actividades permitidas por id de plan de sitio", notes = "")
    public ResponseEntity<SPPermittedActivity> findByManagementPlanId(@PathVariable("sitePlanId") int sitePlanId) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<>();
        response = this._service.findBySitePlanId(sitePlanId);
        return response;
    }

    @RequestMapping(value = "/export/{sitePlanId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de actividades permitidas para plan de sitio", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("sitePlanId") int sitePlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(sitePlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "PS_Actividad_Permitida_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
