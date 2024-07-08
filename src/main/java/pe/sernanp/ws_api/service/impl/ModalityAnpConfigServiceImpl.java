package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ModalityAnpConfig;
import pe.sernanp.ws_api.repository.ModalityAnpConfigRepository;
import pe.sernanp.ws_api.service.ModalityAnpConfigService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ModalityAnpConfigServiceImpl extends BaseServiceImpl  implements ModalityAnpConfigService {
    @Autowired
    ModalityAnpConfigRepository _repository;

//    public ResponseEntity<ModalityAnpConfig> findAll() throws Exception {
//        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<ModalityAnpConfig>();
//        try {
//            List<ModalityAnpConfig> items = _repository.findAll();
//            response.setItems(items);
//        } catch (Exception ex) {
//            response.setMessage("Ocurrio un error al listar");
//            response.setSuccess(false);
//            response.setExtra(ex.getMessage());
//        }
//        return response;
//    }

    public ResponseEntity<ModalityAnpConfig> findById(int id) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<ModalityAnpConfig>();
        try {
            Optional<ModalityAnpConfig> item = _repository.findById(id);
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

    public ResponseEntity<ModalityAnpConfig> findByAnpConfig(String anpConfigIds) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<ModalityAnpConfig>();
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
            List<ModalityAnpConfig> items = _repository.findByAnpConfigIds(_anpConfigIds, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ModalityAnpConfig> save(ModalityAnpConfig item) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<ModalityAnpConfig>();
        ModalityAnpConfig _item = null;
        try {
            try {
                _item = _repository.save(item);
            } catch (Exception ex) {
                //if (((ConstraintViolationException) ex.getCause()).getSQLState().equals("23505")) {
                if (ex.getMessage().contains("uq_")){
                    response.setMessage("Ya existe una configuración de modalidad parecida registrada.");
                    response.setSuccess(false);
                    response.setWarning(true);
                } else {
                    response.setMessage("Ocurrio un error al guardar.");
                    response.setSuccess(false);
                    response.setExtra(ex.getMessage());
                }
                return response;
            }
            response.setItem(_item);
            response.setMessage("Registro exitoso.");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ModalityAnpConfig> update(ModalityAnpConfig item) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<ModalityAnpConfig>();
        try {
            Optional<ModalityAnpConfig> _item = _repository.findById(item.getId());
            ModalityAnpConfig _itemUpdate = null;
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
            //if (((ConstraintViolationException) ex.getCause()).getSQLState().equals("23505")) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe una configuración de modalidad parecida registrada.");
                response.setSuccess(false);
                response.setWarning(true);
                return response;
            } else {
                response.setMessage("Ocurrio un error al guardar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
                return response;
            }
        }
        return response;
    }

    public ResponseEntity<ModalityAnpConfig> delete(int id) throws Exception {
        ResponseEntity<ModalityAnpConfig> response = new ResponseEntity<ModalityAnpConfig>();
        try {
            Optional<ModalityAnpConfig> item = _repository.findById(id);
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

    public ResponseEntity<ListDTO> listSectorByTypeModalityAndAnpCode(ModalityAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        try {
            String anpCodes = item.getAnpConfig() == null ? "" : item.getAnpConfig().getCode();
            String stringAnpCodes = trimEnd(trimStart(anpCodes, ","), ",");
            String temp = stringAnpCodes.replace(",", "");
            String[] _anpCodes = !isNullOrEmpty(temp) ? anpCodes.split(",") : new String[]{""};
            int typeId = item.getAnpConfig() == null ? 0 : item.getAnpConfig().getType() == null ? 0 : item.getAnpConfig().getType().getId();

            List<ListDTO> sectors =  _repository.listSectorByTypeModalityAndAnpCode(typeId, item.getModality().getId(), _anpCodes);
            response.setItems(sectors);
            if (sectors.size() == 0)
                response.setMessage("No se encontraron sectores configurados para la modalidad.");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar los sectores.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDTO> listPolygonByTypeModalityAndAnpSector(ModalityAnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<>();
        try {
            String anpCodes = item.getAnpConfig() == null ? "" : item.getAnpConfig().getCode();
            String stringAnpCodes = trimEnd(trimStart(anpCodes, ","), ",");
            String temp = stringAnpCodes.replace(",", "");
            String[] _anpCodes = !isNullOrEmpty(temp) ? anpCodes.split(",") : null;

            String stringSectorCode = trimEnd(trimStart(item.getAnpConfig().getSectorCode(), ","), ",");
            String temp2 = stringSectorCode.replace(",", "");
            String[] _sectorCodes = !isNullOrEmpty(temp2) ? item.getAnpConfig().getSectorCode().split(",") : new String[]{""};
            int typeId = item.getAnpConfig() == null ? 0 : item.getAnpConfig().getType() == null ? 0 : item.getAnpConfig().getType().getId();

            List<ListDTO> sectors =  _repository.listPolygonByTypeModalityAndAnpSector(typeId, item.getModality().getId(), _anpCodes, _sectorCodes);
            response.setItems(sectors);
            if (sectors.size() == 0)
                response.setMessage("No se encontraron poligonos configurados para la modalidad.");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar los sectores.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ByteArrayInputStream export(int anpConfigId) throws Exception {
        String[] columns = { "TIPO", "ANP", "SECTOR", "MODALIDAD" , "TIPO USO"};

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Modalidad_Anp");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        int [] id = new int[] {anpConfigId};
        List<ModalityAnpConfig> items = _repository.findByAnpConfigIds(id, false);

        int initRow = 1;
        for (ModalityAnpConfig _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getAnpConfig() == null ? "" : _item.getAnpConfig().getType() == null ? "" : _item.getAnpConfig().getType().getName());
            row.createCell(1).setCellValue(_item.getAnpConfig() == null ? "" : _item.getAnpConfig().getName());
            row.createCell(2).setCellValue(_item.getAnpConfig() == null ? "" : _item.getAnpConfig().getSectorName());
            row.createCell(3).setCellValue(_item.getModality() == null ? "" : _item.getModality().getShortName());
            row.createCell(4).setCellValue(_item.getUseType() == null ? "" : _item.getUseType().getName());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
