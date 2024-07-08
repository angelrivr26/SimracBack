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
import pe.sernanp.ws_api.model.SPGrantingPolygon;
import pe.sernanp.ws_api.model.SPPermittedActivity;
import pe.sernanp.ws_api.repository.SPGrantingPolygonRepository;
import pe.sernanp.ws_api.service.SPGrantingPolygonService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class SPGrantingPolygonServiceImpl extends BaseServiceImpl implements SPGrantingPolygonService {
    @Autowired
    SPGrantingPolygonRepository _repository;

    public ResponseEntity<SPGrantingPolygon> findById(int id) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<SPGrantingPolygon>();
        try {
            Optional<SPGrantingPolygon> item = _repository.findById(id);
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

    public ResponseEntity<SPGrantingPolygon> findBySitePlanId(int sitePlanId) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<SPGrantingPolygon>();
        try {
            List<SPGrantingPolygon> items = _repository.findBySitePlanIdAndIsDeleted(sitePlanId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

//    public ResponseEntity<ModalityDTO> listModalityById(String ids) throws Exception {
//        ResponseEntity<ModalityDTO> response = new ResponseEntity<ModalityDTO>();
//        try {
//            ids = isNullOrEmpty(ids) ? "" : ids;
//            List<ModalityDTO> items = _repository.listModalityById(ids);
//            response.setItems(items);
//        } catch (Exception ex) {
//            response.setMessage("Ocurrio un error al listar");
//            response.setSuccess(false);
//            response.setExtra(ex.getMessage());
//        }
//        return response;
//    }

    public ResponseEntity<ListDetailDTO> listByModality(SPPermittedActivity itemActivity) throws Exception {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<ListDetailDTO>();
        try {
            List<ListDetailDTO> items = _repository.listBySitePlanAndModality(itemActivity.getSitePlan().getId(), itemActivity.getModality().getId());
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SPGrantingPolygon> findBySouristSectorId(int touristSectorId) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<SPGrantingPolygon>();
        try {
            List<SPGrantingPolygon> items = _repository.findBySpTouristSectorIdAndIsDeleted(touristSectorId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SPGrantingPolygon> findByConfig(SPGrantingPolygon item) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<SPGrantingPolygon>();
        try {
            int sitePlanId = item.getSitePlan() == null ? 0 : item.getSitePlan().getId();
            String sectorCodes = item.getSpTouristSector() == null ? "" : item.getSpTouristSector().getCode();

            List<SPGrantingPolygon> items = _repository.findByConfig(sitePlanId, sectorCodes);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ModalityDTO> listModalityByConfig(SPGrantingPolygon item) throws Exception {
        ResponseEntity<ModalityDTO> response = new ResponseEntity<ModalityDTO>();
        try {
            int sitePlanId = item.getSitePlan() == null ? 0 : item.getSitePlan().getId();
            String sectorCodes = item.getSpTouristSector() == null ? "" : item.getSpTouristSector().getCode();
            String polygonCodes = item.getCode() == null ? "" : item.getCode();

            List<ModalityDTO> items = _repository.listModalityByConfig(sitePlanId, sectorCodes, polygonCodes);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SPGrantingPolygon> save(SPGrantingPolygon item) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<SPGrantingPolygon>();
        try {
            SPGrantingPolygon _item = null;
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

    public ResponseEntity<SPGrantingPolygon> update(SPGrantingPolygon item) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<SPGrantingPolygon>();
        try {
            Optional<SPGrantingPolygon> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                SPGrantingPolygon _itemUpdate = null;
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

    public ResponseEntity<SPGrantingPolygon> delete(int id) throws Exception {
        ResponseEntity<SPGrantingPolygon> response = new ResponseEntity<SPGrantingPolygon>();
        try {
            Optional<SPGrantingPolygon> item = _repository.findById(id);
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
        String[] columns = { "ANP", "SECTOR", "CODIGO", "AREA", "RUTA", "USO", "HECT" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PS_PoligonoOtorgamiento");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<SPGrantingPolygon> items = this._repository.findBySitePlanIdAndIsDeleted(sitePlanId, false);

        int initRow = 1;
        for (SPGrantingPolygon _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getSitePlan() == null ? "" : _item.getSitePlan().getName());
            row.createCell(1).setCellValue(_item.getSpTouristSector() == null ? "" : _item.getSpTouristSector().getCode());
            row.createCell(2).setCellValue((_item.getPolygon() == null && _item.getPolygon().getCode() == null) ? "" : _item.getPolygon().getCode());
            row.createCell(3).setCellValue((_item.getPolygon() == null && _item.getPolygon().getArea() == null) ? "" : _item.getPolygon().getArea());
            row.createCell(4).setCellValue((_item.getPolygon() == null && _item.getPolygon().getRoute() == null) ? "" : _item.getPolygon().getRoute());
            row.createCell(5).setCellValue(_item.getUseType() == null ? "" : _item.getUseType().getName());
            row.createCell(6).setCellValue((_item.getPolygon() == null && _item.getPolygon().getHectares() == null) ? "" : _item.getPolygon().getHectares().toString());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
