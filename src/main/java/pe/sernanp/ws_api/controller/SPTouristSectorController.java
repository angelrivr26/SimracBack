package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SPTouristSector;
import pe.sernanp.ws_api.service.SPTouristSectorService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/site-plan/tourist-sector")
@Api(description = "Controladora de servicio para sector turistico de plan de sitio", tags = "site-plan-tourist-sector-controller")
public class SPTouristSectorController  {
    @Autowired
    SPTouristSectorService _service;

//    @GetMapping
//    public ResponseEntity<SPTouristSector> list() throws Exception {
//        ResponseEntity<SPTouristSector> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una sector turistico", notes = "")
    public ResponseEntity<SPTouristSector> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la sector turistico", notes = "")
    public ResponseEntity<SPTouristSector> save(@RequestBody SPTouristSector item) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la sector turistico", notes = "")
    public ResponseEntity<SPTouristSector> update(@RequestBody SPTouristSector item) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una sector turistico", notes = "")
    public ResponseEntity<SPTouristSector> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-ps/{sitePlanId}")
    @ApiOperation(value = "Método que devuelve listado de sectores turisticos por id de plan de sitio", notes = "")
    public ResponseEntity<SPTouristSector> findByManagementPlanId(@PathVariable("sitePlanId") int sitePlanId) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<>();
        response = this._service.findBySitePlanId(sitePlanId);
        return response;
    }

//    @PostMapping("/list-modality-by-sector")
//    @ApiOperation(value = "Método que devuelve listado de sectores turisticos por id de plan de sitio", notes = "")
//    public ResponseEntity<ModalityDTO> findModalityBySectorCode(@RequestBody SPTouristSector item) throws Exception {
//        ResponseEntity<ModalityDTO> response = new ResponseEntity<>();
//        response = this._service.findModalityBySectorCode(item);
//        return response;
//    }

    @RequestMapping(value = "/export/{sitePlanId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de sectores turisticos para plan de sitio", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("sitePlanId") int sitePlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(sitePlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "PS_Sector_Turistico" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
