package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.AnpConfigDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.AnpConfig;
import pe.sernanp.ws_api.service.AnpConfigService;

@RestController
@RequestMapping(value="/anpconfig")
@Api(description = "Controladora de servicio de Configuracion ANP", tags = "anp-config-controller")
public class AnpConfigController extends BaseController {
    @Autowired
    AnpConfigService _service;
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Método que devuelve listado de Actividades con filtros", notes = "")
    public ResponseEntity<AnpConfigDTO> search(@RequestParam("item") String item) throws Exception {
        ResponseEntity<AnpConfigDTO> response = new ResponseEntity<AnpConfigDTO>();
        try {
            PaginatorEntity paginator = super.setPaginator();
            AnpConfig item2 = super.fromJson(item, AnpConfig.class);
            response = this._service.search(item2, paginator);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error en el buscar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @PostMapping("/search-related")
    @ApiOperation(value = "Método que devuelve una configuración registrada según sus parametros", notes = "")
    public ResponseEntity<ListDTO> searchRelated(@RequestBody AnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        response = this._service.searchRelated(item);
        return response;
    }

    @GetMapping("/list-by-modality/{modalityId}")
    @ApiOperation(value = "Método que devuelve listado de Actividad de Configuración ANP según modalidad", notes = "")
    public ResponseEntity<ListDTO> listByModality(@PathVariable("modalityId") int modalityId) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        response = this._service.listByModality(modalityId);
        return response;
    }

    @PostMapping("/detail-by-anpconfig")
    @ApiOperation(value = "Método que devuelve una configuración registrada según sus parametros", notes = "")
    public ResponseEntity<AnpConfig> findByAnpConfig(@RequestBody AnpConfig item) throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<>();
        response = this._service.findByAnpConfig(item);
        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Método que devuelve el detalle de una Configuración ANP", notes = "")
    public ResponseEntity<AnpConfig> detail(@PathVariable("id") int id) throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<>();
        response = this._service.findById(id);
        return response;
    }

    @PostMapping()
    @ApiOperation(value = "Método que permite registrar la Configuración ANP", notes = "")
    public ResponseEntity<AnpConfig> save(@RequestBody AnpConfig item) throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<>();
        response = this._service.save(item);
        return response;
    }

    // comentado hasta definir el tema de versiones
//    @PutMapping()
//    @ApiOperation(value = "Método que permite actualizar de Configuración ANP", notes = "")
//    public ResponseEntity<AnpConfig> update(@RequestBody AnpConfig item) throws Exception {
//        ResponseEntity<AnpConfig> response = new ResponseEntity<>();
//        response = this._service.update(item);
//        return response;
//    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Método que permite eliminar una Configuración ANP", notes = "")
    public ResponseEntity<AnpConfig> delete(@PathVariable("id") int id) throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<>();
        response = this._service.delete(id);
        return response;
    }
}
