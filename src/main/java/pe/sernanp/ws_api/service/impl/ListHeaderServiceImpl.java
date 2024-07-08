package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListHeader;
import pe.sernanp.ws_api.repository.ListHeaderRepository;
import pe.sernanp.ws_api.service.ListHeaderService;

import java.util.List;
import java.util.Optional;

@Service
public class ListHeaderServiceImpl extends BaseServiceImpl implements ListHeaderService {
    @Autowired
    ListHeaderRepository _repository;

    public ResponseEntity<ListHeader> search(ListHeader item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<ListHeader>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            String name = isNullOrEmpty(item.getName()) ? "" : item.getName();
            boolean isDeleted = false;//item.getIsDeleted()== null ? false : item.getIsDeleted();

            Page<ListHeader> pag = this._repository.search(item.getCode(), name,isDeleted, page);
            List<ListHeader> items = pag.getContent();
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

//    public ResponseEntity<ListDetailDTO> findAll() throws Exception {
//        ResponseEntity<ListDetailDTO> response = new ResponseEntity<ListDetailDTO>();
//        try {
//            List<ListDetailDTO> items = _repository.listAll();
//            response.setItems(items);
//        } catch (Exception ex) {
//            response.setMessage("Ocurrio un error al listar");
//            response.setSuccess(false);
//            response.setExtra(ex.getMessage());
//        }
//        return response;
//    }

    public ResponseEntity<ListHeader> findById(int id) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<ListHeader>();
        try {
            Optional<ListHeader> item = _repository.findById(id);
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

    public ResponseEntity<ListHeader> save(ListHeader item) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<ListHeader>();
        try {
            int code = _repository.getCurrentValSequence();
            item.setCode(code);
            ListHeader _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListHeader> update(ListHeader item) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<ListHeader>();
        try {
            Optional<ListHeader> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item.setCode(_item.get().getCode());
                item = _repository.save(item);
                response.setItem(item);
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

    public ResponseEntity<ListHeader> delete(int id) throws Exception {
        ResponseEntity<ListHeader> response = new ResponseEntity<ListHeader>();
        try {
            Optional<ListHeader> item = _repository.findById(id);
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
