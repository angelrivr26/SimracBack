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
import pe.sernanp.ws_api.dto.RecommendationDTO;
import pe.sernanp.ws_api.dto.RecommendationEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.MRRecommendation;
import pe.sernanp.ws_api.model.MRRecommendationDocument;
import pe.sernanp.ws_api.repository.MRRecommendationDocumentRepository;
import pe.sernanp.ws_api.repository.MRRecommendationRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.MRRecommendationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MRRecommendationServiceImpl implements MRRecommendationService {
    @Autowired
    MRRecommendationRepository _repository;
    @Autowired
    DocumentService documentService;
    @Autowired
    MRRecommendationDocumentRepository mrRecommendationDocumentRepository;
    final private ListDetail competitorPending = ListDetail.builder().id(99).build();
    public ResponseEntity<RecommendationEntity> findById(int id) throws Exception {
        ResponseEntity<RecommendationEntity> response = new ResponseEntity<RecommendationEntity>();

        try {
            Optional<MRRecommendation> item = _repository.findById(id);
            RecommendationEntity recommendationEntity;
            if (item.isPresent()){
                RecommendationDTO itemDto = _repository.detailById(id);
                //recommendationEntity.getRecommendation();
                List<MRRecommendationDocument> documents = mrRecommendationDocumentRepository.findByMrRecommendationId(itemDto.getId());
                RecommendationEntity _item = RecommendationEntity.builder()
                        .id(itemDto.getId())
                        .source(itemDto.getSource())
                        .compromise(itemDto.getCompromise())
                        .recommendation(itemDto.getRecommendation())
                        .agreedDate(itemDto.getAgreedDate())
                        .complianceType(itemDto.getComplianceType())
                        .documentEvaluate(itemDto.getDocumentEvaluate())
                        .comment(itemDto.getComment())
                        .stageType(itemDto.getStageType())
                        .documents(documents)
                        .build();
                response.setItem(_item);

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

    public ResponseEntity<RecommendationEntity> findByMonitoringRecordId(int monitoringRecordId) throws Exception {
        ResponseEntity<RecommendationEntity> response = new ResponseEntity<RecommendationEntity>();
        try {
            List<RecommendationDTO> items = _repository.listByMonitoringRecordId(monitoringRecordId);
            List<RecommendationEntity> _items = items.stream().map( t ->{
                    List<MRRecommendationDocument> documents = mrRecommendationDocumentRepository.findByMrRecommendationId(t.getId());
                    return RecommendationEntity.builder()
                    .id(t.getId())
                    .source(t.getSource())
                    .compromise(t.getCompromise())
                    .recommendation(t.getRecommendation())
                    .agreedDate(t.getAgreedDate())
                            .complianceType(t.getComplianceType())
                    .documentEvaluate(t.getDocumentEvaluate())
                    .comment(t.getComment())
                    .stageType(t.getStageType())
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

    public ResponseEntity<MRRecommendation> save(MRRecommendation item) throws Exception {
        ResponseEntity<MRRecommendation> response = new ResponseEntity<MRRecommendation>();
        try {
            if(item.getId() == 0) {
                Optional<MRRecommendation> mrRecommendation = _repository.findByMonitoringRecordId(item.getMonitoringRecord().getId())
                        .stream()
                        .filter(t -> t.getCompliance().getId() == item.getCompliance().getId())
                        .findFirst();
                if (mrRecommendation.isPresent()) {
                    response.setMessage("Este compromiso ya esta registrado para una recomendación.");
                    response.setWarning(true);
                    return response;
                }
                if(item.getComplianceType() == null || item.getComplianceType().getId() == 0){
                    item.setComplianceType(competitorPending);
                }
            }

            MRRecommendation _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MRRecommendation> update(MRRecommendation item) throws Exception {
        ResponseEntity<MRRecommendation> response = new ResponseEntity<MRRecommendation>();
        try {
            Optional<MRRecommendation> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                MRRecommendation _itemUpdate = _repository.save(item);
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

    public ResponseEntity<MRRecommendation> delete(int id) throws Exception {
        ResponseEntity<MRRecommendation> response = new ResponseEntity<MRRecommendation>();
        try {
            Optional<MRRecommendation> item = _repository.findById(id);
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

        List<MRRecommendation> items = this._repository.findByMonitoringRecordIdAndIsDeleted(monitoringRecordId, false);

        int initRow = 1;
        for (MRRecommendation _item : items) {
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
    public ResponseEntity<MRRecommendation> saveWithFiles(MRRecommendation item2, MultipartFile fileEvaluation, MultipartFile[] files, String folderId) {
        ResponseEntity<MRRecommendation> response = new ResponseEntity<>();
        MRRecommendation _item;
        MRRecommendationDocument document;
        try {
            if(item2.getId() == 0) {
                Optional<MRRecommendation> mrRecommendation = _repository.findByMonitoringRecordId(item2.getMonitoringRecord().getId()).stream().filter(t -> t.getCompliance().getId() == item2.getCompliance().getId()).findFirst();
                if (mrRecommendation.isPresent()) {
                    response.setMessage("Este compromiso ya esta registrado para una recomendación.");
                    response.setWarning(true);
                    return response;
                }
                if(item2.getComplianceType() == null || item2.getComplianceType().getId() == 0){
                    item2.setComplianceType(competitorPending);
                }
            }
            if (fileEvaluation != null && !fileEvaluation.isEmpty()) {
                DocumentoDTO temp2 = documentService.saveFile(fileEvaluation, false, folderId, fileEvaluation.getOriginalFilename());
                if (temp2.getSuccess()){
                    item2.setEvaluateDocumentCode(temp2.getId());
                    item2.setEvaluateDocumentSize(fileEvaluation.getSize());
                    item2.setEvaluateDocumentName(fileEvaluation.getOriginalFilename());
                }
                else{
                    response.setMessage("Ocurrio un erro al guardar el documento de evaluacion");
                    response.setExtra(temp2.getMessagge());
                    response.setWarning(true);
                };
            }
            _item = _repository.save(item2);

            for(MultipartFile file : files){
                if (file != null && !file.isEmpty()) {
                    DocumentoDTO temp = documentService.saveFile(file, false, folderId, file.getOriginalFilename());
                    if (temp.getSuccess()){
                        document = new MRRecommendationDocument();
                        document.setMrRecommendation(_item);
                        document.setDocumentCode(temp.getId());
                        document.setDocumentSize(file.getSize());
                        document.setDocumentName(file.getOriginalFilename());
                        mrRecommendationDocumentRepository.save(document);
                    }
                    else{
                        response.setMessage("Ocurrio un erro al guardar los documentos");
                        response.setExtra(temp.getMessagge());
                        response.setWarning(true);
                    };
                }
            }
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
