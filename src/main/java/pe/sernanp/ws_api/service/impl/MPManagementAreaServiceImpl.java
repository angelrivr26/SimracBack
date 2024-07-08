package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.ManagementPlanDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MPManagementArea;
import pe.sernanp.ws_api.model.ManagementPlan;
import pe.sernanp.ws_api.repository.MPManagementAreaRepository;
import pe.sernanp.ws_api.service.MPManagementAreaService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class MPManagementAreaServiceImpl implements MPManagementAreaService {
    @Autowired
    MPManagementAreaRepository _repository;

    public ResponseEntity<MPManagementArea> findById(int id) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<MPManagementArea>();
        try {
            Optional<MPManagementArea> item = _repository.findById(id);
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

    public ResponseEntity<MPManagementArea> findByManagementPlanId(int managementPlanId) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<MPManagementArea>();
        try {
            List<MPManagementArea> items = _repository.findByManagementPlanIdAndIsDeleted(managementPlanId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MPManagementArea> save(MPManagementArea item) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<MPManagementArea>();
        try {
            item.setModality(item.getModality() != null ? (item.getModality().getId() == 0 ? null : item.getModality()) : item.getModality());
            MPManagementArea _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MPManagementArea> update(MPManagementArea item) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<MPManagementArea>();
        try {
            Optional<MPManagementArea> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item.setModality(item.getModality() != null ? (item.getModality().getId() == 0 ? null : item.getModality()) : item.getModality());
                MPManagementArea _itemUpdate = _repository.save(item);
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

    public ResponseEntity<MPManagementArea> delete(int id) throws Exception {
        ResponseEntity<MPManagementArea> response = new ResponseEntity<MPManagementArea>();
        try {
            Optional<MPManagementArea> item = _repository.findById(id);
            if (item.isPresent()){
                _repository.deleteById(id);
                //_repository.updateIsDeleted(id, true);
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

    public ByteArrayInputStream export(int managementPlanId) throws Exception {
        String[] columns = { "ANP", "CODIGO", "AREA DE MANEJO", "HECTAREAS" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PM_AreaManejo");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<MPManagementArea> items = this._repository.findByManagementPlanIdAndIsDeleted(managementPlanId, false);

        int initRow = 1;
        for (MPManagementArea _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getManagementPlan() == null ? "" : _item.getManagementPlan().getAnpCode());
            row.createCell(1).setCellValue(_item.getCode());
            row.createCell(2).setCellValue(_item.getName());
            row.createCell(3).setCellValue(_item.getHectares());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }

}
