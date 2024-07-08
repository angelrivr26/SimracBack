package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MPResource;
import pe.sernanp.ws_api.repository.MPResourceRepository;
import pe.sernanp.ws_api.service.MPResourceService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class MPResourceServiceImpl implements MPResourceService {
    @Autowired
    MPResourceRepository _repository;

    public ResponseEntity<MPResource> findById(int id) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<MPResource>();
        try {
            Optional<MPResource> item = _repository.findById(id);
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

    public ResponseEntity<MPResource> findByManagementPlanId(int managementPlanId) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<MPResource>();
        try {
            List<MPResource> items = _repository.findByManagementPlanIdAndIsDeleted(managementPlanId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MPResource> save(MPResource item) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<MPResource>();
        try {
            MPResource _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MPResource> update(MPResource item) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<MPResource>();
        try {
            Optional<MPResource> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                MPResource _itemUpdate = _repository.save(item);
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

    public ResponseEntity<MPResource> delete(int id) throws Exception {
        ResponseEntity<MPResource> response = new ResponseEntity<MPResource>();
        try {
            Optional<MPResource> item = _repository.findById(id);
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

        List<MPResource> items = this._repository.findByManagementPlanIdAndIsDeleted(managementPlanId, false);

        int initRow = 1;
        for (MPResource _item : items) {
            row = sheet.createRow(initRow);
//            row.createCell(0).setCellValue(_item.getManagementPlan() == null ? "" : _item.getManagementPlan().getAnpCode());
//            row.createCell(1).setCellValue(_item.getCode());
//            row.createCell(2).setCellValue(_item.getName());
//            row.createCell(3).setCellValue(_item.getHectares());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
