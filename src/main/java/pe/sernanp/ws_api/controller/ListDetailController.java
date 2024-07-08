package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.service.ListDetailService;

@RestController
@RequestMapping(value="/listdetail")
@Api(description = "Controladora de servicio de detalle tablas maestras")
public class ListDetailController extends BaseController {
    @Autowired
    ListDetailService _service;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve listado de las tablas maestras con filtros", notes = "")
    public ResponseEntity<ListDetail> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<ListDetail>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            ListDetail item2 = super.fromJson(item, ListDetail.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

//    @GetMapping
//    public ResponseEntity<ListDetail> list() throws Exception {
//        ResponseEntity<ListDetail> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/list-by-code/{code}")
    @ApiOperation(value = "Método que devuelve el listado de una tabla maestra", notes = "")
    public ResponseEntity<ListDetailDTO> findByCode(@PathVariable("code") int code) throws Exception {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<>();
        response = this._service.findByCode(code);
        return response;
    }

    @GetMapping("/list-by-type/{id}")
    @ApiOperation(value = "Método que devuelve el listado de una tabla maestra por tipo", notes = "")
    public ResponseEntity<ListDetailDTO> findByListDetailId(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<>();
        response = this._service.findByListDetailId(id);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListDetail> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    public ResponseEntity<ListDetail> save(@RequestBody ListDetail item) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PutMapping()
    public ResponseEntity<ListDetail> update(@RequestBody ListDetail item) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ListDetail> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }
}
