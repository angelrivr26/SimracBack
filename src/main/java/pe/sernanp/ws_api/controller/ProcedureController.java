package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.sernanp.ws_api.dto.*;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ProcedureAssign;
import pe.sernanp.ws_api.model.ProcedureMPV;
import pe.sernanp.ws_api.service.ProcedureService;

@RestController
@RequestMapping(value="/procedure")
@Api(description = "Controlador de servicio de Tramite", tags = "procedure-controller")
public class
ProcedureController extends BaseController {
	@Autowired
	ProcedureService _service;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ApiOperation(value = "Método que devuelve una permite buscar Tramite con filtros y paginador", notes = "")
	public ResponseEntity<ProcedureResponseDTO> search(@RequestParam("item") String item) throws Exception {
		ResponseEntity<ProcedureResponseDTO> response = new ResponseEntity<ProcedureResponseDTO>();
		try {
			PaginatorEntity paginator = super.setPaginator();
			ProcedureRequestDTO item2 = super.fromJson(item, ProcedureRequestDTO.class);
			response = this._service.search(item2, paginator);
		} catch (Exception ex){
			response.setMessage("Ocurrio un error en el buscar");
			response.setExtra(ex.getMessage());
			response.setSuccess(false);
		}
        return response;
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Método que devuelve el detalle de un Trámite", notes = "")
	public ResponseEntity<?> detail(@PathVariable("id") int id) throws Exception {
		ResponseEntity<?> response = new ResponseEntity<>();
		response = this._service.findById(id);
		return response;
	}

	@RequestMapping(value = "/criterion", method = RequestMethod.POST)
	@ApiOperation(value = "Método que permite registrar los criterios", notes = "")
	public ResponseEntity<?> save(@RequestBody CriterionResponseDTO item) throws Exception {
		ResponseEntity<?> response = new ResponseEntity<>();
		response = this._service.saveCriterion(item);
		return response;
	}

	@GetMapping("/result/{id}")
	@ApiOperation(value = "Método que devuelve el resultado de evaluación de un Trámite", notes = "")
	public ResponseEntity<ProcedureMPV> result(@PathVariable("id") int id) throws Exception {
		ResponseEntity<ProcedureMPV> response = new ResponseEntity<>();
		response = this._service.result(id);
		return response;
	}

	@RequestMapping(value = "/determination", method = RequestMethod.POST)
	@ApiOperation(value = "Método que permite registrar los criterios", notes = "")
	public ResponseEntity<?> saveDetermination(@RequestBody DeterminationResponseDTO item) throws Exception {
		ResponseEntity<?> response = new ResponseEntity<>();
		response = this._service.saveDetermination(item);
		return response;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.POST)
	@ApiOperation(value = "Método que permite registrar la asignación de trámites", notes = "")
	public ResponseEntity<?> saveAssign(@RequestBody ProcedureAssignDTO item) throws Exception {
		ResponseEntity<?> response = new ResponseEntity<>();
		response = this._service.saveAssign(item);
		return response;
	}

	@GetMapping("/assign/{id}")
	@ApiOperation(value = "Método que devuelve la asignación un Trámite", notes = "")
	public ResponseEntity<?> getAssign(@PathVariable("id") int id) throws Exception {
		ResponseEntity<?> response = new ResponseEntity<>();
		response = this._service.getAssign(id);
		return response;
	}

	@GetMapping("/listAssign/{id}")
	@ApiOperation(value = "Método que devuelve la lista de asignación de un Trámite", notes = "")
	public ResponseEntity<ProcedureAssign> getListAssign(@PathVariable("id") int id) throws Exception {
		ResponseEntity<ProcedureAssign> response = new ResponseEntity<>();
		response = this._service.getList(id);
		return response;
	}

	@GetMapping("/updateCut/{id}/{record}/{year}")
	@ApiOperation(value = "Método que devuelve la lista de asignación de un Trámite", notes = "")
	public ResponseEntity<ProcedureAssign> updateCut(@PathVariable("id") int id, @PathVariable("record") int record, @PathVariable("year") int period) throws Exception {
		ResponseEntity<ProcedureAssign> response = new ResponseEntity<>();
		response = this._service.updateCut(id, record, period);
		return response;
	}



}
