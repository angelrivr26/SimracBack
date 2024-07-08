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
import pe.sernanp.ws_api.dto.ManagementPlanDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ManagementPlan;
import pe.sernanp.ws_api.model.SitePlan;
import pe.sernanp.ws_api.repository.ManagementPlanRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.ManagementPlanService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ManagementPlanServiceImpl extends BaseServiceImpl implements ManagementPlanService {
    @Autowired
    ManagementPlanRepository _repository;

    @Autowired
    DocumentService documentService;

    public ResponseEntity<ManagementPlanDTO> search(ManagementPlan item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<ManagementPlanDTO> response = new ResponseEntity<ManagementPlanDTO>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            String anpCode = isNullOrEmpty(item.getAnpCode()) ? "" : item.getAnpCode();
            int instrumentTypeId = item.getInstrumentType() == null ? 0 : item.getInstrumentType().getId();
            String name = isNullOrEmpty(item.getName()) ? "" : item.getName();
            String resolution = isNullOrEmpty(item.getResolution()) ? "" : item.getResolution();
            int flagValid = item.getFlagValid() == null ? 2 : (item.getFlagValid() ? 1 : 0);
            int flagDraft = item.getFlagDraft() == null ? 2 : (item.getFlagDraft() ? 1 : 0);
            boolean isDeleted = item.getIsDeleted()== null ? false : item.getIsDeleted();

            Page<ManagementPlanDTO> pag = this._repository.search(anpCode, instrumentTypeId, resolution, name, item.getResourceTypeId(), item.getResourceId(), flagValid, flagDraft, isDeleted, page);
            List<ManagementPlanDTO> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());
            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar los planes de manejo.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ManagementPlan> findAll() throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<ManagementPlan>();
        try {
            List<ManagementPlan> items = _repository.findByIsDeleted(false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ManagementPlan> findById(int id) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<ManagementPlan>();
        try {
            Optional<ManagementPlan> item = _repository.findById(id);
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

    public ResponseEntity<ManagementPlan> findByAnpCode(String anpCodes) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<ManagementPlan>();
        try {
            List<ManagementPlan> items = _repository.findByAnpCode(isNullOrEmpty(anpCodes) ? "" : anpCodes);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ManagementPlan> save(ManagementPlan item, MultipartFile resolutionFile, MultipartFile managementPlanFile, MultipartFile compatibilityReportFile) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<ManagementPlan>();
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
            if (managementPlanFile != null && !managementPlanFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                DocumentoDTO temp2 = documentService.saveFile(managementPlanFile, false, item.getDigitalRouteId(), item.getManagementPlanFile());
                if (temp2.getSuccess()){
                    item.setManagementPlanFileId(temp2.getId());
                    _repository.updateManagementPlanFileId(item.getId(), temp2.getId());
                }
                else response.setExtra(temp2.getMessagge());
            }
            if (compatibilityReportFile != null && !compatibilityReportFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                DocumentoDTO temp3 = documentService.saveFile(compatibilityReportFile, false, item.getDigitalRouteId(), item.getCompatibilityReportFile());
                if (temp3.getSuccess()){
                    item.setCompatibilityReportFileId(temp3.getId());
                    _repository.updateCompatibilityReportFileId(item.getId(), temp3.getId());
                }
                else response.setExtra(temp3.getMessagge());
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

    public ResponseEntity<ManagementPlan> update(ManagementPlan item, MultipartFile resolutionFile, MultipartFile managementPlanFile, MultipartFile compatibilityReportFile) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<ManagementPlan>();
        try {
            Optional<ManagementPlan> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                if (!item.getFlagDraft()) {
                    boolean validate = (item.getInstrumentType() == null || item.getInstrumentType().getId() == 0 || isNullOrEmpty(item.getName())
                            || isNullOrEmpty(item.getAnpCode()) || isNullOrEmpty(item.getResolution())
                            || isNullOrEmpty(item.getManagementPlanFile()));
                    if (validate) {
                        response.setMessage("Se debe completar lo campos obligatorios del plan de manejo.");
                        response.setSuccess(false);
                        response.setWarning(true);
                        return response;
                    }
                }
                item.setResolutionRouteId(_item.get().getResolutionRouteId());
                item.setManagementPlanFileId(_item.get().getManagementPlanFileId());

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
                if (managementPlanFile != null && !managementPlanFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                    DocumentoDTO temp2 = documentService.saveFile(managementPlanFile, false, item.getDigitalRouteId(), item.getManagementPlanFile());
                    if (temp2.getSuccess()){
                        item.setManagementPlanFileId(temp2.getId());
                        _repository.updateManagementPlanFileId(item.getId(), temp2.getId());
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

    public ResponseEntity<ManagementPlan> delete(int id) throws Exception {
        ResponseEntity<ManagementPlan> response = new ResponseEntity<ManagementPlan>();
        try {
            Optional<ManagementPlan> item = _repository.findById(id);
            if (item.isPresent()){
                try {
                    _repository.deleteById(id);
                    response.setMessage("Se elimino el registro correctamente.");
                    //_repository.updateIsDeleted(id, false);
                }
                catch (Exception ex) {
                    if (((ConstraintViolationException) ex.getCause()).getSQLState().equals("23503")) {
                        response.setMessage("No se puede eliminar un plan de manejo mientras es utilizado.");
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
            }
            else{
                response.setMessage("El registro no existe.");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al eliminar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ByteArrayInputStream export(ManagementPlan item) throws Exception {
        String[] columns = { "ANP", "TIPO", "NOMBRE", "RESOLUCION", "RECURSO", "PERIODO", "VIGENTE" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PlanManejo");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        String anpCode = isNullOrEmpty(item.getAnpCode()) ? "" : item.getAnpCode();
        int instrumentTypeId = item.getInstrumentType() == null ? 0 : item.getInstrumentType().getId();
        String name = isNullOrEmpty(item.getName()) ? "" : item.getName();
        String resolution = isNullOrEmpty(item.getResolution()) ? "" : item.getResolution();
        int flagValid = item.getFlagValid() == null ? 2 : (item.getFlagValid() ? 1 : 0);
        int flagDraft = item.getFlagDraft() == null ? 2 : (item.getFlagDraft() ? 1 : 0);
        boolean isDeleted = item.getIsDeleted()== null ? false : item.getIsDeleted();

        List<ManagementPlanDTO> items = this._repository.search2(anpCode, instrumentTypeId, resolution, name, item.getResourceTypeId(), item.getResourceId(), flagValid, flagDraft, isDeleted);

        int initRow = 1;
        for (ManagementPlanDTO _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getAnpName());
            row.createCell(1).setCellValue(_item.getInstrumentTypeName());
            row.createCell(2).setCellValue(_item.getName());
            row.createCell(3).setCellValue(_item.getResolution());
            row.createCell(4).setCellValue(_item.getResources());
            row.createCell(5).setCellValue(_item.getPeriod());
            row.createCell(6).setCellValue(_item.getFlagValidity() ? "Activo" : "Inactivo");
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
