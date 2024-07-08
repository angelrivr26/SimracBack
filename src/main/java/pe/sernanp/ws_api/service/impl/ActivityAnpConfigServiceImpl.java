package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ActivityAnpConfig;
import pe.sernanp.ws_api.repository.ActivityAnpConfigRepository;
import pe.sernanp.ws_api.service.ActivityAnpConfigService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityAnpConfigServiceImpl extends BaseServiceImpl implements ActivityAnpConfigService {
    @Autowired
    ActivityAnpConfigRepository _repository;

    public ResponseEntity<ActivityAnpConfig> findById(int id) throws Exception {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<ActivityAnpConfig>();
        try {
            Optional<ActivityAnpConfig> item = _repository.findById(id);
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

    public ResponseEntity<ActivityAnpConfig> findByAnpConfig(String anpConfigIds) throws Exception {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<ActivityAnpConfig>();
        try {
            String stringAnpConfigIds = trimEnd(trimStart(anpConfigIds, ","), ",");
            String temp = stringAnpConfigIds.replace(",", "");

            if (stringAnpConfigIds.length() == 0 || !isInteger(temp.trim())) {
                response.setMessage("Ocurrio un error al listar");
                response.setSuccess(false);
                response.setWarning(true);
                response.setExtra("Los id's del anp deben ser numericos y serparador por coma (',') y no vacio");
                return response;
            }
            int[] _anpConfigIds = Arrays.stream(stringAnpConfigIds.split(",")).mapToInt(Integer::parseInt).toArray();
            List<ActivityAnpConfig> items = _repository.findByAnpConfigIds(_anpConfigIds, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ActivityAnpConfig> save(ActivityAnpConfig item) throws Exception, ConstraintViolationException {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<ActivityAnpConfig>();
        ActivityAnpConfig _item = null;
        try {
            _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");
        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe una configuración de actividad parecida registrada.");
                response.setSuccess(false);
                response.setWarning(true);
                return response;
            } else {
                response.setMessage("Ocurrio un error al guardar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<ActivityAnpConfig> update(ActivityAnpConfig item) throws Exception {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<ActivityAnpConfig>();
        try {
            Optional<ActivityAnpConfig> _item = _repository.findById(item.getId());
            ActivityAnpConfig _itemUpdate = null;
            if (_item.isPresent()) {
                _itemUpdate = _repository.save(item);
                response.setItem(_itemUpdate);
                response.setMessage("Se actualizó el registro correctamente");
            }
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe una configuración de actividad parecida registrada.");
                response.setSuccess(false);
                response.setWarning(true);
                return response;
            } else {
                response.setMessage("Ocurrio un error al actualizar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<ActivityAnpConfig> delete(int id) throws Exception {
        ResponseEntity<ActivityAnpConfig> response = new ResponseEntity<ActivityAnpConfig>();
        try {
            Optional<ActivityAnpConfig> item = _repository.findById(id);
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

    public ResponseEntity<ListDTO> listActivityByTypeAndAnpConfig(ActivityAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        try {
            String anpCodes = item.getAnpConfig() == null ? "" : item.getAnpConfig().getCode();
            String stringAnpCodes = trimEnd(trimStart(anpCodes, ","), ",");
            String temp = stringAnpCodes.replace(",", "");
            String[] _anpCodes = !isNullOrEmpty(temp) ? anpCodes.split(",") : new String[] {""};

            String sectorCodes = item.getAnpConfig() == null ? "" : isNullOrEmpty(item.getAnpConfig().getSectorCode()) ? "" : item.getAnpConfig().getSectorCode();
//            String stringSectorCodes = trimEnd(trimStart(sectorCodes, ","), ",");
//            String temp2 = stringSectorCodes.replace(",", "");
//            String[] _sectorCodes = !isNullOrEmpty(temp2) ? sectorCodes.split(",") : new String[] {""};

            String polygonCodes = item.getAnpConfig() == null ? "" : isNullOrEmpty(item.getAnpConfig().getPolygonCode()) ? "" : item.getAnpConfig().getPolygonCode();
//            String stringPolygonCodes = trimEnd(trimStart(polygonCodes, ","), ",");
//            String temp3 = stringPolygonCodes.replace(",", "");
//            String[] _polygonCodes = !isNullOrEmpty(temp3) ? polygonCodes.split(",") : new String[] {""};

            int typeId = item.getAnpConfig() == null ? 0 : item.getAnpConfig().getType() == null ? 0 : item.getAnpConfig().getType().getId();
            int activityTypeId = item.getActivityType() == null ? 0 : item.getActivityType().getId();

            List<ListDTO> items = _repository.listActivityByTypeAndAnpConfig(typeId, _anpCodes, sectorCodes, polygonCodes, activityTypeId);
            response.setItems(items);
            if (items.size() == 0)
                response.setMessage("No se encontraron actividades configuradas.");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar las actividades");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDTO> listActivityTypeByAnpConfig(ActivityAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        try {
            String anpCodes = item.getAnpConfig() == null ? "" : item.getAnpConfig().getCode();
            String stringAnpCodes = trimEnd(trimStart(anpCodes, ","), ",");
            String temp = stringAnpCodes.replace(",", "");
            String[] _anpCodes = !isNullOrEmpty(temp) ? anpCodes.split(",") : new String[] {""};

            String sectorCodes = item.getAnpConfig() == null ? "" : isNullOrEmpty(item.getAnpConfig().getSectorCode()) ? "" : item.getAnpConfig().getSectorCode();
            String polygonCodes = item.getAnpConfig() == null ? "" : isNullOrEmpty(item.getAnpConfig().getPolygonCode()) ? "" : item.getAnpConfig().getPolygonCode();

            int typeId = item.getAnpConfig() == null ? 0 : item.getAnpConfig().getType() == null ? 0 : item.getAnpConfig().getType().getId();

            List<ListDTO> items = _repository.listActivityTypeByAnpConfig(typeId, _anpCodes, sectorCodes, polygonCodes);
            response.setItems(items);
            if (items.size() == 0)
                response.setMessage("No se encontraron actividades configuradas.");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar las actividades");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ByteArrayInputStream export(int anpConfigId) throws Exception {
        String[] columns = { "TIPO", "ANP", "TIPO ACTIVIDAD", "ACTIVIDAD" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Actividad_Anp");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        int [] id = new int[] {anpConfigId};
        List<ActivityAnpConfig> items = _repository.findByAnpConfigIds(id, false);

        int initRow = 1;
        for (ActivityAnpConfig _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getAnpConfig() == null ? "" : _item.getAnpConfig().getType() == null ? "" : _item.getAnpConfig().getType().getName());
            row.createCell(1).setCellValue(_item.getAnpConfig() == null ? "" : _item.getAnpConfig().getName());
            row.createCell(2).setCellValue(_item.getActivityType() == null ? "" : _item.getActivityType().getName());
            row.createCell(3).setCellValue(_item.getActivity() == null ? "" : _item.getActivity().getName());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
