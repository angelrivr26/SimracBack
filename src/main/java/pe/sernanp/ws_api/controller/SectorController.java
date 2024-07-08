package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Sector;
import pe.sernanp.ws_api.service.SectorService;

@RestController
@RequestMapping(value="/sector")
@Api(description = "Controladora de servicio de Sector", tags = "sector-controller")
public class SectorController extends BaseController {
    @Autowired
    SectorService _service;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve un listado de Sector", notes = "")
    public ResponseEntity<Sector> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            Sector item2 = super.fromJson(item, Sector.class);
            item2.setSector(null);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @RequestMapping(value = "/area/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve un listado de Sector", notes = "")
    public ResponseEntity<Sector> areaSearch(@RequestParam("item") String item) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            Sector item2 = super.fromJson(item, Sector.class);
            if (item2.getSector() == null || item2.getSector().getId() == 0) {
                response.setMessage("El id del sector no puede ser 0.");
                response.setSuccess(false);
            } else
                response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un Sector", notes = "")
    public ResponseEntity<Sector> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

//    @GetMapping()
//    @ApiOperation(value = "Método que devuelve un listado de Sector por id del sector padre", notes = "")
//    public ResponseEntity<Sector> findAll() throws Exception {
//        ResponseEntity<Sector> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/list-by-sector/{sectorId}")
    @ApiOperation(value = "Método que devuelve un listado de Sector por id del sector padre", notes = "")
    public ResponseEntity<Sector> findBySectorId(@PathVariable("sectorId") int sectorId) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        response = this._service.findBySectorId(sectorId);
        return response;
    }

    @GetMapping("/list-by-areacode/{areaCode}")
    @ApiOperation(value = "Método que devuelve un listado de Sectores por codigo", notes = "")
    public ResponseEntity<Sector> findByAreaCode(@PathVariable("areaCode") String areaCode) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        response = this._service.findByAreaCode(areaCode);
        return response;
    }

    @GetMapping("/list-by-polygon/{polygonCode}")
    @ApiOperation(value = "Método que devuelve un listado de Sectores por codigo del poligono", notes = "")
    public ResponseEntity<Sector> findAreaByPolygonCode(@PathVariable("polygonCode") String polygonCode) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        response = this._service.findAreaByPolygonCode(polygonCode);
        return response;
    }

    @GetMapping("/list-by-anp/{anpCode}")
    @ApiOperation(value = "Método que devuelve listado de sectores por anp", notes = "")
    public ResponseEntity<Sector> findByAnpCode(@PathVariable("anpCode") String anpCode) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        response = this._service.findByAnpCode(anpCode);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar el Sector", notes = "")
    public ResponseEntity<Sector> save(@RequestBody Sector item) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        item.setSector(null);
        response = this._service.save(item);
        return response;
    }

    @PostMapping("/area")
    @ApiOperation(value = "Método que permite registrar el Sector", notes = "")
    public ResponseEntity<Sector> areaSave(@RequestBody Sector item) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        if (item.getSector() == null || item.getSector().getId() == 0) {
            response.setMessage("El id del sector no puede ser 0.");
            response.setSuccess(false);
        } else
            response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar el Sector", notes = "")
    public ResponseEntity<Sector> update(@RequestBody Sector item) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        item.setSector(null);
        response = this._service.update(item);
        return response;
    }

    @PutMapping("/area")
    @ApiOperation(value = "Método que permite actualizar el Sector", notes = "")
    public ResponseEntity<Sector> areaUpdate(@RequestBody Sector item) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        if (item.getSector() == null || item.getSector().getId() == 0) {
            response.setMessage("El id del sector no puede ser 0.");
            response.setSuccess(false);
        } else
            response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar un Sector", notes = "")
    public ResponseEntity<Sector> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }
}
