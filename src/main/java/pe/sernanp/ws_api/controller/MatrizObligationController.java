package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.dto.MatrizObligationDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MatrizObligation;
import pe.sernanp.ws_api.service.MatrizObligationService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value="/matriz-obligation")
@Api(description = "Controladora de servicio para matriz obligacion", tags = "matriz-obligation-Controller")
public class MatrizObligationController extends BaseController {
    @Autowired
    MatrizObligationService _service;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve listado de matriz obligacion con filtros", notes = "")
    public ResponseEntity<MatrizObligationDTO> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<MatrizObligationDTO> response = new ResponseEntity<MatrizObligationDTO>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            MatrizObligation item2 = super.fromJson(item, MatrizObligation.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @GetMapping
    @ApiOperation(value = "Método que devuelve el listado de matriz obligacion", notes = "")
    public ResponseEntity<MatrizObligation> list() throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<>();
        response = this._service.findAll();
        return response;
    }

    @GetMapping("list-recordcode")
    @ApiOperation(value = "Método que devuelve el listado de matriz obligacion", notes = "")
    public ResponseEntity<ListDTO> listRecordCode() throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        response = this._service.listRecordCode();
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una matriz obligacion", notes = "")
    public ResponseEntity<MatrizObligation> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar una matriz obligacion", notes = "")
    public ResponseEntity<MatrizObligation> save(@RequestBody MatrizObligation item) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    @PostMapping("/fiscal-obligation")
    @ApiOperation(value = "Método que permite registrar una obligación fiscal en la matriz obligacion", notes = "")
    public ResponseEntity<MatrizObligation> saveFiscalObligation(@RequestBody MatrizObligation item) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<>();
        response = this._service.saveFiscalObligation(item);
        return response;
    }

    @GetMapping("/fiscal-obligation/{id}")
    @ApiOperation(value = "Método que permite registrar una obligación fiscal en la matriz obligacion", notes = "")
    public ResponseEntity<FiscalObligationDTO> listFiscalObligation(@PathVariable("id") int id) throws Exception {
        ResponseEntity<FiscalObligationDTO> response = new ResponseEntity<>();
        response = this._service.listFiscalObligation(id);
        return response;
    }

    @PostMapping("/fiscal-obligation/delete")
    @ApiOperation(value = "Método que permite eliminar una obligación fiscal en la matriz obligacion", notes = "")
    public ResponseEntity<MatrizObligation> deleteFiscalObligation(@RequestBody MatrizObligation item) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<>();
        response = this._service.deleteFiscalObligation(item);
        return response;
    }

    @PostMapping("/fiscal-obligation/deletemassive")
    @ApiOperation(value = "Método que permite eliminar una obligación fiscal en la matriz obligacion", notes = "")
    public ResponseEntity<MatrizObligation> deleteMassiveFiscalObligation(@RequestBody List<MatrizObligation> items) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<>();
        response = this._service.deleteMassiveFiscalObligation(items);
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una matriz obligacion", notes = "")
    public ResponseEntity<MatrizObligation> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve reporte en excel de matriz obligacion", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> export(@RequestBody MatrizObligation item) throws Exception{
        ByteArrayInputStream stream = this._service.export(item);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Matriz_obligacion_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

    @GetMapping("/fiscal-obligation/export/{id}")
    @ApiOperation(value = "Método que devuelve reporte en excel de matriz obligacion", notes = "")
    @ResponseBody()
    public org.springframework.http.ResponseEntity<InputStreamResource> exportFiscalObligation(@PathVariable("id") int id) throws Exception{
        ByteArrayInputStream stream = this._service.exportFiscalObligation(id);
        HttpHeaders headers = new HttpHeaders();
        LocalDate dateActual = LocalDate.now();
        String documentName = "Obligaciones_fiscales_" + dateActual.toString();
        headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
        return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
}
