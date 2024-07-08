package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ODDTO;
import pe.sernanp.ws_api.dto.SupervisionRecordDTO;
import pe.sernanp.ws_api.dto.SupervisionRecordRequestDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.OD;
import pe.sernanp.ws_api.model.SupervisionRecord;
import pe.sernanp.ws_api.service.SupervisionRecordService;

@RestController
@RequestMapping(value="/supervision-record")
@Api(description = "Controladora de servicio de actas de supervisión")
public class SupervisionRecordController extends BaseController {
    @Autowired
    SupervisionRecordService _service;

//    @GetMapping
//    @ApiOperation(value = "Método que devuelve el listado de actas de supervisión", notes = "")
//    public ResponseEntity<SupervisionRecord> list() throws Exception {
//        ResponseEntity<SupervisionRecord> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/list-by-type/{typeId}")
    @ApiOperation(value = "Método que devuelve el listado de actas de supervisión por tipo", notes = "")
    public ResponseEntity<SupervisionRecord> listByType(@PathVariable("typeId") int typeId) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<>();
        response = this._service.listByType(typeId);
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un acta de supervisión", notes = "")
    public ResponseEntity<SupervisionRecord> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método para grabar un acta de supervisión", notes = "")
    public ResponseEntity<SupervisionRecord> save(@RequestBody SupervisionRecord item) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Método para grabar un acta de supervisión", notes = "")
    public ResponseEntity<SupervisionRecord> save(@RequestParam("item") String item, @RequestParam("file") MultipartFile file,@RequestParam("folderId") String folderId) throws Exception {
        SupervisionRecord item2 = super.fromJson(item, SupervisionRecord.class);
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<>();
        response = this._service.saveWithFile(item2, file, folderId);
        return response;
    }
    @PutMapping("/finalize/{id}")
    @ApiOperation(value = "Método para finalizar un acta de supervisión", notes = "")
    public ResponseEntity<SupervisionRecord> finalize(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<>();
        response = this._service.finalizeRegister(id);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método para eliminar un acta de supervisión", notes = "")
    public ResponseEntity<SupervisionRecord> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve una permite buscar Otorgamiento de derechos con filtros y paginador", notes = "")
    public ResponseEntity<SupervisionRecordDTO> search(@RequestParam("item") String item, @RequestParam("paginator") String paginator) throws Exception {
        ResponseEntity<SupervisionRecordDTO> response = new ResponseEntity<SupervisionRecordDTO>();
        try {
            //PaginatorEntity paginator = super.setPaginator();
            PaginatorEntity paginator2 = super.fromJson(paginator, PaginatorEntity.class);
            SupervisionRecordRequestDTO item2 = super.fromJson(item, SupervisionRecordRequestDTO.class);
            response = this._service.search(item2, paginator2);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }
}
