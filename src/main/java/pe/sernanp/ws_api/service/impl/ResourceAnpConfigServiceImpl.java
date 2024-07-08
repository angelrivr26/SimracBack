package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ResourceAnpConfig;
import pe.sernanp.ws_api.repository.ResourceAnpConfigRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.ResourceAnpConfigService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceAnpConfigServiceImpl extends BaseServiceImpl implements ResourceAnpConfigService {
    @Autowired
    ResourceAnpConfigRepository _repository;

    public ResponseEntity<ResourceAnpConfig> findById(int id) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<ResourceAnpConfig>();
        try {
            Optional<ResourceAnpConfig> item = _repository.findById(id);
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

    public ResponseEntity<ResourceAnpConfig> findByAnpConfig(String anpConfigIds) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<ResourceAnpConfig>();
        try {
            String stringAnpConfigIds = trimEnd(trimStart(anpConfigIds, ","), ",");
            String temp = stringAnpConfigIds.replace(",", "");

            if (stringAnpConfigIds.length() == 0 || !isInteger(temp.trim())) {
                response.setMessage("Ocurrio un error al listar");
                response.setSuccess(false);
                response.setExtra("Los id's del anp deben ser numericos y serparador por coma (',') y no vacio");
                return response;
            }
            int[] _anpConfigIds = Arrays.stream(stringAnpConfigIds.split(",")).mapToInt(Integer::parseInt).toArray();
            List<ResourceAnpConfig> items = _repository.findByAnpConfigIds(_anpConfigIds, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ResourceAnpConfig> findByAnpConfigCode(String anpConfigIds) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<ResourceAnpConfig>();
        try {
            String stringAnpConfigIds = trimEnd(trimStart(anpConfigIds, ","), ",");
            String temp = stringAnpConfigIds.replace(",", "");

            if (isNullOrEmpty(stringAnpConfigIds)) {
                response.setMessage("Ocurrio un error al listar");
                response.setSuccess(false);
                response.setExtra("'el anpConfigIds deben ser los codigos serparados por coma (',') y no vacio");
                return response;
            }
            String[] _anpConfigCodes = stringAnpConfigIds.split(",");//Arrays.stream(stringAnpConfigIds.split(",")).mapToInt(Integer::parseInt).toArray();
            List<ResourceAnpConfig> items = _repository.findByAnpConfigCode(_anpConfigCodes, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDTO> listByAnpConfig(ResourceAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<ListDTO>();
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

            List<ListDTO> items = _repository.findByAnpConfig(typeId, _anpCodes, sectorCodes, polygonCodes);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ResourceAnpConfig> save(ResourceAnpConfig item) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<ResourceAnpConfig>();
        try {
            item = _repository.save(item);
            response.setItem(item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe una configuración de modalidad parecida registrada.");
                response.setSuccess(false);
                response.setWarning(true);
            } else {
                response.setMessage("Ocurrio un error al guardar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<ResourceAnpConfig> update(ResourceAnpConfig item) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<ResourceAnpConfig>();
        try {
            Optional<ResourceAnpConfig> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item = _repository.save(item);
                response.setItem(item);
                response.setMessage("Se actualizó el registro correctamente");
            }
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe una configuración de modalidad parecida registrada.");
                response.setSuccess(false);
                response.setWarning(true);
            } else {
                response.setMessage("Ocurrio un error al guardar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<ResourceAnpConfig> delete(int id) throws Exception {
        ResponseEntity<ResourceAnpConfig> response = new ResponseEntity<ResourceAnpConfig>();
        try {
            Optional<ResourceAnpConfig> item = _repository.findById(id);
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

    public ByteArrayInputStream export(int anpConfigId) throws Exception {
        String[] columns = { "TIPO", "ANP", "TIPO RECURSO", "RECURSO" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Recurso_Anp");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        int [] id = new int[] {anpConfigId};
        List<ResourceAnpConfig> items = _repository.findByAnpConfigIds(id, false);

        int initRow = 1;
        for (ResourceAnpConfig _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getAnpConfig() == null ? "" : _item.getAnpConfig().getType() == null ? "" : _item.getAnpConfig().getType().getName());
            row.createCell(1).setCellValue(_item.getAnpConfig() == null ? "" : _item.getAnpConfig().getName());
            row.createCell(2).setCellValue(_item.getResourceType() == null ? "" : _item.getResourceType().getName());
            row.createCell(3).setCellValue(_item.getResource() == null ? "" : _item.getResource().getName());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
