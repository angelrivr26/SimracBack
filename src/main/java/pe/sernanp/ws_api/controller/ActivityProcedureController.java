package pe.sernanp.ws_api.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ActivityProcedure;
import pe.sernanp.ws_api.service.ActivityProcedureService;

@RestController
@RequestMapping("/activityprocedure")
public class ActivityProcedureController {

    @Autowired
    ActivityProcedureService _service;

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la activityProcedure", notes = "")
    public ResponseEntity<ActivityProcedure> save(@RequestBody ActivityProcedure item) throws Exception {
        ResponseEntity<ActivityProcedure> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @GetMapping("/list-by-procedure/{id}")
    @ApiOperation(value = "Método que devuelve listado de activityProcedure", notes = "")
    public ResponseEntity<ActivityProcedure> listIdProcedure(@PathVariable("id") int idProcedure) throws Exception   {
        ResponseEntity<ActivityProcedure> response = new ResponseEntity<>();
        response = this._service.listByIdProcedure(idProcedure);
        return response;
    }

}