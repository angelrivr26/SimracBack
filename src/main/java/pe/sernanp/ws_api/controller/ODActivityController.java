package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ODActivity;
import pe.sernanp.ws_api.service.ODActivityService;

@RestController
@RequestMapping(value="/od/activity")
@Api(description = "Controladora de servicio de actividad od", tags = "grant-rights-activity-controller")
public class ODActivityController  {
    @Autowired
    ODActivityService _service;

//    @GetMapping
//    public ResponseEntity<ODActivity> list() throws Exception {
//        ResponseEntity<ODActivity> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una actividad od", notes = "")
    public ResponseEntity<ODActivity> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la actividad od", notes = "")
    public ResponseEntity<ODActivity> save(@RequestBody ODActivity item) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la actividad od", notes = "")
    public ResponseEntity<ODActivity> update(@RequestBody ODActivity item) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una actividad od", notes = "")
    public ResponseEntity<ODActivity> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-od/{id}")
    @ApiOperation(value = "Método que devuelve listado de actividad od por id de od", notes = "")
    public ResponseEntity<ODActivity> findByOdId(@PathVariable("id") int odId) throws Exception {
        ResponseEntity<ODActivity> response = new ResponseEntity<>();
        response = this._service.findByOdId(odId);
        return response;
    }
}
