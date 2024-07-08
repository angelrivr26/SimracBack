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
import pe.sernanp.ws_api.dto.SupervisionReportDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.*;
import pe.sernanp.ws_api.repository.MatrizObligationRepository;
import pe.sernanp.ws_api.repository.SRFiscalObligationRepository;
import pe.sernanp.ws_api.repository.SupervisionRecordRepository;
import pe.sernanp.ws_api.repository.SupervisionReportRepository;
import pe.sernanp.ws_api.service.SupervisionReportService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SupervisionReportServiceImpl extends BaseServiceImpl implements SupervisionReportService {
    @Autowired
    SupervisionReportRepository _repository;
    @Autowired
    SRFiscalObligationRepository _repositorySRFiscalObligation;
    @Autowired
    SupervisionRecordRepository _repositorySupervisionRecord;
    @Autowired
    MatrizObligationRepository _repositoryMatrizObligation;


    public ResponseEntity<SupervisionReportDTO> search(SupervisionReport item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<SupervisionReportDTO> response = new ResponseEntity<SupervisionReportDTO>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            OD itemOd = item.getSupervisionRecord() == null ? new OD() : item.getSupervisionRecord().getOd();

            String anpCodes = isNullOrEmpty(itemOd.getAnpConfigIds()) ? "" : itemOd.getAnpConfigIds();
            int typeId = itemOd.getType() == null ? 0 : itemOd.getType().getId();
            String odCode = isNullOrEmpty(itemOd.getCode()) ? "" : itemOd.getCode();
            int supervisionType = item.getSupervisionRecord().getFlagRegular() == null ? 2 : item.getSupervisionRecord().getFlagRegular() ? 1 : 0;
            String startDate = item.getSupervisionRecord().getOpeningDate() == null ? "1500-01-01" : item.getSupervisionRecord().getOpeningDate().toString();
            String endDate = item.getSupervisionRecord().getClosingDate() == null ? "2999-01-01" : item.getSupervisionRecord().getClosingDate().toString();
            String recordCode = isNullOrEmpty(item.getRecordCode()) ? "" : item.getRecordCode();
            int beneficiaryId = itemOd.getBeneficiary() == null ? 0 : itemOd.getBeneficiary().getId();
            String reportNumber = isNullOrEmpty(item.getReportNumber()) ? "" : item.getReportNumber();

            Page<SupervisionReportDTO> pag = this._repository.search(anpCodes, typeId, odCode, supervisionType, startDate, endDate, recordCode, beneficiaryId, reportNumber, page);
            List<SupervisionReportDTO> items = pag.getContent();
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

    public ResponseEntity<SupervisionReport> findAll() throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<SupervisionReport>();
        try {
            List<SupervisionReport> items = _repository.findAll();
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SupervisionReport> findById(int id) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<SupervisionReport>();
        try {
            Optional<SupervisionReport> item = _repository.findById(id);
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

    public ResponseEntity<SupervisionReport> calculateEvaluation(int id) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<SupervisionReport>();
        try {
            _repository.calculateEvaluation(id, 228);
            Optional<SupervisionReport> item = _repository.findById(id);
            if (item.isPresent()){
                response.setItem(item.get());
                response.setMessage("Información actualizada correctamente.");
            }
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

    public ResponseEntity<SupervisionReport> save(SupervisionReport item, MultipartFile documentReport) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<SupervisionReport>();
        try {

            Optional<SupervisionRecord> itemSupervisionRecord = _repositorySupervisionRecord.findById(item.getSupervisionRecord().getId());
            if (itemSupervisionRecord.isPresent()) {
                int odId = itemSupervisionRecord.get().getOd().getId();
                Integer matrizObligationId = _repositoryMatrizObligation.findByOdIdAndFileNumber(odId, item.getRecordCode());

                if (matrizObligationId != null && matrizObligationId > 0) {
                    item.setMatrizObligationId(matrizObligationId);
                    SupervisionReport _item = _repository.save(item);

                    _repositorySRFiscalObligation.generateMassive(_item.getId(), matrizObligationId, itemSupervisionRecord.get().getId());

                    //if (documentReport != null && !documentReport.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                    if (documentReport != null && !documentReport.isEmpty()) {
                        DocumentoDTO temp = documentService.saveFile(documentReport, true, "item.getDigitalRouteId()", item.getDocumentReportName());
                        if (temp.getSuccess()){
                            _item.setDocumentReportId(temp.getId());
                            _repository.updateDocumentReporId(_item.getId(), temp.getId());
                        }
                        else response.setExtra(temp.getMessagge());
                    }

                    response.setItem(_item);
                    response.setMessage("Registro exitoso");
                } else {
                    response.setMessage("El otrogamiento relacionado al acta no cuenta con una matriz de obligaciones fiscales.");
                    response.setSuccess(false);
                }
            } else {
                response.setMessage("El acta seleccionada no existe.");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SupervisionReport> update(SupervisionReport item, MultipartFile documentReport) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<SupervisionReport>();
        try {
            Optional<SupervisionReport> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                Optional<SupervisionRecord> itemSupervisionRecord = _repositorySupervisionRecord.findById(item.getSupervisionRecord().getId());
                if (itemSupervisionRecord.isPresent()) {
                    int odId = itemSupervisionRecord.get().getOd().getId();
                    Integer itemMatrizObligationId = _repositoryMatrizObligation.findByOdIdAndFileNumber(odId, item.getRecordCode());

                    if (itemMatrizObligationId != null && itemMatrizObligationId > 0) {
                        item.setMatrizObligationId(itemMatrizObligationId);
                        SupervisionReport _itemUpdate = _repository.save(item);

                        //if (documentReport != null && !documentReport.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                        if (documentReport != null && !documentReport.isEmpty()) {
                            DocumentoDTO temp = documentService.saveFile(documentReport, true, "item.getDigitalRouteId()", item.getDocumentReportName());
                            if (temp.getSuccess()){
                                _itemUpdate.setDocumentReportId(temp.getId());
                                _repository.updateDocumentReporId(_itemUpdate.getId(), temp.getId());
                            }
                            else response.setExtra(temp.getMessagge());
                        }

                        response.setItem(_itemUpdate);
                        response.setMessage("Se actualizó el registro correctamente");
                    } else {
                        response.setMessage("El otrogamiento relacionado al acta no cuenta con una matriz de obligaciones fiscales.");
                        response.setSuccess(false);
                    }
                } else {
                    response.setMessage("El acta seleccionada no existe.");
                    response.setSuccess(false);
                }
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

    public ResponseEntity<SupervisionReport> delete(int id) throws Exception {
        ResponseEntity<SupervisionReport> response = new ResponseEntity<SupervisionReport>();
        try {
            Optional<SupervisionReport> item = _repository.findById(id);
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

    public ByteArrayInputStream export(SupervisionReport item) throws Exception {
        String[] columns = { "TIPO", "ANP", "INFORME", "OTORGAMIENTO", "TITULO", "BENEFICIARIO", "FECHA APERTURA", "ESTADO", "OBLIGACIONES" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("IS_Obligaciones");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        OD itemOd = item.getSupervisionRecord() == null ? new OD() : item.getSupervisionRecord().getOd();

        String anpCodes = isNullOrEmpty(itemOd.getAnpConfigIds()) ? "" : itemOd.getAnpConfigIds();
        int typeId = itemOd.getType() == null ? 0 : itemOd.getType().getId();
        String odCode = isNullOrEmpty(itemOd.getCode()) ? "" : itemOd.getCode();
        int supervisionType = item.getSupervisionRecord().getFlagRegular() == null ? 2 : item.getSupervisionRecord().getFlagRegular() ? 1 : 0;
        String startDate = item.getSupervisionRecord().getOpeningDate() == null ? "1500-01-01" : item.getSupervisionRecord().getOpeningDate().toString();
        String endDate = item.getSupervisionRecord().getClosingDate() == null ? "2999-01-01" : item.getSupervisionRecord().getClosingDate().toString();
        String recordCode = isNullOrEmpty(item.getRecordCode()) ? "" : item.getRecordCode();
        int beneficiaryId = itemOd.getBeneficiary() == null ? 0 : itemOd.getBeneficiary().getId();
        String reportNumber = isNullOrEmpty(item.getReportNumber()) ? "" : item.getReportNumber();

        List<SupervisionReportDTO> items = this._repository.search(anpCodes, typeId, odCode, supervisionType, startDate, endDate, recordCode, beneficiaryId, reportNumber);

        int initRow = 1;
        for (SupervisionReportDTO _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getTypeName());
            row.createCell(1).setCellValue(_item.getAnpNames());
            row.createCell(2).setCellValue(_item.getReportNumber());
            row.createCell(3).setCellValue(_item.getOdCode());
            row.createCell(4).setCellValue(_item.getTitleEnables());
            row.createCell(5).setCellValue(_item.getBeneficiaryName());
            row.createCell(6).setCellValue(_item.getOpeningDate());
            row.createCell(7).setCellValue(_item.getStateName());
            row.createCell(8).setCellValue(_item.getObligationCount());
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
