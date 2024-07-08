package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.EvaluationResult;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.PdaSectores;
import pe.sernanp.ws_api.model.ProcedureState;
import pe.sernanp.ws_api.repository.EvaluationResultRepository;
import pe.sernanp.ws_api.repository.ProcedureStateRepository;
import pe.sernanp.ws_api.service.EvaluationResultService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationResultServiceImpl  implements EvaluationResultService {

    @Autowired
    EvaluationResultRepository _repository;
    
    @Autowired
    ProcedureStateRepository _repositoryProcedureState;

    @Override
    public ResponseEntity<EvaluationResult> save(EvaluationResult item) throws Exception {
        ResponseEntity<EvaluationResult> response = new ResponseEntity<EvaluationResult>();
        try {
            EvaluationResult _item = _repository.save(item);
            if (_item.getHasData() == true){
                _repositoryProcedureState.update(_item.getProcedure().getId());

                ProcedureState x = new ProcedureState();
                x.setId(0);
                ListDetail type = new ListDetail();
                type.setId(306);
                x.setState(type);
                x.setDate(new Date());
                x.setProcedure(_item.getProcedure());
                x.setActive(true);
                _repositoryProcedureState.save(x);
            }
            response.setItem(_item);
            response.setMessage("Registro exitoso");
            response.setSuccess(true);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<EvaluationResult> update(EvaluationResult item) throws Exception {
        ResponseEntity<EvaluationResult> response = new ResponseEntity<EvaluationResult>();
        try {
            Optional<EvaluationResult> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                EvaluationResult _itemUpdate = _repository.save(item);
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

    @Override
    public ResponseEntity<EvaluationResult> listByTramite(int id) throws Exception {
        ResponseEntity<EvaluationResult> response = new ResponseEntity<EvaluationResult>();
        try {
            EvaluationResult item = _repository.listbytramite(id);
            response.setItem(item);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
    public ByteArrayInputStream export(int procedureId) throws Exception {
        String[] columns = { "Contenido", "", "", "N°", "Criterios de evaluación", "Resultado", "Comentario"};

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Hoja 1");
        Row row = sheet.createRow(0);
//        Row row2 = sheet.createRow(1);
//
//        row.setRowStyle(workbook.createCellStyle());
//        row2.setRowStyle(workbook.createCellStyle());
//        row.getRowStyle().setWrapText(true);
//        row.getRowStyle().setAlignment(HorizontalAlignment.CENTER);
//        row.getRowStyle().setVerticalAlignment(VerticalAlignment.CENTER);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
//            cell.setCellStyle(row.getRowStyle());
//            else {
//                Cell cell2 = row2.createCell(i);
//                cell2.setCellValue(columns[i]);
//                cell2.setCellStyle(row.getRowStyle());;
//            }
        }
//        Cell cell = row.createCell(2);
//        cell.setCellValue("AÑOS");
//        cell.setCellStyle(row.getRowStyle());
//
//        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A2"));
//        sheet.addMergedRegion(CellRangeAddress.valueOf("B1:B2"));
//        sheet.addMergedRegion(CellRangeAddress.valueOf("C1:F1"));

        EvaluationResult item = _repository.listbytramite(procedureId);

        row = sheet.createRow(1);
        row.createCell(0).setCellValue("");
        row.createCell(1).setCellValue("");

        int initRow = 1;
//        for (PdaSectores _item : itemsResult) {
//            row = sheet.createRow(initRow);
//            row.createCell(0).setCellValue(_item.getActivity() == null ? "" : _item.getActivity().getName());
//            row.createCell(1).setCellValue(_item.getSector() == null ? "" : _item.getSector().getName());
//            initRow++;
//        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
