package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.VerifiedFactDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRVerifiedFact;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface SRVerifiedFactService {
    ResponseEntity<VerifiedFactDTO> findBySupervisionRecordId(int supervisionRecordId) throws Exception;
    //    ResponseEntity<ODActivity> findAll() throws Exception;
    ResponseEntity<SRVerifiedFact> findById(int id) throws Exception;
    ResponseEntity<SRVerifiedFact> save(SRVerifiedFact item) throws Exception;
    ResponseEntity<SRVerifiedFact> update(SRVerifiedFact item) throws Exception;
    ResponseEntity<SRVerifiedFact> delete(int id) throws Exception;
    ByteArrayInputStream export(int supervisionRecordId)  throws Exception;
    ResponseEntity<SRVerifiedFact> saveWithFiles(SRVerifiedFact item, MultipartFile[] files, String folderId);

}
