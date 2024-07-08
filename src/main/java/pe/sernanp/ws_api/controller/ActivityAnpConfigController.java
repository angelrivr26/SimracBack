package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ActivityAnpConfig;
import pe.sernanp.ws_api.service.ActivityAnpConfigService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/anpconfig/activity")
@Api(description = "Controladora de servicio de Actividad y Configuración", tags = "anp-config-activity-controller")
public class ActivityAnpConfigController {
    @Autowired
    ActivityAnpConfigService _service;

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una Actividad por Configuración", notes = "")
    public ResponseEntity<ActivityAnpConfig> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la Actividad por Configuración", notes = "")
    public ResponseEntity<ActivityAnpConfig> save(@RequestBody ActivityAnpConfig item) throws Exception {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la Actividad por Configuración", notes = "")
    public ResponseEntity<ActivityAnpConfig> update(@RequestBody ActivityAnpConfig item) throws Exception {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una Actividad por Configuración", notes = "")
    public ResponseEntity<ActivityAnpConfig> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-configanp/{id}")
    @ApiOperation(value = "Método que devuelve listado de Actividades por Configuración", notes = "")
    public ResponseEntity<ActivityAnpConfig> findByAnpConfig(@PathVariable("id") String ids) throws Exception {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<>();
        response = this._service.findByAnpConfig(ids);
        return response;
    }

    @PostMapping("/list-by-type-and-anpconfig")
    @ApiOperation(value = "Método que devuelve listado de Actividades por tipo y anp", notes = "")
    public ResponseEntity<ListDTO> listActivityByTypeAndAnpCode(@RequestBody ActivityAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        response = this._service.listActivityByTypeAndAnpConfig(item);
        return response;
    }

    @PostMapping("/list-type-by-anpconfig")
    @ApiOperation(value = "Método que devuelve listado de Actividades por tipo y anp", notes = "")
    public ResponseEntity<ListDTO> listActivityTypeByAnpConfig(@RequestBody ActivityAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        response = this._service.listActivityTypeByAnpConfig(item);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de Actividades por Configuración", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int anpConfigId) throws Exception{
        ByteArrayInputStream stream = this._service.export(anpConfigId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Actividad_anp_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
