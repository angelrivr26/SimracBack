package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.MRCompetitor;
import pe.sernanp.ws_api.repository.MRCompetitorRepository;
import pe.sernanp.ws_api.service.MRCompetitorService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class MRCompetitorServiceImpl implements MRCompetitorService {
    @Autowired
    MRCompetitorRepository _repository;


    @Override
    public ResponseEntity<MRCompetitor> detail(int id) {
        ResponseEntity<MRCompetitor> response = new ResponseEntity<>();
        try{
            Optional<MRCompetitor> itemOptional = this._repository.findById(id);
            if(itemOptional.isPresent()){
                response.setItem(itemOptional.get());
            }else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Ocurrio un error al buscar el registro");
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<MRCompetitor> delete(int id) {
        ResponseEntity<MRCompetitor> response = new ResponseEntity<MRCompetitor>();
        try {
            Optional<MRCompetitor> item = this._repository.findById(id);
            if (item.isPresent()){
                this._repository.deleteById(id);
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

    @Override
    public ResponseEntity<MRCompetitor> save(MRCompetitor item) {
        ResponseEntity<MRCompetitor> response = new ResponseEntity<MRCompetitor>();
        try {
            MRCompetitor _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<MRCompetitor> update(MRCompetitor item) {
        ResponseEntity<MRCompetitor> response = new ResponseEntity<MRCompetitor>();
        try {
            Optional<MRCompetitor> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                MRCompetitor _itemUpdate = _repository.save(item);
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
    public ResponseEntity<MRCompetitor> findByMonitoringRecordId(int monitoringRecordId) {
        ResponseEntity<MRCompetitor> response = new ResponseEntity<MRCompetitor>();
        try {
            List<MRCompetitor> items = _repository.findByMonitoringRecordId(monitoringRecordId);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ByteArrayInputStream export(int supervisionRecordId) throws Exception {
        String[] columns = { "N°", "TIPO PARTICIPANTE", "¿ES PERSONAL?", "N° DOCUMENTO", "NOMBRES Y APELLIDOS" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Acta_Super_Participantes");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        List<MRCompetitor> items = _repository.findByMonitoringRecordId(supervisionRecordId);

        int initRow = 1;
        for (MRCompetitor _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getCompiterNumber());
            row.createCell(1).setCellValue(_item.getCompiterType() == null ? "" : _item.getCompiterType().getName());
            row.createCell(2).setCellValue(_item.isFlagStaff() ? "Si" : "No");
            row.createCell(3).setCellValue(_item.getDocumentNumber());
            row.createCell(4).setCellValue(_item.getFullName());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }

}
