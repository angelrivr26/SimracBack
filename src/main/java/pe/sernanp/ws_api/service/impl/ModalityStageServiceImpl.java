package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Modality;
import pe.sernanp.ws_api.model.ModalityStage;
import pe.sernanp.ws_api.repository.ModalityStageRepository;
import pe.sernanp.ws_api.service.ModalityStageService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ModalityStageServiceImpl implements ModalityStageService {
    @Autowired
    ModalityStageRepository _repository;

    public ResponseEntity<ModalityStage> findById(int id) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<ModalityStage>();
        try {
            Optional<ModalityStage> item = _repository.findById(id);
            if (item.isPresent())
                response.setItem(item.get());
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ModalityStage> findByModalityId(int modalityId) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<ModalityStage>();
        try {
            List<ModalityStage> items = _repository.findByModalityIdAndIsDeleted(modalityId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ModalityStage> save(ModalityStage item) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<ModalityStage>();
        try {
            ModalityStage _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ModalityStage> update(ModalityStage item) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<ModalityStage>();
        try {
            Optional<ModalityStage> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                ModalityStage _itemUpdate = _repository.save(item);
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

    public ResponseEntity<ModalityStage> delete(int id) throws Exception {
        ResponseEntity<ModalityStage> response = new ResponseEntity<ModalityStage>();
        try {
            Optional<ModalityStage> item = _repository.findById(id);
            if (item.isPresent()){
                //_repository.deleteById(id);
                _repository.updateIsDeleted(id, true);
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

    public ByteArrayInputStream export(int modalityId) throws Exception {
        String[] columns = { "ORDEN", "DESCRIPCION", "OBLIGATORIO", "PLAZO (D)", "ESTADO" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Modalidad_Etapas");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<ModalityStage> items = _repository.findByModalityIdAndIsDeleted(modalityId, false);

        int initRow = 1;
        for (ModalityStage _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getOrder());
            row.createCell(1).setCellValue(_item.getName());
            row.createCell(2).setCellValue(_item.getFlagActive() ? "Si" : "No");
            row.createCell(3).setCellValue(_item.getTerm());
            row.createCell(4).setCellValue((_item.getState() == null ? "" : _item.getState().getName()));
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
