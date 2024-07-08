package pe.sernanp.ws_api.service;

import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.ODDTO;
import pe.sernanp.ws_api.entity.MdPOdEntity;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.OD;

import java.io.ByteArrayInputStream;

public interface ODService {
    ResponseEntity<ODDTO> search(OD item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<ODDTO> search(OD item) throws Exception;
    ResponseEntity<OD> findAll() throws Exception;
    ResponseEntity<OD> findById(int id) throws Exception;
    ResponseEntity<OD> findByType(int typeId) throws Exception;
    ResponseEntity<OD> save(OD item, MultipartFile resolutionFile, MultipartFile titleRouteFile) throws Exception;
    ResponseEntity<OD> update(OD item, MultipartFile resolutionFile, MultipartFile titleRouteFile) throws Exception;
    ResponseEntity<OD> delete(int id) throws Exception;
    ByteArrayInputStream export(OD item)  throws Exception;
    byte[] getFile (boolean isAlfresco, String fileId) throws Exception;
    public String getFileName (String fileId) throws Exception;

    ResponseEntity<MdPOdEntity> saveForExternal(MdPOdEntity item) throws Exception;
    ResponseEntity<MdPOdEntity> getForExternal(int procedureId) throws Exception;
}
