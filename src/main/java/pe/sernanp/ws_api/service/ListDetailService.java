package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListDetail;

public interface ListDetailService {
    ResponseEntity<ListDetail> search(ListDetail item, PaginatorEntity paginator) throws Exception;
    ResponseEntity<ListDetailDTO> findByCode(int code) throws Exception;
    ResponseEntity<ListDetailDTO> findByListDetailId(int id) throws Exception;
//    ResponseEntity<ListDetail> findAll() throws Exception;
    ResponseEntity<ListDetail> findById(int id) throws Exception;
    ResponseEntity<ListDetail> save(ListDetail item) throws Exception;
    ResponseEntity<ListDetail> update(ListDetail item) throws Exception;
    ResponseEntity<ListDetail> delete(int id) throws Exception;
}
