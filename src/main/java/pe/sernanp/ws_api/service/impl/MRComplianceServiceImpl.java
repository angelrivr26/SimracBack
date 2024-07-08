package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.*;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.*;
import pe.sernanp.ws_api.repository.MRComplianceDocumentRepository;
import pe.sernanp.ws_api.repository.MRComplianceRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.MRComplianceService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MRComplianceServiceImpl implements MRComplianceService {
    @Autowired
    MRComplianceRepository _repository;
    @Autowired
    MRComplianceDocumentRepository _MrComplianceDocumentRepository;
    @Autowired
    DocumentService documentService;
    final private ListDetail compliancePending = ListDetail.builder().id(99).build();
    public ResponseEntity<ComplianceEntity> findById(int id) throws Exception {
        ResponseEntity<ComplianceEntity> response = new ResponseEntity<ComplianceEntity>();
        List<MRComplianceDocument> documents;
        MRCompliance _item;
        try {
            Optional<MRCompliance> item = _repository.findById(id);
            if (item.isPresent()){
                ListDetailEntity complianceType = null;
                ListDetailEntity stageType = null;
                _item = item.get();
                documents = _MrComplianceDocumentRepository.findByMrComplianceId(_item.getId());
                if(_item.getComplianceType() != null)
                    complianceType = ListDetailEntity.builder()
                            ._id(_item.getComplianceType().getId())
                            ._name(_item.getComplianceType().getName())
                            .build();
                if(_item.getStageType() != null)
                    stageType = ListDetailEntity.builder()
                            ._id(_item.getStageType().getId())
                            ._name(_item.getStageType().getName())
                            .build();
                ComplianceEntity complianceEntity = ComplianceEntity.builder()
                                        .id(_item.getId())
                                        .complianceSelected(_item.isComplianceSelected())
                                        .complianceType(complianceType)
                                        .stageType(stageType)
                                        .comments(_item.getComments())
                                        .activity(_item.getActivity())
                                        .description(_item.getDescription())
                                        .documents(documents)
                                        .build();
                response.setItem(complianceEntity);
            }
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

    public ResponseEntity<ComplianceEntity> findByMonitoringRecordId(int monitoringRecordId) throws Exception {
        ResponseEntity<ComplianceEntity> response = new ResponseEntity<ComplianceEntity>();
        try {
            List<ComplianceDTO> items = _repository.findByMonitoringRecordId(monitoringRecordId);
            List<ComplianceEntity> _items = items.stream().map(t->{
                List<MRComplianceDocument> documents = _MrComplianceDocumentRepository.findByMrComplianceId(t.getId());
                return ComplianceEntity
                        .builder()
                        .id(t.getId())
                        .complianceSelected(t.getComplianceSelected())
                        .comments(t.getComment())
                        .activity(t.getActivity())
                        .complianceType(t.getComplianteType())
                        .stageType(t.getStageType())
                        .source(t.getSource())
                        .odFiscalObligation(t.getFiscalObligation())
                        .description(t.getDescription())
                        .documents(documents)
                        .build();
            }).collect(Collectors.toList());
            response.setItems(_items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MRCompliance> save(MRCompliance item) throws Exception {
        ResponseEntity<MRCompliance> response = new ResponseEntity<MRCompliance>();
        try {
            if(item.getComplianceType() == null || item.getComplianceType().getId() == 0){
                item.setComplianceType(compliancePending);
            }
            MRCompliance _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MRCompliance> update(MRCompliance item) throws Exception {
        ResponseEntity<MRCompliance> response = new ResponseEntity<MRCompliance>();
        try {
            Optional<MRCompliance> _item = _repository.findById(item.getId());
            MRCompliance mrCompliance;
            if (_item.isPresent()) {
                mrCompliance = _item.get();
                mrCompliance.setComplianceType(item.getComplianceType());
                mrCompliance.setComplianceSelected(item.isComplianceSelected());
                mrCompliance.setComments(item.getComments());
                mrCompliance.setStageType(item.getStageType());
                mrCompliance.setActivity(item.getActivity());
                mrCompliance.setDescription(item.getDescription());
                MRCompliance _itemUpdate = _repository.save(mrCompliance);
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

    public ResponseEntity<MRCompliance> delete(int id) throws Exception {
        ResponseEntity<MRCompliance> response = new ResponseEntity<MRCompliance>();
        try {
            Optional<MRCompliance> item = _repository.findById(id);
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

        List<MRCompliance> items = this._repository.findByMonitoringRecordIdAndIsDeleted(monitoringRecordId, false);

        int initRow = 1;
        for (MRCompliance _item : items) {
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
    public ResponseEntity<MRCompliance> saveWithFiles(MRCompliance item2, MultipartFile[] files, String folderId) {
        ResponseEntity<MRCompliance> response = new ResponseEntity<MRCompliance>();
        MRComplianceDocument document = new MRComplianceDocument();
        MRCompliance mrCompliance;
        MRCompliance _itemUpdate;
        try {
            Optional<MRCompliance> _item = _repository.findById(item2.getId());
            if(_item.isPresent())
            {
                mrCompliance = _item.get();
                mrCompliance.setComplianceType(item2.getComplianceType());
                mrCompliance.setComplianceSelected(item2.isComplianceSelected());
                mrCompliance.setComments(item2.getComments());
                mrCompliance.setStageType(item2.getStageType());
                mrCompliance.setActivity(item2.getActivity());
                mrCompliance.setDescription(item2.getDescription());
                _itemUpdate = _repository.save(mrCompliance);
            }else{
                if(item2.getComplianceType() == null || item2.getComplianceType().getId() == 0){
                    item2.setComplianceType(compliancePending);
                }
                _itemUpdate = _repository.save(item2);
            }
            for(MultipartFile file : files){
                if (file != null && !file.isEmpty()) {
                    DocumentoDTO temp = documentService.saveFile(file, false, folderId, file.getOriginalFilename());
                    if (temp.getSuccess()){
                        document = new MRComplianceDocument();
                        document.setMrCompliance(_itemUpdate);
                        document.setDocumentCode(temp.getId());
                        document.setDocumentSize(file.getSize());
                        document.setDocumentName(file.getOriginalFilename());
                        _MrComplianceDocumentRepository.save(document);
                    }
                    else response.setExtra(temp.getMessagge());
                }
            }
            response.setItem(_itemUpdate);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<ListDetailDTO> complianceForList(int monitoringRecordId) {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<ListDetailDTO>();
        List<ListDetailDTO> items = _repository.complianceForListByMonitoringRecordId(monitoringRecordId);
        response.setItems(items);
        return response;
    }

    @Override
    public ResponseEntity<MRCompliance> findAll() {
        ResponseEntity<MRCompliance> response = new ResponseEntity<MRCompliance>();
        List<MRCompliance> items = _repository.findAll();
        response.setItems(items);
        return response;
    }
}
