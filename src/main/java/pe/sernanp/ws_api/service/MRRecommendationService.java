package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.RecommendationEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRRecommendation;

import java.io.ByteArrayInputStream;

public interface MRRecommendationService {
    ResponseEntity<RecommendationEntity> findByMonitoringRecordId(int monitoringRecordId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<RecommendationEntity> findById(int id) throws Exception;
    ResponseEntity<MRRecommendation> save(MRRecommendation item) throws Exception;
    ResponseEntity<MRRecommendation> update(MRRecommendation item) throws Exception;
    ResponseEntity<MRRecommendation> delete(int id) throws Exception;
    ByteArrayInputStream export(int monitoringRecordId)  throws Exception;

    ResponseEntity<MRRecommendation> saveWithFiles(MRRecommendation item2, MultipartFile fileEvaluation, MultipartFile[] files, String folderId);
}
