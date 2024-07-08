package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ODDocument;
import pe.sernanp.ws_api.service.ODDocumentService;

@RestController
@RequestMapping(value="/od/document")
@Api(description = "Controladora de servicio de documento od", tags = "grant-rights-document-controller")
public class ODDocumentController {
    @Autowired
    ODDocumentService _service;

//    @GetMapping
//    public ResponseEntity<ODDocument> list() throws Exception {
//        ResponseEntity<ODDocument> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una documento od", notes = "")
    public ResponseEntity<ODDocument> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la documento od", notes = "")
    public ResponseEntity<ODDocument> save(@RequestBody ODDocument item) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    @ApiOperation(value = "Método que permite actualizar la documento od", notes = "")
    public ResponseEntity<ODDocument> update(@RequestBody ODDocument item) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una documento od", notes = "")
    public ResponseEntity<ODDocument> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @GetMapping("/list-by-od/{id}")
    @ApiOperation(value = "Método que devuelve listado de documento od por id de od", notes = "")
    public ResponseEntity<ODDocument> findByOdId(@PathVariable("id") int odId) throws Exception {
        ResponseEntity<ODDocument> response = new ResponseEntity<>();
        response = this._service.findByOdId(odId);
        return response;
    }
}
