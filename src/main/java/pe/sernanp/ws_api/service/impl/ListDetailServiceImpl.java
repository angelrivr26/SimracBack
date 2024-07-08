package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.repository.ListDetailRepository;
import pe.sernanp.ws_api.service.ListDetailService;

import java.util.List;
import java.util.Optional;

@Service
public class ListDetailServiceImpl extends BaseServiceImpl implements ListDetailService {
    @Autowired
    ListDetailRepository _repository;

    public ResponseEntity<ListDetail> search(ListDetail item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<ListDetail>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            int listTypeId = item.getListType() == null ? 0 : item.getListType().getId();
            int listDetailId = item.getListDetail() == null ? 0 : item.getListDetail().getId();
            String name = isNullOrEmpty(item.getName()) ? "" : item.getName();
            boolean isDeleted = false;//item.getIsDeleted()== null ? false : item.getIsDeleted();

            Page<ListDetail> pag = this._repository.search(listTypeId, listDetailId, name,isDeleted, page);
            List<ListDetail> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());
            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar los sectores.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDetailDTO> findByCode(int code) throws Exception {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<ListDetailDTO>();
        try {
            List<ListDetailDTO> items = _repository.findByListTypeCodeAndFlagActive(code,true);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDetailDTO> findByListDetailId(int id) throws Exception {
        ResponseEntity<ListDetailDTO> response = new ResponseEntity<ListDetailDTO>();
        try {
            List<ListDetailDTO> items = _repository.findByListDetailId(id);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
//
//    public ResponseEntity<ListDetail> findAll() throws Exception {
//        ResponseEntity<ListDetail> response = new ResponseEntity<ListDetail>();
//        try {
//            List<ListDetail> items = _repository.findAll();
//            response.setItems(items);
//        } catch (Exception ex) {
//            response.setMessage("Ocurrio un error al listar");
//            response.setSuccess(false);
//            response.setExtra(ex.getMessage());
//        }
//        return response;
//    }
//
    public ResponseEntity<ListDetail> findById(int id) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<ListDetail>();
        try {
            Optional<ListDetail> item = _repository.findById(id);
            if (item.isPresent())
                response.setItem(item.get());
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDetail> save(ListDetail item) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<ListDetail>();
        try {
            if (item.getListType() == null || item.getListType().getId() == 0) {
                response.setMessage("Seleccione un listado valido.");
                response.setSuccess(false);
                return response;
            }

            item.setListDetail((item.getListDetail() == null || item.getListDetail().getId() == 0) ? null : item.getListDetail());
            ListDetail _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDetail> update(ListDetail item) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<ListDetail>();
        try {
            if (item.getListType() == null || item.getListType().getId() == 0) {
                response.setMessage("Seleccione un listado valido.");
                response.setSuccess(false);
                return response;
            }

            item.setListDetail((item.getListDetail() == null || item.getListDetail().getId() == 0) ? null : item.getListDetail());
            Optional<ListDetail> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                _repository.save(item);
                response.setMessage("Se actualizó el registro correctamente");
            }
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al actualizar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDetail> delete(int id) throws Exception {
        ResponseEntity<ListDetail> response = new ResponseEntity<ListDetail>();
        try {
            Optional<ListDetail> item = _repository.findById(id);
            if (item.isPresent()){
                _repository.deleteById(id);
                response.setMessage("Se elimino el registro correctamente.");
            }
            else{
                response.setMessage("El registro no existe.");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al eliminar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
}
