package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.MonitoringRecordDTO;
import pe.sernanp.ws_api.dto.MonitoringRecordRequestDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MonitoringRecord;

public interface MonitoringRecordService {
    ResponseEntity<MonitoringRecord> findAll() throws Exception;
    ResponseEntity<MonitoringRecord> findById(int id) throws Exception;
    ResponseEntity<MonitoringRecord> listByType(int typeId) throws Exception;
    ResponseEntity<MonitoringRecord> listByResource(int resourceId) throws Exception;
    ResponseEntity<MonitoringRecord> save(MonitoringRecord item) throws Exception;
    ResponseEntity<MonitoringRecord> update(MonitoringRecord item) throws Exception;
    ResponseEntity<MonitoringRecord> delete(int id) throws Exception;

    ResponseEntity<MonitoringRecord> finalizeRegister(int id);

    ResponseEntity<MonitoringRecordDTO> search(MonitoringRecordRequestDTO item2, PaginatorEntity paginator);

    ResponseEntity<MonitoringRecord> saveWithFile(MonitoringRecord item2, MultipartFile file, String folderId);

}
