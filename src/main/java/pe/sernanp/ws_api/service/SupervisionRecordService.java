package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.SupervisionRecordDTO;
import pe.sernanp.ws_api.dto.SupervisionRecordRequestDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.OD;
import pe.sernanp.ws_api.model.SupervisionRecord;

public interface SupervisionRecordService {
    ResponseEntity<SupervisionRecord> findAll() throws Exception;
    ResponseEntity<SupervisionRecord> findById(int id) throws Exception;
    ResponseEntity<SupervisionRecord> listByType(int typeId) throws Exception;
    ResponseEntity<SupervisionRecord> save(SupervisionRecord item) throws Exception;
    ResponseEntity<SupervisionRecord> update(SupervisionRecord item) throws Exception;
    ResponseEntity<SupervisionRecord> delete(int id) throws Exception;

    ResponseEntity<SupervisionRecordDTO> search(SupervisionRecordRequestDTO item, PaginatorEntity paginator);

    ResponseEntity<SupervisionRecord> finalizeRegister(int id);

    ResponseEntity<SupervisionRecord> saveWithFile(SupervisionRecord item2, MultipartFile file, String folderId);

}
