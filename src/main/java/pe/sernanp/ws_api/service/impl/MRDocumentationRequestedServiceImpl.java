package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentationRequestDTO;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRDocumentationRequested;
import pe.sernanp.ws_api.repository.MRDocumentationRequestedRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.MRDocumentationRequestedService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class MRDocumentationRequestedServiceImpl implements MRDocumentationRequestedService {
    @Autowired
    MRDocumentationRequestedRepository _repository;
    @Autowired
    DocumentService documentService;

    public ResponseEntity<MRDocumentationRequested> findById(int id) throws Exception {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<MRDocumentationRequested>();
        try {
            Optional<MRDocumentationRequested> item = _repository.findById(id);
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

    public ResponseEntity<DocumentationRequestDTO> findByMonitoringRecordId(int monitoringRecordId) {
        ResponseEntity<DocumentationRequestDTO> response = new ResponseEntity<DocumentationRequestDTO>();
        try {
            List<DocumentationRequestDTO> items = _repository.listByMonitoringRecordId(monitoringRecordId);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MRDocumentationRequested> save(MRDocumentationRequested item) throws Exception {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<MRDocumentationRequested>();
        try {
            if(item.getId() == 0){
                Optional<MRDocumentationRequested> mrDocumentationRequested = _repository.findByMonitoringRecordId(item.getMonitoringRecord().getId()).stream().filter(t-> t.getCompliance().getId() == item.getCompliance().getId()).findFirst();
                if(mrDocumentationRequested.isPresent()){
                    response.setMessage("Este compromiso ya esta registrado para una documentación solicitada.");
                    response.setWarning(true);
                    return response;
                }
            }
            MRDocumentationRequested _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MRDocumentationRequested> update(MRDocumentationRequested item) throws Exception {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<MRDocumentationRequested>();
        try {
            Optional<MRDocumentationRequested> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                MRDocumentationRequested _itemUpdate = _repository.save(item);
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

    public ResponseEntity<MRDocumentationRequested> delete(int id) throws Exception {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<MRDocumentationRequested>();
        try {
            Optional<MRDocumentationRequested> item = _repository.findById(id);
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

    public ByteArrayInputStream export(int monitoringRecordId) throws Exception {
        String[] columns = { "ANP", "TIPO ACTIVIDAD", "ACTIVIDAD" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("PM_ActivPermitidas");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<MRDocumentationRequested> items = this._repository.findByMonitoringRecordIdAndIsDeleted(monitoringRecordId, false);

        int initRow = 1;
        for (MRDocumentationRequested _item : items) {
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
    public ResponseEntity<MRDocumentationRequested> saveWithFiles(MRDocumentationRequested item2, MultipartFile file, String folderId) {
        ResponseEntity<MRDocumentationRequested> response = new ResponseEntity<>();
        MRDocumentationRequested _item;
        MRDocumentationRequested document;
        try {
            if(item2.getId() == 0) {
                Optional<MRDocumentationRequested> mrDocumentationRequested = _repository.findByMonitoringRecordId(item2.getMonitoringRecord().getId()).stream().filter(t -> t.getCompliance().getId() == item2.getCompliance().getId()).findFirst();
                if (mrDocumentationRequested.isPresent()) {
                    response.setMessage("Este compromiso ya esta registrado para una documentación solicitada.");
                    response.setWarning(true);
                    return response;
                }
            }
            if (file != null && !file.isEmpty()) {
                DocumentoDTO temp2 = documentService.saveFile(file, false, folderId, file.getOriginalFilename());
                if (temp2.getSuccess()){
                    item2.setDocumentCode(temp2.getId());
                    item2.setDocumentSize(file.getSize());
                    item2.setDocumentName(file.getOriginalFilename());
                }
                else{
                    response.setMessage("Ocurrio un erro al guardar el documento de evaluacion");
                    response.setExtra(temp2.getMessagge());
                    response.setWarning(true);
                };
            }
            _item = _repository.save(item2);
            response.setItem(_item);
            response.setWarning(false);
        }catch (Exception ex){
            response.setMessage("Ocurrio un error al Guardar las recomendaciones");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }
}
