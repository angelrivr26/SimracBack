package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Compromise;
import pe.sernanp.ws_api.service.CompromiseService;

@RestController
@RequestMapping(value="od/compromise")
@Api(description = "Controladora de servicio de compromisos de OD", tags = "grant-rights-compromise-controller")
public class CompromiseController {
    @Autowired
    CompromiseService _service;

//    @GetMapping
//    public ResponseEntity<Compromise> list() throws Exception {
//        ResponseEntity<Compromise> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un compromiso", notes = "")
    public ResponseEntity<Compromise> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping("/monetary")
    @ApiOperation(value = "Método que permite registrar un compromiso monetario", notes = "")
    public ResponseEntity<Compromise> saveMonetary(@RequestBody Compromise item) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

//    @PostMapping("/no-monetary")
//    @ApiOperation(value = "Método que permite registrar un compromiso no monetario", notes = "")
//    public ResponseEntity<Compromise> saveNoMonetary(@RequestBody Compromise item) throws Exception {
//        ResponseEntity<Compromise> response = new ResponseEntity<>();
//        response = this._service.save(item);
//        return response;
//    }

    @PutMapping("/monetary")
    @ApiOperation(value = "Método que permite actualizar un compromiso monetario", notes = "")
    public ResponseEntity<Compromise> updateMonetary(@RequestBody Compromise item) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

//    @PutMapping("/no-monetary")
//    @ApiOperation(value = "Método que permite actualizar un compromiso no monetario", notes = "")
//    public ResponseEntity<Compromise> updateNoMonetary(@RequestBody Compromise item) throws Exception {
//        ResponseEntity<Compromise> response = new ResponseEntity<>();
//        response = this._service.update(item);
//        return response;
//    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una compromiso od", notes = "")
    public ResponseEntity<Compromise> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-monetary-by-od/{id}")
    @ApiOperation(value = "Método que devuelve listado de compromisos od monetarios por id de od", notes = "")
    public ResponseEntity<Compromise> findMonetaryByOdId(@PathVariable("id") int odId) throws Exception {
        ResponseEntity<Compromise> response = new ResponseEntity<>();
        response = this._service.findMonetaryByOdId(odId);
        return response;
    }

//    @GetMapping("/list-no-monetary-by-od/{id}")
//    @ApiOperation(value = "Método que devuelve listado de compromisos od no monetarios por id de od", notes = "")
//    public ResponseEntity<Compromise> findNoMonetaryByOdId(@PathVariable("id") int odId) throws Exception {
//        ResponseEntity<Compromise> response = new ResponseEntity<>();
//        response = this._service.findNoMonetaryByOdId(odId);
//        return response;
//    }
}
