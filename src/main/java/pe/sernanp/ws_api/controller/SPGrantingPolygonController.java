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
import pe.sernanp.ws_api.model.AnpConfig;
import pe.sernanp.ws_api.model.Modality;
import pe.sernanp.ws_api.model.SPGrantingPolygon;
import pe.sernanp.ws_api.model.SPPermittedActivity;
import pe.sernanp.ws_api.service.SPGrantingPolygonService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/site-plan/granting-polygon")
@Api(description = "Controladora de servicio para poligono de otrogamiento de plan de sitio", tags = "site-plan-granting -polygon-controller")
public class SPGrantingPolygonController  {
    @Autowired
    SPGrantingPolygonService _service;

//    @GetMapping
//    public ResponseEntity<SPGrantingPolygon> list() throws Exception {
//        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una poligono de otrogamiento", notes = "")
    public ResponseEntity<SPGrantingPolygon> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la poligono de otrogamiento", notes = "")
    public ResponseEntity<SPGrantingPolygon> save(@RequestBody SPGrantingPolygon item) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la poligono de otrogamiento", notes = "")
    public ResponseEntity<SPGrantingPolygon> update(@RequestBody SPGrantingPolygon item) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una poligono de otrogamiento", notes = "")
    public ResponseEntity<SPGrantingPolygon> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-ps/{sitePlanId}")
    @ApiOperation(value = "Método que devuelve listado de poligonos de otrogamiento por id de plan de manejo", notes = "")
    public ResponseEntity<SPGrantingPolygon> findByManagementPlanId(@PathVariable("sitePlanId") int sitePlanId) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<>();
        response = this._service.findBySitePlanId(sitePlanId);
        return response;
    }

//    @GetMapping("/list-modality/{ids}")
//    @ApiOperation(value = "Método que devuelve listado de poligonos de otrogamiento por id de plan de manejo", notes = "")
//    public ResponseEntity<ModalityDTO> listModalityById(@PathVariable("ids") String ids) throws Exception {
//        ResponseEntity<ModalityDTO> response = new ResponseEntity<>();
//        response = this._service.listModalityById(ids);
//        return response;
//    }

    @PostMapping("/list-by-modality/")
    @ApiOperation(value = "Método que devuelve listado de poligonos de otrogamiento por id de plan de manejo", notes = "")
    public ResponseEntity<ListDetailDTO> listByModality(@RequestBody SPPermittedActivity itemActivity) throws Exception {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<>();
        response = this._service.listByModality(itemActivity);
        return response;
    }

    @GetMapping("/list-by-ts/{touristSectorId}")
    @ApiOperation(value = "Método que devuelve listado de poligonos de otrogamiento por id de plan de manejo", notes = "")
    public ResponseEntity<SPGrantingPolygon> findBySouristSectorId(@PathVariable("touristSectorId") int touristSectorId) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<>();
        response = this._service.findBySouristSectorId(touristSectorId);
        return response;
    }

    @PostMapping("/list-by-config")
    @ApiOperation(value = "Método que devuelve listado de poligonos de otrogamiento por id de plan de manejo", notes = "")
    public ResponseEntity<SPGrantingPolygon> findByConfig(@RequestBody SPGrantingPolygon item) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<>();
        response = this._service.findByConfig(item);
        return response;
    }

    @PostMapping("/list-modality-by-config")
    @ApiOperation(value = "Método que devuelve listado de poligonos de otrogamiento por id de plan de manejo", notes = "")
    public ResponseEntity<ModalityDTO> listModalityByConfig(@RequestBody SPGrantingPolygon item) throws Exception {
        ResponseEntity<ModalityDTO> response = new ResponseEntity<>();
        response = this._service.listModalityByConfig(item);
        return response;
    }

    @RequestMapping(value = "/export/{sitePlanId}", method = RequestMethod.GET)
    @ApiOperation(value = "Método que devuelve reporte en excel de poligonos de otrogamiento para plan de manejo", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@PathVariable("sitePlanId") int sitePlanId) throws Exception{
        ByteArrayInputStream stream = this._service.export(sitePlanId);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "PS_Poligono_Otorgamiento_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
