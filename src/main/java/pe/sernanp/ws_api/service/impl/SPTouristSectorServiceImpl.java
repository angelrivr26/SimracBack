package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SPTouristSector;
import pe.sernanp.ws_api.repository.SPTouristSectorRepository;
import pe.sernanp.ws_api.service.SPTouristSectorService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class SPTouristSectorServiceImpl implements SPTouristSectorService {
    @Autowired
    SPTouristSectorRepository _repository;

    public ResponseEntity<SPTouristSector> findById(int id) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<SPTouristSector>();
        try {
            Optional<SPTouristSector> item = _repository.findById(id);
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

    public ResponseEntity<SPTouristSector> findBySitePlanId(int sitePlanId) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<SPTouristSector>();
        try {
            List<SPTouristSector> items = _repository.findBySitePlanIdAndIsDeleted(sitePlanId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SPTouristSector> save(SPTouristSector item) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<SPTouristSector>();
        try {
            SPTouristSector _item = null;
//            item.setModality(item.getModality() != null ? (item.getModality().getId() == 0 ? null : item.getModality()) : item.getModality());
            _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe un registro parecido.");
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

    public ResponseEntity<SPTouristSector> update(SPTouristSector item) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<SPTouristSector>();
        try {
            Optional<SPTouristSector> _item = _repository.findById(item.getId());
            SPTouristSector _itemUpdate = null;
            if (_item.isPresent()) {
//                item.setModality(item.getModality() != null ? (item.getModality().getId() == 0 ? null : item.getModality()) : item.getModality());
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
                response.setMessage("Ya existe un registro parecido.");
                response.setSuccess(false);
                response.setWarning(true);
                return response;
            } else {
                response.setMessage("Ocurrio un error al actualizar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
                return response;
            }
        }
        return response;
    }

    public ResponseEntity<SPTouristSector> delete(int id) throws Exception {
        ResponseEntity<SPTouristSector> response = new ResponseEntity<SPTouristSector>();
        try {
            Optional<SPTouristSector> item = _repository.findById(id);
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

//    public ResponseEntity<ModalityDTO> findModalityBySectorCode(SPTouristSector item) throws Exception {
//        ResponseEntity<ModalityDTO> response = new ResponseEntity<ModalityDTO>();
//        try {
//            int sitePlanId = item.getSitePlan() == null ? 0 : item.getSitePlan().getId();
//            List<ModalityDTO> itemsModality = _repository.findModalityBySectorCode(item.getCode(), sitePlanId);
//
//            if (itemsModality.size() > 0)
//                response.setItems(itemsModality);
//            else
//                response.setMessage("No se encontraron registros.");
//
//        } catch (Exception ex) {
//            response.setMessage("Ocurrio un error en el detalle");
//            response.setSuccess(false);
//            response.setExtra(ex.getMessage());
//        }
//        return response;
//    }

    public ByteArrayInputStream export(int sitePlanId) throws Exception {
        String[] columns = { "ANP", "CODIGO", "NOMBRE", "HECTAREAS" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PS_SectorTuristico");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<SPTouristSector> items = this._repository.findBySitePlanIdAndIsDeleted(sitePlanId, false);

        int initRow = 1;
        for (SPTouristSector _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getSitePlan() == null ? "" : _item.getSitePlan().getName());
            row.createCell(1).setCellValue(_item.getSectorArea().getCode() == null ? "" : _item.getSectorArea().getCode());
            row.createCell(2).setCellValue(_item.getSectorArea().getName() == null ? "" : _item.getSectorArea().getName());
            row.createCell(3).setCellValue(_item.getSectorArea().getArea() == null ? "" : _item.getSectorArea().getArea().toString());
//            row.createCell(1).setCellValue(_item.getCode() == null ? "" : _item.getCode());
//            row.createCell(2).setCellValue(_item.getName() == null ? "" : _item.getName());
//            row.createCell(3).setCellValue(_item.getHectares() == null ? "" : _item.getHectares().toString());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
