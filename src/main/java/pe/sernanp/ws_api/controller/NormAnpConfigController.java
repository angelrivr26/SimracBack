package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.NormAnpConfig;
import pe.sernanp.ws_api.service.NormAnpConfigService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/anpconfig/norm")
@Api(description = "Controladora de servicio de Norma y Configuración", tags = "anp-config-norm-controller")
public class NormAnpConfigController {
    @Autowired
    NormAnpConfigService _service;

//    @GetMapping
//    public ResponseEntity<NormAnpConfig> list() throws Exception {
//        ResponseEntity<NormAnpConfig> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una Norma por Configuración", notes = "")
    public ResponseEntity<NormAnpConfig> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la Norma por Configuración", notes = "")
    public ResponseEntity<NormAnpConfig> save(@RequestBody NormAnpConfig item) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la Norma por Configuración", notes = "")
    public ResponseEntity<NormAnpConfig> update(@RequestBody NormAnpConfig item) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una Norma por Configuración", notes = "")
    public ResponseEntity<NormAnpConfig> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-configanp/{id}")
    @ApiOperation(value = "Método que devuelve listado de Normas por Configuración", notes = "")
    public ResponseEntity<NormAnpConfig> findByAnpConfig(@PathVariable("id") String ids) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<>();
        response = this._service.findByAnpConfig(ids);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de Normas por Configuración", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int anpConfigId) throws Exception{
        ByteArrayInputStream stream = this._service.export(anpConfigId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Norma_anp_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
