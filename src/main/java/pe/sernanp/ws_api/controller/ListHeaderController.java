package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListHeader;
import pe.sernanp.ws_api.service.ListHeaderService;

@RestController
@RequestMapping(value="/listheader")
@Api(description = "Controladora de servicio de tablas maestras")
public class ListHeaderController extends BaseController {
    @Autowired
    ListHeaderService _service;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve listado de las tablas maestras con filtros", notes = "")
    public ResponseEntity<ListHeader> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<ListHeader>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            ListHeader item2 = super.fromJson(item, ListHeader.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

//    @GetMapping
//    public ResponseEntity<ListDetailDTO> list() throws Exception {
//        ResponseEntity<ListDetailDTO> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ListHeader> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    public ResponseEntity<ListHeader> save(@RequestBody ListHeader item) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    public ResponseEntity<ListHeader> update(@RequestBody ListHeader item) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ListHeader> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }
}
