package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.MonitoringRecordDTO;
import pe.sernanp.ws_api.dto.MonitoringRecordRequestDTO;
import pe.sernanp.ws_api.dto.SupervisionRecordDTO;
import pe.sernanp.ws_api.dto.SupervisionRecordRequestDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRCompetitor;
import pe.sernanp.ws_api.model.MonitoringRecord;
import pe.sernanp.ws_api.model.SupervisionRecord;
import pe.sernanp.ws_api.service.MonitoringRecordService;

@RestController
@RequestMapping(value="/monitoring-record")
@Api(description = "Controladora de servicio de actas de seguimiento")
public class MonitoringRecordController extends BaseController {
    @Autowired
    MonitoringRecordService _service;

//    @GetMapping
//    @ApiOperation(value = "Método que devuelve el listado de actas de seguimiento", notes = "")
//    public ResponseEntity<MonitoringRecord> list() throws Exception {
//        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
//        response = this._service.findAll();
//        return response;
//    }

    @GetMapping("/list-by-type/{typeId}")
    @ApiOperation(value = "Método que devuelve el listado de actas de seguimiento por tipo", notes = "")
    public ResponseEntity<MonitoringRecord> listByType(@PathVariable("typeId") int typeId) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        response = this._service.listByType(typeId);
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de un acta de seguimiento", notes = "")
    public ResponseEntity<MonitoringRecord> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @GetMapping("/list-by-resource/{resourceId}")
    @ApiOperation(value = "Método que devuelve el listado de actas de seguimiento", notes = "")
    public ResponseEntity<MonitoringRecord> listByResource(@PathVariable("resourceId") int resourceId) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        response = this._service.listByResource(resourceId);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método para guardar un acta de seguimiento", notes = "")
    public ResponseEntity<MonitoringRecord> save(@RequestBody MonitoringRecord item) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }
    @PutMapping("/finalize/{id}")
    @ApiOperation(value = "Método para finalizar un acta de seguimiento", notes = "")
    public ResponseEntity<MonitoringRecord> finalize(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        response = this._service.finalizeRegister(id);
        return response;
    }
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve una permite buscar Otorgamiento de derechos con filtros y paginador", notes = "")
    public ResponseEntity<MonitoringRecordDTO> search(@RequestParam("item") String item, @RequestParam("paginator") String paginator) throws Exception {
        ResponseEntity<MonitoringRecordDTO> response = new ResponseEntity<MonitoringRecordDTO>();
        try {
            PaginatorEntity paginator2 =super.fromJson(paginator, PaginatorEntity.class);
            MonitoringRecordRequestDTO item2 = super.fromJson(item, MonitoringRecordRequestDTO.class);
            response = this._service.search(item2, paginator2);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }
    @PutMapping()
    @ApiOperation(value = "Método para actualizar un acta de seguimiento", notes = "")
    public ResponseEntity<MonitoringRecord> update(@RequestBody MonitoringRecord item) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        response = this._service.update(item);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método para eliminar un acta de seguimiento", notes = "")
    public ResponseEntity<MonitoringRecord> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Método para grabar un acta de seguimiento", notes = "")
    public ResponseEntity<MonitoringRecord> save(@RequestParam("item") String item, @RequestParam("file") MultipartFile file,@RequestParam("folderId") String folderId) throws Exception {
        MonitoringRecord item2 = super.fromJson(item, MonitoringRecord.class);
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        response = this._service.saveWithFile(item2, file, folderId);
        return response;
    }
}
