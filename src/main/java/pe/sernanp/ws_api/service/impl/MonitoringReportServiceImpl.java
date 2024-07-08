package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.dto.MonitoringReportDTO;
import pe.sernanp.ws_api.dto.SupervisionReportDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MonitoringRecord;
import pe.sernanp.ws_api.model.MonitoringReport;
import pe.sernanp.ws_api.model.OD;
import pe.sernanp.ws_api.model.SupervisionReport;
import pe.sernanp.ws_api.repository.MonitoringRecordRepository;
import pe.sernanp.ws_api.repository.MonitoringReportRepository;
import pe.sernanp.ws_api.service.MonitoringReportService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class MonitoringReportServiceImpl extends BaseServiceImpl implements MonitoringReportService {
    @Autowired
    MonitoringReportRepository _repository;
    @Autowired
    MonitoringRecordRepository _repositoryMonitoringRecord;

    public ResponseEntity<MonitoringReportDTO> search(MonitoringReport item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<MonitoringReportDTO> response = new ResponseEntity<MonitoringReportDTO>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());

            String anpCodes = isNullOrEmpty(item.getOd().getAnpConfigIds()) ? "" : item.getOd().getAnpConfigIds();
            int typeId = item.getType() == null ? 0 : item.getType().getId();
            String odCode = isNullOrEmpty(item.getOd().getCode()) ? "" : item.getOd().getCode();
            String startDate = item.getOd().getStartDate() == null ? "1500-01-01" : item.getOd().getStartDate().toString();
            String endDate = item.getOd().getEndDate() == null ? "2999-01-01" : item.getOd().getEndDate().toString();
//            String recordCode = isNullOrEmpty(item.getRecordCode()) ? "" : item.getRecordCode();
            int beneficiaryId = item.getOd().getBeneficiary() == null ? 0 : item.getOd().getBeneficiary().getId();
            String reportNumber = isNullOrEmpty(item.getReportNumber()) ? "" : item.getReportNumber();

            Page<MonitoringReportDTO> pag = this._repository.search(anpCodes, typeId, odCode, startDate, endDate, beneficiaryId, reportNumber, page);
            List<MonitoringReportDTO> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());
            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar resultados.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MonitoringReport> findAll() throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<MonitoringReport>();
        try {
            List<MonitoringReport> items = _repository.findAll();
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MonitoringReport> findById(int id) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<MonitoringReport>();
        try {
            Optional<MonitoringReport> item = _repository.findById(id);
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

    public ResponseEntity<MonitoringRecord> listMonitoringRecordById(int id) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<MonitoringRecord>();
        try {
            List<MonitoringRecord> items = _repositoryMonitoringRecord.findByMonitoringReportId(id);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MonitoringReport> save(MonitoringReport item, MultipartFile documentReport) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<MonitoringReport>();
        try {
            MonitoringReport _item;
            // Turismo
            if (item.getOd() != null && item.getOd().getId() != 0) {
                item.setResource(null);
                item.setResourceType(null);
                String monitoringRecordIds = _repositoryMonitoringRecord.getIdsByOdId(item.getOd().getId());
                if (isNullOrEmpty(monitoringRecordIds)) {
                    response.setMessage("El otorgamiento no cuenta con actas.");
                    response.setSuccess(false);
                    return response;
                }
                item.setMonitoringRecordIds(monitoringRecordIds);
                _item = _repository.save(item);
            // recurso
            } else if ((item.getResourceType() != null && item.getResourceType().getId() != 0) && (item.getResource() != null && item.getResource().getId() != 0) && !isNullOrEmpty(item.getMonitoringRecordIds())){
                item.setOd(null);
                _item = _repository.save(item);
            } else{
                response.setMessage("Debe enviar todos los campos obligatorios.");
                response.setSuccess(false);
                return response;
            }

            //if (documentReport != null && !documentReport.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
            if (documentReport != null && !documentReport.isEmpty()) {
                DocumentoDTO temp = documentService.saveFile(documentReport, true, "item.getDigitalRouteId()", item.getDocumentReportName());
                if (temp.getSuccess()){
                    _item.setDocumentReportId(temp.getId());
                    _repository.updateDocumentReporId(_item.getId(), temp.getId());
                }
                else response.setExtra(temp.getMessagge());
            }

            _repository.calculateEvaluation(_item.getId());

            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MonitoringReport> update(MonitoringReport item, MultipartFile documentReport) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<MonitoringReport>();
        try {
            Optional<MonitoringReport> _item = _repository.findById(item.getId());
            MonitoringReport _itemUpdate;
            if (_item.isPresent()) {
                // Turismo
                if (item.getOd() != null && item.getOd().getId() != 0) {
                    item.setResource(null);
                    item.setResourceType(null);
                    String monitoringRecordIds = _repositoryMonitoringRecord.getIdsByOdId(item.getOd().getId());
                    if (isNullOrEmpty(monitoringRecordIds)) {
                        response.setMessage("El otorgamiento no cuenta con actas.");
                        response.setSuccess(false);
                        return response;
                    }
                    item.setMonitoringRecordIds(monitoringRecordIds);
                    _itemUpdate = _repository.save(item);
                    // recurso
                } else if ((item.getResourceType() != null && item.getResourceType().getId() != 0) && (item.getResource() != null && item.getResource().getId() != 0) && !isNullOrEmpty(item.getMonitoringRecordIds())){
                    item.setOd(null);
                    _itemUpdate = _repository.save(item);
                } else{
                    response.setMessage("Debe enviar todos los campos obligatorios.");
                    response.setSuccess(false);
                    return response;
                }

                //if (documentReport != null && !documentReport.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                if (documentReport != null && !documentReport.isEmpty()) {
                    DocumentoDTO temp = documentService.saveFile(documentReport, true, "item.getDigitalRouteId()", item.getDocumentReportName());
                    if (temp.getSuccess()){
                        _itemUpdate.setDocumentReportId(temp.getId());
                        _repository.updateDocumentReporId(_itemUpdate.getId(), temp.getId());
                    }
                    else response.setExtra(temp.getMessagge());
                }

                _repository.calculateEvaluation(item.getId());

                response.setItem(_itemUpdate);
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

    public ResponseEntity<MonitoringReport> updateConclusion(MonitoringReport item) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<MonitoringReport>();
        try {
            Optional<MonitoringReport> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                _repository.updateConclusion(item.getId(), item.getRecommendations(), item.getAdditionalRecommendations(), item.getConclusions());
                _repository.calculateEvaluation(item.getId());
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

    public ResponseEntity<MonitoringReport> delete(int id) throws Exception {
        ResponseEntity<MonitoringReport> response = new ResponseEntity<MonitoringReport>();
        try {
            Optional<MonitoringReport> item = _repository.findById(id);
            if (item.isPresent()){
                _repository.deleteById(id);
                response.setMessage("Se elimino el registro correctamente.");
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

    public ByteArrayInputStream export(MonitoringReport item) throws Exception {
        String[] columns = { "TIPO", "INFORME", "N° ACTAS", "N° OTORGAMIENTOS", "N° OBLIGACIONES", "N° OBLIGACIONES CUMPLIDAS" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("RS_Obligaciones");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        String anpCodes = isNullOrEmpty(item.getOd().getAnpConfigIds()) ? "" : item.getOd().getAnpConfigIds();
        int typeId = item.getType() == null ? 0 : item.getType().getId();
        String odCode = isNullOrEmpty(item.getOd().getCode()) ? "" : item.getOd().getCode();
        String startDate = item.getOd().getStartDate() == null ? "1500-01-01" : item.getOd().getStartDate().toString();
        String endDate = item.getOd().getEndDate() == null ? "2999-01-01" : item.getOd().getEndDate().toString();
//        String recordCode = isNullOrEmpty(item.getRecordCode()) ? "" : item.getRecordCode();
        int beneficiaryId = item.getOd().getBeneficiary() == null ? 0 : item.getOd().getBeneficiary().getId();
        String reportNumber = isNullOrEmpty(item.getReportNumber()) ? "" : item.getReportNumber();

        List<MonitoringReportDTO> items = this._repository.search(anpCodes, typeId, odCode, startDate, endDate, beneficiaryId, reportNumber);

        int initRow = 1;
        for (MonitoringReportDTO _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getTypeName());
            row.createCell(1).setCellValue(_item.getReportNumber());
            row.createCell(2).setCellValue(_item.getRecordsTotal());
            row.createCell(3).setCellValue(_item.getOdsTotal());
            row.createCell(4).setCellValue(_item.getObligationsTotal());
            row.createCell(5).setCellValue(_item.getObligationsFulfilled());
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
        } catch (Exception ex) {
            return "";
        }
    }
}
