package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.dto.SRFiscalObligationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRFiscalObligation;
import pe.sernanp.ws_api.repository.SRFiscalObligationRepository;
import pe.sernanp.ws_api.repository.SupervisionReportRepository;
import pe.sernanp.ws_api.service.SRFiscalObligationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class SRFiscalObligationServiceImpl implements SRFiscalObligationService {
    @Autowired
    SRFiscalObligationRepository _repository;
    @Autowired
    SupervisionReportRepository _repositorySupervisionReport;

    public ResponseEntity<SRFiscalObligation> findById(int id) throws Exception {
        ResponseEntity<SRFiscalObligation> response = new ResponseEntity<SRFiscalObligation>();
        try {
            Optional<SRFiscalObligation> item = _repository.findById(id);
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

    public ResponseEntity<SRFiscalObligationDTO> findBySupervisionReportId(int supervisionReportId) throws Exception {
        ResponseEntity<SRFiscalObligationDTO> response = new ResponseEntity<SRFiscalObligationDTO>();
        try {
            List<SRFiscalObligationDTO> items = _repository.findBySupervisionReportIdAndIsDeleted(supervisionReportId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDetailDTO> listFiscalObligationBySupervisionReportId(int supervisionReportId) throws Exception {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<ListDetailDTO>();
        try {
            List<ListDetailDTO> items = _repository.listFiscalObligationBySupervisionReportId(supervisionReportId);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SRFiscalObligation> save(SRFiscalObligation item) throws Exception {
        ResponseEntity<SRFiscalObligation> response = new ResponseEntity<SRFiscalObligation>();
        try {
            SRFiscalObligation _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SRFiscalObligation> update(SRFiscalObligation item) throws Exception {
        ResponseEntity<SRFiscalObligation> response = new ResponseEntity<SRFiscalObligation>();
        try {
            Optional<SRFiscalObligation> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                SRFiscalObligation _itemUpdate = _repository.save(item);
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

    public ResponseEntity<SRFiscalObligation> delete(int id) throws Exception {
        ResponseEntity<SRFiscalObligation> response = new ResponseEntity<SRFiscalObligation>();
        try {
            Optional<SRFiscalObligation> item = _repository.findById(id);
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
}
