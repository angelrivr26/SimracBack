package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Polygon;
import pe.sernanp.ws_api.service.PolygonService;

@RestController
@RequestMapping(value="/polygon")
@Api(description = "Controladora de servicio de Poligono", tags = "polygon-controller")
public class PolygonController extends BaseController {
    @Autowired
    PolygonService _service;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve un listado de poligonos", notes = "")
    public ResponseEntity<Polygon> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<Polygon>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            Polygon item2 = super.fromJson(item, Polygon.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un poligono", notes = "")
    public ResponseEntity<Polygon> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }
    
    @GetMapping("/list-by-sector/{sectorId}")
    @ApiOperation(value = "Método que devuelve un listado de poligonos por id del sector", notes = "")
    public ResponseEntity<Polygon> findBySectorId(@PathVariable("sectorId") int sectorId) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<>();
        response = this._service.findBySectorId(sectorId);
        return response;
    }

    @GetMapping("/list-by-anp/{anpCode}")
    @ApiOperation(value = "Método que devuelve un listado de poligonos por id del sector", notes = "")
    public ResponseEntity<Polygon> findByAnpCode(@PathVariable("anpCode") String anpCodes) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<>();
        response = this._service.findByAnpCode(anpCodes);
        return response;
    }
    
    @PostMapping()
    @ApiOperation(value = "Método que permite registrar el poligono", notes = "")
    public ResponseEntity<Polygon> save(@RequestBody Polygon item) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar el poligono", notes = "")
    public ResponseEntity<Polygon> update(@RequestBody Polygon item) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un poligono", notes = "")
    public ResponseEntity<Polygon> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }
}
