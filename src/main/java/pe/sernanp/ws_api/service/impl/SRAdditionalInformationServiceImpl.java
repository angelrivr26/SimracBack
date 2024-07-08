package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRAdditionalInformation;
import pe.sernanp.ws_api.repository.SRAdditionalInformationRepository;
import pe.sernanp.ws_api.service.SRAdditionalInformationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class SRAdditionalInformationServiceImpl implements SRAdditionalInformationService {
    @Autowired
    SRAdditionalInformationRepository _repository;

    public ResponseEntity<SRAdditionalInformation> findById(int id) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<SRAdditionalInformation>();
        try {
            Optional<SRAdditionalInformation> item = _repository.findById(id);
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

    public ResponseEntity<SRAdditionalInformation> findBySupervisionRecordId(int supervisionRecordId) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<SRAdditionalInformation>();
        try {
            List<SRAdditionalInformation> items = _repository.findBySupervisionRecordIdAndIsDeleted(supervisionRecordId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SRAdditionalInformation> save(SRAdditionalInformation item) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<SRAdditionalInformation>();
        try {
            SRAdditionalInformation _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SRAdditionalInformation> update(SRAdditionalInformation item) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<SRAdditionalInformation>();
        try {
            Optional<SRAdditionalInformation> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                SRAdditionalInformation _itemUpdate = _repository.save(item);
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

    public ResponseEntity<SRAdditionalInformation> delete(int id) throws Exception {
        ResponseEntity<SRAdditionalInformation> response = new ResponseEntity<SRAdditionalInformation>();
        try {
            Optional<SRAdditionalInformation> item = _repository.findById(id);
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

    public ByteArrayInputStream export(int supervisionRecordId) throws Exception {
        String[] columns = { "ANP", "TIPO ACTIVIDAD", "ACTIVIDAD" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PM_ActivPermitidas");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<SRAdditionalInformation> items = this._repository.findBySupervisionRecordIdAndIsDeleted(supervisionRecordId, false);

        int initRow = 1;
        for (SRAdditionalInformation _item : items) {
            row = sheet.createRow(initRow);
//            row.createCell(0).setCellValue(_item.getManagementPlan() == null ? "" : _item.getManagementPlan().getAnpCode());
//            row.createCell(1).setCellValue(_item.getActivityType() == null ? "" : _item.getActivityType().getName());
//            row.createCell(2).setCellValue(_item.getActivity() == null ? "" : _item.getActivity().getName());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
