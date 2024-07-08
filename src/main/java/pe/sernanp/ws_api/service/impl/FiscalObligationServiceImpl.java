package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FiscalObligation;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.MPResource;
import pe.sernanp.ws_api.model.SPTouristSector;
import pe.sernanp.ws_api.repository.FiscalObligationRepository;
import pe.sernanp.ws_api.service.FiscalObligationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class FiscalObligationServiceImpl implements FiscalObligationService {
    @Autowired
    FiscalObligationRepository _repository;

    public ResponseEntity<FiscalObligation> findById(int id) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<FiscalObligation>();
        try {
            Optional<FiscalObligation> item = _repository.findById(id);
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

    public ResponseEntity<FiscalObligation> findByManagementPlanId(int managementPlanId) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<FiscalObligation>();
        try {
            List<FiscalObligation> items = _repository.findByManagementPlanIdAndIsDeleted(managementPlanId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<FiscalObligation> findBySitePlanId(int sitePlanId) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<FiscalObligation>();
        try {
            List<FiscalObligation> items = _repository.findBySitePlanIdAndIsDeleted(sitePlanId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<FiscalObligationDTO> listForOd(int odId) throws Exception {
        ResponseEntity<FiscalObligationDTO> response = new ResponseEntity<FiscalObligationDTO>();
        try {
            List<FiscalObligationDTO> items = _repository.listForOd(odId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<FiscalObligation> save(FiscalObligation item) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<FiscalObligation>();
        try {
            item.setOd((item.getOd() != null && item.getOd().getId() != 0) ? item.getOd() : null);
            item.setSitePlan((item.getSitePlan() != null && item.getSitePlan().getId() != 0) ? item.getSitePlan() : null);
            item.setManagementPlan((item.getManagementPlan() != null && item.getManagementPlan().getId() != 0) ? item.getManagementPlan() : null);

            if ((item.getSitePlan() != null && item.getSitePlan().getId() != 0) || (item.getManagementPlan() != null && item.getManagementPlan().getId() != 0)) {
                item.setResponsible(new ListDetail());
                item.getResponsible().setId(38);
            }

            if (item.getSitePlan() != null && item.getSitePlan().getId() != 0) {
                item.setSource(new ListDetail());
                item.getSource().setId(91);
            }

            item = _repository.save(item);
            if (item.getOd() != null && item.getOd().getId() != 0)
                _repository.insertMatriz(item.getId(), item.getOd().getId());

            response.setItem(item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<FiscalObligation> update(FiscalObligation item) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<FiscalObligation>();
        try {
            Optional<FiscalObligation> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item.setOd((item.getOd() != null && item.getOd().getId() != 0) ? item.getOd() : null);
                item.setSitePlan((item.getSitePlan() != null && item.getSitePlan().getId() != 0) ? item.getSitePlan() : null);
                item.setManagementPlan((item.getManagementPlan() != null && item.getManagementPlan().getId() != 0) ? item.getManagementPlan() : null);

                FiscalObligation _itemUpdate = _repository.save(item);
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

    public ResponseEntity<FiscalObligation> delete(int id) throws Exception {
        ResponseEntity<FiscalObligation> response = new ResponseEntity<FiscalObligation>();
        try {
            Optional<FiscalObligation> item = _repository.findById(id);
            if (item.isPresent()){
                _repository.deleteMatriz(id);
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
            if (ex.getMessage().contains("fk_")){
                response.setMessage("No se puede eliminar una obligación mientras sea utilizada.");
                response.setSuccess(false);
                response.setWarning(true);
                response.setExtra(ex.getMessage());
            } else {
                response.setMessage("Ocurrio un error al guardar la modalidad");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ByteArrayInputStream exportBySitePlan(int sitePlanId) throws Exception {
        String[] columns = { "ANP", "TIPO NORMA", "PUBLICO", "DESCRIPCIÓN" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PS_ObligacionFiscal");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<FiscalObligation> items = this._repository.findBySitePlanIdAndIsDeleted(sitePlanId, false);

        int initRow = 1;
        for (FiscalObligation _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getSitePlan() == null ? "" : _item.getSitePlan().getAnpName());
            row.createCell(1).setCellValue(_item.getNormType() == null ? "" : _item.getNormType().getName());
            row.createCell(2).setCellValue(_item.getAudience() == null ? "" : _item.getAudience().getName());
            row.createCell(3).setCellValue(_item.getDescription());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }

    public ByteArrayInputStream exportByManagementPlan(int managementPlanId) throws Exception {
        String[] columns = { "FUENTE", "DESCRIPCION", "CARACTERISTICA" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PM_ObligacionFiscal");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<FiscalObligation> items = this._repository.findByManagementPlanIdAndIsDeleted(managementPlanId, false);

        int initRow = 1;
        for (FiscalObligation _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getSource() == null ? "" : _item.getSource().getName());
            row.createCell(1).setCellValue(_item.getDescription());
            row.createCell(2).setCellValue(_item.getCharacteristic());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
