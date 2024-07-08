package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.dto.SitePlanDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SitePlan;
import pe.sernanp.ws_api.repository.SitePlanRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.SitePlanService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class SitePlanServiceImpl extends BaseServiceImpl implements SitePlanService {
    @Autowired
    SitePlanRepository _repository;

    @Autowired
    DocumentService documentService;

    public ResponseEntity<SitePlanDTO> search(SitePlan item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<SitePlanDTO> response = new ResponseEntity<SitePlanDTO>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            String anpCode = isNullOrEmpty(item.getAnpCode()) ? "" : item.getAnpCode();
            int instrumentTypeId = item.getInstrumentType() == null ? 0 : item.getInstrumentType().getId();
            String resolution = isNullOrEmpty(item.getResolution()) ? "" : item.getResolution();
            int flagValid = item.getFlagValid() == null ? 2 : (item.getFlagValid() ? 1 : 0);
            String name = isNullOrEmpty(item.getName()) ? "" : item.getName();
            int flagDraft = item.getFlagDraft() == null ? 2 : (item.getFlagDraft() ? 1 : 0);
            boolean isDeleted = false;//item.getIsDeleted()== null ? false : item.getIsDeleted();

            Page<SitePlanDTO> pag = this._repository.search(anpCode, instrumentTypeId, resolution, name, flagValid, flagDraft, isDeleted, page);
            List<SitePlanDTO> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());
            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar los planes de sitio.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SitePlan> findAll() throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<SitePlan>();
        try {
            List<SitePlan> items = _repository.findByIsDeleted(false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SitePlan> findById(int id) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<SitePlan>();
        try {
            Optional<SitePlan> item = _repository.findById(id);
            if (item.isPresent())
                response.setItem(item.get());
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SitePlan> findByAnpCode(String anpCodes) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<SitePlan>();
        try {
            List<SitePlan> items = _repository.findByAnpCode(isNullOrEmpty(anpCodes) ? "" : anpCodes);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SitePlan> findByPolygonCode(String polygonCode) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<SitePlan>();
        try {
            List<SitePlan> items = _repository.findByPolygonCode(isNullOrEmpty(polygonCode) ? "" : polygonCode);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SitePlan> save(SitePlan item, MultipartFile resolutionFile, MultipartFile instrumentFile) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<SitePlan>();
        try {
            if (!isNullOrEmpty(item.getAnpCode())){
                String anpNames = _repository.getAnpNames(item.getAnpCode());
                item.setAnpName(anpNames);
            }
            item = _repository.save(item);

            if (resolutionFile != null && !resolutionFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                DocumentoDTO temp = documentService.saveFile(resolutionFile, false, item.getDigitalRouteId(), item.getResolution());
                if (temp.getSuccess()){
                    item.setResolutionRouteId(temp.getId());
                    _repository.updateResolutionRouteDocumentId(item.getId(), temp.getId());
                }
                else response.setExtra(temp.getMessagge());
            }
            if (instrumentFile != null && !instrumentFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                DocumentoDTO temp2 = documentService.saveFile(instrumentFile, false, item.getDigitalRouteId(), item.getInstrumentFile());
                if (temp2.getSuccess()){
                    item.setInstrumentFileId(temp2.getId());
                    _repository.updateInstrumentFile(item.getId(), temp2.getId());
                }
                else response.setExtra(temp2.getMessagge());
            }
            response.setItem(item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SitePlan> update(SitePlan item, MultipartFile resolutionFile, MultipartFile instrumentFile) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<SitePlan>();
        try {
            Optional<SitePlan> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                if (!item.getFlagDraft()) {
                    boolean validate = (item.getInstrumentType() == null || item.getInstrumentType().getId() == 0 || isNullOrEmpty(item.getName())
                            || isNullOrEmpty(item.getAnpCode()) || isNullOrEmpty(item.getResolution())
                            || isNullOrEmpty(item.getInstrumentFile()));
                    if (validate) {
                        response.setMessage("Se debe completar lo campos obligatorios del plan de sitio.");
                        response.setSuccess(false);
                        response.setWarning(true);
                        return response;
                    }
                }

                item.setResolutionRouteId(_item.get().getResolutionRouteId());
                item.setInstrumentFileId(_item.get().getInstrumentFileId());

                if (!isNullOrEmpty(item.getAnpCode())){
                    String anpNames = _repository.getAnpNames(item.getAnpCode());
                    item.setAnpName(anpNames);
                }

                item = _repository.save(item);

                if (resolutionFile != null && !resolutionFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                    DocumentoDTO temp = documentService.saveFile(resolutionFile, false, item.getDigitalRouteId(), item.getResolution());
                    if (temp.getSuccess()){
                        item.setResolutionRouteId(temp.getId());
                        _repository.updateResolutionRouteDocumentId(item.getId(), temp.getId());
                    }
                    else response.setExtra(temp.getMessagge());
                }
                if (instrumentFile != null && !instrumentFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                    DocumentoDTO temp2 = documentService.saveFile(instrumentFile, false, item.getDigitalRouteId(), item.getInstrumentFile());
                    if (temp2.getSuccess()){
                        item.setInstrumentFileId(temp2.getId());
                        _repository.updateInstrumentFile(item.getId(), temp2.getId());
                    }
                    else response.setExtra(temp2.getMessagge());
                }
                response.setItem(item);
                response.setMessage("Se actualizó el registro correctamente");
            }
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al actualizar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SitePlan> delete(int id) throws Exception {
        ResponseEntity<SitePlan> response = new ResponseEntity<SitePlan>();
        try {
            Optional<SitePlan> item = _repository.findById(id);
            if (item.isPresent()){
                _repository.deleteById(id);
                response.setMessage("Se elimino el registro correctamente.");
                //_repository.updateIsDeleted(id, false);
            }
            else {
                response.setMessage("El registro no existe.");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            if (((ConstraintViolationException) ex.getCause()).getSQLState().equals("23503")) {
                response.setMessage("No se puede eliminar un plan de sitio mientras es utilizado.");
                response.setSuccess(false);
                response.setWarning(true);
                response.setExtra(ex.getMessage());
            } else {
                response.setMessage("Ocurrio un error al eliminar");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
            return response;
        }
        return response;
    }

    public ByteArrayInputStream export(SitePlan item) throws Exception {
        String[] columns = { "ANP", "TIPO", "NOMBRE", "RESOLUCION", "PERIODO", "VIGENTE" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PlanSitio");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        String anpCode = isNullOrEmpty(item.getAnpCode()) ? "" : item.getAnpCode();
        int instrumentTypeId = item.getInstrumentType() == null ? 0 : item.getInstrumentType().getId();
        String resolution = isNullOrEmpty(item.getResolution()) ? "" : item.getResolution();
        int flagValid = item.getFlagValid() == null ? 2 : (item.getFlagValid() ? 1 : 0);
        String name = isNullOrEmpty(item.getName()) ? "" : item.getName();
        //int resourceId = (item.getResources() == "0" || isNullOrEmpty(item.getResources())) ? 0 : Integer.parseInt(item.getResources());
        int flagDraft = item.getFlagDraft() == null ? 2 : (item.getFlagDraft() ? 1 : 0);
        boolean isDeleted = false;//item.getIsDeleted()== null ? false : item.getIsDeleted();

        List<SitePlanDTO> items = this._repository.search2(anpCode, instrumentTypeId, resolution, name, flagValid, flagDraft, isDeleted);

        int initRow = 1;
        for (SitePlanDTO _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getAnpName());
            row.createCell(1).setCellValue(_item.getInstrumentType() == null ? "" : _item.getInstrumentType().getName());
            row.createCell(2).setCellValue(_item.getName());
            row.createCell(3).setCellValue(_item.getResolution());
            row.createCell(4).setCellValue(_item.getPeriod());
            row.createCell(5).setCellValue(_item.getFlagValidity() ? "Activo" : "Inactivo");
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }

    public String getFileName (String fileId) throws Exception {
        try {
            String fileName = _repository.getFileNameByFileId(fileId);
            return URLEncoder.encode(fileName.trim(), "UTF-8").replace("+", "%20");
        }catch (Exception ex) {
            return "";
        }
    }
}
