package pe.sernanp.ws_api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.UserOfficeDTO;
import pe.sernanp.ws_api.dto.UserOfficeI;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.UserOffice;
import pe.sernanp.ws_api.service.UserOfficeService;

@RestController
@RequestMapping(value="/useroffice")
public class UserOfficeController extends BaseController {
    @Autowired(required=true)
    UserOfficeService _service;

    @GetMapping("/{userId}/{rolId}")
    @ApiOperation(value = "Método que devuelve el detalle de un usuario", notes = "")
    ResponseEntity<UserOffice> detail (@PathVariable int userId, @PathVariable int rolId){
        ResponseEntity<UserOffice> response = new ResponseEntity<>();
        response = this._service.findById(userId, rolId);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que guarda un usuario rol oficina", notes = "")
    ResponseEntity<UserOfficeDTO> save (@RequestBody UserOfficeDTO item){
        ResponseEntity<UserOfficeDTO> response = this._service.save(item);
        return response;
    }
    @DeleteMapping("/{userId}/{rolId}")
    @ApiOperation(value = "Método que elimina un usuario oficina", notes = "")
    ResponseEntity<UserOffice> delete (@PathVariable int userId, @PathVariable int rolId){
        ResponseEntity<UserOffice> response = this._service.delete(userId, rolId);
        return response;
    }

    @GetMapping("/detail/{userId}/{rolId}")
    @ApiOperation(value = "Método que devuelve el listado de oficinas de un usuario", notes = "")
    ResponseEntity<UserOfficeI> detailData (@PathVariable int userId, @PathVariable int rolId){
        ResponseEntity<UserOfficeI> response = new ResponseEntity<>();
        response = this._service.detail(userId, rolId);
        return response;
    }
}
