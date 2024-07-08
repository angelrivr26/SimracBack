package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListHeader;

public interface ListHeaderService {
    ResponseEntity<ListHeader> search(ListHeader item, PaginatorEntity paginator) throws Exception;
//    ResponseEntity<ListDetailDTO> findAll() throws Exception;
    ResponseEntity<ListHeader> findById(int id) throws Exception;
    ResponseEntity<ListHeader> save(ListHeader item) throws Exception;
    ResponseEntity<ListHeader> update(ListHeader item) throws Exception;
    ResponseEntity<ListHeader> delete(int id) throws Exception;
}
