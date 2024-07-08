package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SPGrantingPolygon;
import pe.sernanp.ws_api.model.SPPermittedActivity;
import pe.sernanp.ws_api.repository.SPPermittedActivityRepository;
import pe.sernanp.ws_api.service.SPPermittedActivityService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class SPPermittedActivityServiceImpl extends BaseServiceImpl implements SPPermittedActivityService {
    @Autowired
    SPPermittedActivityRepository _repository;

    public ResponseEntity<SPPermittedActivity> findById(int id) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<SPPermittedActivity>();
        try {
            Optional<SPPermittedActivity> item = _repository.findById(id);
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

    public ResponseEntity<SPPermittedActivity> findBySitePlanId(int sitePlanId) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<SPPermittedActivity>();
        try {
            List<SPPermittedActivity> items = _repository.findBySitePlanIdAndIsDeleted(sitePlanId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SPPermittedActivity> save(SPPermittedActivity item) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<SPPermittedActivity>();
        try {
            String [] grantingPolygonIds = isNullOrEmpty(item.getGrantingPolygonIds()) ? null : item.getGrantingPolygonIds().split(",");
            for (String grantingPolygonId : grantingPolygonIds) {
                SPPermittedActivity _item = new SPPermittedActivity(item);
                if (isInteger(grantingPolygonId)){
                    _item.setGrantingPolygon(new SPGrantingPolygon());
                    _item.getGrantingPolygon().setId(Integer.parseInt(grantingPolygonId));
                    _repository.save(_item);
                }
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

    public ResponseEntity<SPPermittedActivity> update(SPPermittedActivity item) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<SPPermittedActivity>();
        try {
            Optional<SPPermittedActivity> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                SPPermittedActivity _itemUpdate = _repository.save(item);
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

    public ResponseEntity<SPPermittedActivity> delete(int id) throws Exception {
        ResponseEntity<SPPermittedActivity> response = new ResponseEntity<SPPermittedActivity>();
        try {
            Optional<SPPermittedActivity> item = _repository.findById(id);
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

    public ByteArrayInputStream export(int sitePlanId) throws Exception {
        String[] columns = { "ANP", "TIPO ACTIVIDAD", "ACTIVIDAD" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PS_ActivPermitidas");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<SPPermittedActivity> items = this._repository.findBySitePlanIdAndIsDeleted(sitePlanId, false);

        int initRow = 1;
        for (SPPermittedActivity _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getSitePlan() == null ? "" : _item.getSitePlan().getAnpCode());
            row.createCell(1).setCellValue(_item.getActivityType() == null ? "" : _item.getActivityType().getName());
            row.createCell(2).setCellValue(_item.getActivity() == null ? "" : _item.getActivity().getName());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
