package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ModalityAnpConfig;
import pe.sernanp.ws_api.service.ModalityAnpConfigService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/anpconfig/modality")
@Api(description = "Controladora de servicio de Modalidad y Configuración", tags = "anp-config-modality-of-use-controller")
public class ModalityAnpConfigController {
    @Autowired
    ModalityAnpConfigService _service;

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una Modalidad por Configuración", notes = "")
    public ResponseEntity<ModalityAnpConfig> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la Modalidad por Configuración", notes = "")
    public ResponseEntity<ModalityAnpConfig> save(@RequestBody ModalityAnpConfig item) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la Modalidad por Configuración", notes = "")
    public ResponseEntity<ModalityAnpConfig> update(@RequestBody ModalityAnpConfig item) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una Modalidad por Configuración", notes = "")
    public ResponseEntity<ModalityAnpConfig> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-configanp/{id}")
    @ApiOperation(value = "Método que devuelve listado de Modalidades por Configuración", notes = "")
    public ResponseEntity<ModalityAnpConfig> findByAnpConfig(@PathVariable("id") String ids) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<>();
        response = this._service.findByAnpConfig(ids);
        return response;
    }

    @PostMapping("/list-sectors")
    @ApiOperation(value = "Método que devuelve listado de sectores registrados en modalidad por Configuración", notes = "")
    public ResponseEntity<ListDTO> listSectorByTypeModalityAndAnpCode(@RequestBody ModalityAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        response = this._service.listSectorByTypeModalityAndAnpCode(item);
        return response;
    }

    @PostMapping("/list-polygons")
    @ApiOperation(value = "Método que devuelve listado de poligonos registrados en modalidad por Configuración", notes = "")
    public ResponseEntity<ListDTO> listPolygonByTypeModalityAndAnpSector(@RequestBody ModalityAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        response = this._service.listPolygonByTypeModalityAndAnpSector(item);
        return response;
    }

    @RequestMapping(value = "/export/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de Modalidades por Configuración", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("id") int anpConfigId) throws Exception{
        ByteArrayInputStream stream = this._service.export(anpConfigId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Modalidad_anp_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
