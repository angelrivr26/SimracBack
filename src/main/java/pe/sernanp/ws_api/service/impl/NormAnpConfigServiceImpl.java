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
import pe.sernanp.ws_api.model.NormAnpConfig;
import pe.sernanp.ws_api.repository.NormAnpConfigRepository;
import pe.sernanp.ws_api.service.NormAnpConfigService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class NormAnpConfigServiceImpl extends BaseServiceImpl implements NormAnpConfigService {
    @Autowired
    NormAnpConfigRepository _repository;

    public ResponseEntity<NormAnpConfig> findById(int id) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<NormAnpConfig>();
        try {
            Optional<NormAnpConfig> item = _repository.findById(id);
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

    public ResponseEntity<NormAnpConfig> findByAnpConfig(String anpConfigIds) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<NormAnpConfig>();
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
            List<NormAnpConfig> items = _repository.findByAnpConfigIds(_anpConfigIds, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<NormAnpConfig> save(NormAnpConfig item) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<NormAnpConfig>();
        try {
            item.setSource(new ListDetail());
            item.getSource().setId(96);
            item.setResponsible(new ListDetail());
            item.getResponsible().setId(38);
            NormAnpConfig _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<NormAnpConfig> update(NormAnpConfig item) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<NormAnpConfig>();
        try {
            Optional<NormAnpConfig> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item.setSource(_item.get().getSource());
                item.setResponsible(_item.get().getResponsible());
                NormAnpConfig _itemUpdate = _repository.save(item);
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

    public ResponseEntity<NormAnpConfig> delete(int id) throws Exception {
        ResponseEntity<NormAnpConfig> response = new ResponseEntity<NormAnpConfig>();
        try {
            Optional<NormAnpConfig> item = _repository.findById(id);
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
        String[] columns = { "TIPO", "ANP", "TIPO NORMA", "PUBLICO", "NORMA" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Norma_Anp");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        int [] id = new int[] {anpConfigId};
        List<NormAnpConfig> items = _repository.findByAnpConfigIds(id, false);

        int initRow = 1;
        for (NormAnpConfig _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getAnpConfig() == null ? "" : _item.getAnpConfig().getType() == null ? "" : _item.getAnpConfig().getType().getName());
            row.createCell(1).setCellValue(_item.getAnpConfig() == null ? "" : _item.getAnpConfig().getName());
            row.createCell(2).setCellValue(_item.getNormType() == null ? "" : _item.getNormType().getName());
            row.createCell(3).setCellValue(_item.getAudience() == null ? "" : _item.getAudience().getName());
            row.createCell(4).setCellValue(_item.getShortName() == null ? "" : _item.getShortName());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
