package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ActivityAnpConfig;
import pe.sernanp.ws_api.model.ResourceAnpConfig;
import pe.sernanp.ws_api.service.ResourceAnpConfigService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/anpconfig/resource")
@Api(description = "Controladora de servicio de Recurso y Configuración", tags = "anp-config-resource-controller")
public class ResourceAnpConfigController extends BaseController {
    @Autowired
    ResourceAnpConfigService _service;

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un Recurso por Configuración", notes = "")
    public ResponseEntity<ResourceAnpConfig> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar el Recurso por Configuración", notes = "")
    public ResponseEntity<ResourceAnpConfig> save(@RequestBody ResourceAnpConfig item) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar el Recurso por Configuración", notes = "")
    public ResponseEntity<ResourceAnpConfig> update(@RequestBody ResourceAnpConfig item) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un Recurso por Configuración", notes = "")
    public ResponseEntity<ResourceAnpConfig> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-configanp/{id}")
    @ApiOperation(value = "Método que devuelve listado de Recursos por Configuración", notes = "")
    public ResponseEntity<ResourceAnpConfig> findByAnpConfig(@PathVariable("id") String ids) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<>();
        response = this._service.findByAnpConfig(ids);
        return response;
    }

    @GetMapping("/list-by-configanpcode/{code}")
    @ApiOperation(value = "Método que devuelve listado de Recursos por Configuración", notes = "")
    public ResponseEntity<ResourceAnpConfig> findByAnpConfigCode(@PathVariable("code") String code) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<>();
        response = this._service.findByAnpConfigCode(code);
        return response;
    }

    @PostMapping("/list-by-anpconfig")
    @ApiOperation(value = "Método que devuelve listado de Actividades por tipo y anp", notes = "")
    public ResponseEntity<ListDTO> findByAnpConfig(@RequestBody ResourceAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        response = this._service.listByAnpConfig(item);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de Recursos por Configuración", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int anpConfigId) throws Exception{
        ByteArrayInputStream stream = this._service.export(anpConfigId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Recurso_anp_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
