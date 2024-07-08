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
import pe.sernanp.ws_api.dto.VerifiedFactDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRVerifiedFact;
import pe.sernanp.ws_api.model.SRVerifiedFactDocument;
import pe.sernanp.ws_api.repository.SRVerifiedFactRepository;
import pe.sernanp.ws_api.repository.VerifiedFactDocumentRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.SRVerifiedFactService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SRVerifiedFactServiceImpl implements SRVerifiedFactService {
    @Autowired
    SRVerifiedFactRepository _repository;
    @Autowired
    VerifiedFactDocumentRepository _verifiedFactDocumentRepository;
    @Autowired
    DocumentService documentService;
    public ResponseEntity<SRVerifiedFact> findById(int id) throws Exception {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<SRVerifiedFact>();
        try {
            Optional<SRVerifiedFact> item = _repository.findById(id);
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

    public ResponseEntity<VerifiedFactDTO> findBySupervisionRecordId(int supervisionRecordId) throws Exception {
        ResponseEntity<VerifiedFactDTO> response = new ResponseEntity<VerifiedFactDTO>();
        try {
            List<SRVerifiedFact> items = _repository.findBySupervisionRecordIdAndIsDeleted(supervisionRecordId, false);
            List<VerifiedFactDTO> _items = items.stream()
                    .map(t-> {
                        List<SRVerifiedFactDocument> documents =_verifiedFactDocumentRepository.findByVerifiedFactId(t.getId());
                        return VerifiedFactDTO.builder()
                                .supervisionRecord(t.getSupervisionRecord())
                                .administrativeMeasure(t.getAdministrativeMeasure())
                                .voluntaryCorrection(t.getVoluntaryCorrection())
                                .breach(t.getBreach())
                                .description(t.getOdFiscalObligation() == null ? "" : t.getOdFiscalObligation().getFiscalObligation() == null ? "" : t.getOdFiscalObligation().getFiscalObligation().getDescription())
                                .id(t.getId())
                                .documents(documents)
                                .odFiscalObligation(t.getOdFiscalObligation())
                                .build();
                            }).sorted(Comparator.comparing(VerifiedFactDTO::getDescription))
                            .collect(Collectors.toList());
            response.setItems(_items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SRVerifiedFact> save(SRVerifiedFact item) throws Exception {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<SRVerifiedFact>();
        try {
            SRVerifiedFact _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SRVerifiedFact> update(SRVerifiedFact item) throws Exception {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<SRVerifiedFact>();
        try {
            Optional<SRVerifiedFact> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                SRVerifiedFact _itemUpdate = _repository.save(item);
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

    public ResponseEntity<SRVerifiedFact> delete(int id) throws Exception {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<SRVerifiedFact>();
        try {
            Optional<SRVerifiedFact> item = _repository.findById(id);
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

        List<SRVerifiedFact> items = this._repository.findBySupervisionRecordIdAndIsDeleted(supervisionRecordId, false);

        int initRow = 1;
        for (SRVerifiedFact _item : items) {
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

    @Override
    public ResponseEntity<SRVerifiedFact> saveWithFiles(SRVerifiedFact item, MultipartFile[] files,String folderId) {
        ResponseEntity<SRVerifiedFact> response = new ResponseEntity<SRVerifiedFact>();
        SRVerifiedFactDocument document = new SRVerifiedFactDocument();
        try {
            SRVerifiedFact _item = _repository.save(item);
            for(MultipartFile file : files){
                if (file != null && !file.isEmpty()) {
                    DocumentoDTO temp = documentService.saveFile(file, false, folderId, file.getOriginalFilename());
                    if (temp.getSuccess()){
                        document = new SRVerifiedFactDocument();
                        document.setVerifiedFact(_item);
                        document.setDocumentCode(temp.getId());
                        document.setDocumentSize(file.getSize());
                        document.setDocumentName(file.getOriginalFilename());
                        _verifiedFactDocumentRepository.save(document);
                    }
                    else response.setExtra(temp.getMessagge());
                }
            }
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
}
