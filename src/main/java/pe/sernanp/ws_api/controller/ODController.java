package pe.sernanp.ws_api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ODDTO;
import pe.sernanp.ws_api.entity.MdPOdEntity;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.model.OD;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.service.ODService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/od")
@Api(description = "Controlador de servicio de Otorgamiento de derecho", tags = "grant-rights-controller")
public class ODController extends BaseController {
	@Autowired
	ODService _service;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ApiOperation(value = "Método que devuelve una permite buscar Otorgamiento de derechos con filtros y paginador", notes = "")
	public ResponseEntity<ODDTO> search(@RequestParam("item") String item) throws Exception {
		ResponseEntity<ODDTO> response = new ResponseEntity<ODDTO>();
		try {
			PaginatorEntity paginator = super.setPaginator();
			OD item2 = super.fromJson(item, OD.class);
			response = this._service.search(item2, paginator);
		} catch (Exception ex){
			response.setMessage("Ocurrio un error en el buscar");
			response.setExtra(ex.getMessage());
			response.setSuccess(false);
		}
        return response;
	}
	@RequestMapping(value = "/search2", method = RequestMethod.POST)
	@ApiOperation(value = "Método que devuelve una permite buscar Otorgamiento de derechos con filtros", notes = "")
	public ResponseEntity<ODDTO> search(@RequestBody OD item) throws Exception {
		ResponseEntity<ODDTO> response = new ResponseEntity<ODDTO>();
		try {
			response = this._service.search(item);
		} catch (Exception ex){
			response.setMessage("Ocurrio un error en el buscar");
			response.setExtra(ex.getMessage());
			response.setSuccess(false);
		}
		return response;
	}

	@GetMapping("/list-by-type/{typeId}")
	@ApiOperation(value = "Método que devuelve una lista de Otorgamiento de derechos", notes = "")
	public ResponseEntity<OD> listByType(@PathVariable("typeId") int typeId) throws Exception {
		ResponseEntity<OD> response = new ResponseEntity<>();
		response = this._service.findByType(typeId);
		return response;
	}
	@GetMapping("/{id}")
	@ApiOperation(value = "Método que devuelve el detalle de un Otorgamiento de derecho", notes = "")
	public ResponseEntity<OD> detail(@PathVariable("id") int id) throws Exception {
		ResponseEntity<OD> response = new ResponseEntity<>();
		response = this._service.findById(id);
		return response;
	}

	@PostMapping("/update-finalize")
	@ApiOperation(value = "Método que permite actualizar un Otorgamiento de derecho a la versión final", notes = "")
	public ResponseEntity<OD> updateFinalize(@RequestParam("item") String item, @RequestParam("resolutionFile") MultipartFile resolutionFile, @RequestParam("titleRouteFile") MultipartFile titleRouteFile) throws Exception {
		ResponseEntity<OD> response = new ResponseEntity<>();
		OD item2 = super.fromJson(item, OD.class);
		item2.setFlagDraft(false);
		response = this._service.update(item2, resolutionFile, titleRouteFile);
		return response;
	}

	@PostMapping("/save-draft")
	@ApiOperation(value = "Método que permite registrar un borrador de Otorgamiento de derecho", notes = "")
	public ResponseEntity<OD> saveDraft(@RequestParam("item") String item, @RequestParam("resolutionFile") MultipartFile resolutionFile, @RequestParam("titleRouteFile") MultipartFile titleRouteFile) throws Exception {
		ResponseEntity<OD> response = new ResponseEntity<>();
		OD item2 = super.fromJson(item, OD.class);
		item2.setFlagDraft(true);
		response = this._service.save(item2, resolutionFile, titleRouteFile);
		return response;
	}

	@PutMapping("/update-draft")
	@ApiOperation(value = "Método que permite actualizar un borrador de Otorgamiento de derecho", notes = "")
	public ResponseEntity<OD> updateDarft(@RequestParam("item") String item, @RequestParam("resolutionFile") MultipartFile resolutionFile, @RequestParam("titleRouteFile") MultipartFile titleRouteFile) throws Exception {
		ResponseEntity<OD> response = new ResponseEntity<>();
		OD item2 = super.fromJson(item, OD.class);
		item2.setFlagDraft(true);
		response = this._service.update(item2, resolutionFile, titleRouteFile);
		return response;
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Método que permite eliminar Otorgamiento de derecho", notes = "")
	public ResponseEntity<OD> delete(@PathVariable("id") int id) throws Exception {
		ResponseEntity<OD> response = new ResponseEntity<>();
		response = this._service.delete(id);
		return response;
	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ApiOperation(value = "Método que devuelve reporte en excel de otorgamientos de dercho", notes = "")
	@ResponseBody()
	public org.springframework.http.ResponseEntity<InputStreamResource> export(@RequestBody OD item) throws Exception{
		ByteArrayInputStream stream = this._service.export(item);
		HttpHeaders headers = new HttpHeaders();
		LocalDate dateActual = LocalDate.now();
		String documentName = "Otorgamientos" + dateActual.toString();
		headers.add("Content-Disposition", "attachment; filename="+  documentName + ".xls");
		return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}

	@RequestMapping(value = "/download-file/{fileId}", method = RequestMethod.GET)
	@ApiOperation(value = "Método que el devuelve documento requisito de modalidad", notes = "")
	@ResponseBody()
	public org.springframework.http.ResponseEntity getFile(@PathVariable("fileId") String fileId) throws Exception{
		byte[] bytes = this._service.getFile(false, fileId);
		String fileName = _service.getFileName(fileId);
		LocalDate dateActual = LocalDate.now();
		fileName = fileName == "" ? "Archivo_sin_Nombre_" + dateActual : fileName.replaceAll("\\s", "_").replace("\u00a0","_");
		if (fileName != "" && bytes != null && bytes.length > 0) {
			ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename*=utf-8''" + fileName);
			return org.springframework.http.ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
		}
		else
			return new org.springframework.http.ResponseEntity<String>("El documento " + (fileName == "" ? "solicitado" : fileName) + " no existe o esta vacio.", HttpStatus.OK);
	}


	@PostMapping("/save-external")
	@ApiOperation(value = "Método que permite registrar un borrador de Otorgamiento de derecho desde Mesa de partes", notes = "")
	public ResponseEntity<MdPOdEntity> saveExternal(@RequestBody MdPOdEntity item) throws Exception {
		ResponseEntity<MdPOdEntity> response = new ResponseEntity<>();
		response = this._service.saveForExternal(item);
		return response;
	}


	@GetMapping("/get-for-external/{procedureId}")
	@ApiOperation(value = "Método que devuelve el detalle de un Otorgamiento de derecho para Mesa de partes", notes = "")
	public ResponseEntity<MdPOdEntity> getForExternal(@PathVariable("procedureId") int procedureId) throws Exception {
		ResponseEntity<MdPOdEntity> response = new ResponseEntity<>();
		response = this._service.getForExternal(procedureId);
		return response;
	}
}
