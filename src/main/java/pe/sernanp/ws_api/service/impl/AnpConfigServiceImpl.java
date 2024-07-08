package pe.sernanp.ws_api.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.AnpConfigDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.AnpConfig;
import pe.sernanp.ws_api.repository.AnpConfigRepository;
import pe.sernanp.ws_api.service.AnpConfigService;

import java.util.List;
import java.util.Optional;

@Service
public class AnpConfigServiceImpl extends BaseServiceImpl implements AnpConfigService {
    @Autowired
    AnpConfigRepository _repository;

    public ResponseEntity<AnpConfigDTO> search(AnpConfig item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<AnpConfigDTO> response = new ResponseEntity<AnpConfigDTO>();
        try {
            boolean isDeleted = item.getIsDeleted()== null ? false : item.getIsDeleted();
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            Page<AnpConfigDTO> pag = this._repository.search(isDeleted, page);
            List<AnpConfigDTO> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());
            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar las od");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDTO> searchRelated(AnpConfig item) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<ListDTO>();
        try {
            List<ListDTO> items;
            if (isNullOrEmpty(item.getCode()) && isNullOrEmpty(item.getSectorCode())){
                items = this._repository.searchRelated(item.getType().getId());
            } else {
                if (isNullOrEmpty(item.getSectorCode()))
                    items = this._repository.searchRelatedSector(item.getType().getId(), item.getCode());
                else
                    items = this._repository.searchRelatedPolygon(item.getType().getId(), item.getCode(), item.getSectorCode());
            }

            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar las od");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<AnpConfig> findAll() throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<AnpConfig>();
        try {
            List<AnpConfig> items = _repository.findByIsDeleted(false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<AnpConfig> findById(int id) throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<AnpConfig>();
        try {
            Optional<AnpConfig> item = _repository.findById(id);
            if (item.isPresent())
                response.setItem(item.get());
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

    public ResponseEntity<AnpConfig> findByAnpConfig(AnpConfig item) throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<AnpConfig>();
        try {
            String sectorCode = isNullOrEmpty(item.getSectorCode()) ? "" : item.getSectorCode();
            String polygonCode = isNullOrEmpty(item.getPolygonCode()) ? "" : item.getPolygonCode();
            Optional<AnpConfig> _item = _repository.findByAnpConfig(item.getType().getId(), item.getCode(), sectorCode, polygonCode);
            if (_item.isPresent())
                item = _item.get();
            else {
                item.setSectorCode(isNullOrEmpty(item.getSectorCode()) ? null : item.getSectorCode());
                item.setPolygonCode(isNullOrEmpty(item.getPolygonCode()) ? null : item.getPolygonCode());
                item = _repository.save(item);
            }
            response.setItem(item);
            response.setMessage("Se genero el registro.");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<AnpConfig> save(AnpConfig item) throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<AnpConfig>();
        try {
            AnpConfig _item;
            String polygonCode = isNullOrEmpty(item.getPolygonCode()) ? null : item.getPolygonCode();
            Optional<AnpConfig> _itemTemp = _repository.findByAnpConfig(item.getType().getId(), item.getCode(), item.getSectorCode(), polygonCode);
            if (_itemTemp.isPresent())
                _item = _itemTemp.get();
            else {
                item.setPolygonCode(isNullOrEmpty(item.getPolygonCode()) ? null : item.getPolygonCode());
                _item = _repository.save(item);
            }
            response.setItem(_item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<AnpConfig> update(AnpConfig item) throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<AnpConfig>();
        try {
            Optional<AnpConfig> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item.setName(_item.get().getName());
                AnpConfig _itemUpdate = _repository.save(item);
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

    public ResponseEntity<AnpConfig> delete(int id) throws Exception {
        ResponseEntity<AnpConfig> response = new ResponseEntity<AnpConfig>();
        try {
            Optional<AnpConfig> item = _repository.findById(id);
            if (item.isPresent()){
                try {
                    _repository.deleteById(id);
                    //_repository.updateIsDeleted(id, false);
                }
                catch (Exception ex) {
                    if (((ConstraintViolationException) ex.getCause()).getSQLState().equals("23503")){
                        response.setMessage("No se puede eliminar una configuración mientras es utilizada");
                        response.setSuccess(false);
                        response.setWarning(true);
                        response.setExtra(ex.getMessage());
                    } else {
                        response.setMessage("Ocurrio un error al eliminar");
                        response.setSuccess(false);
                        response.setExtra(ex.getMessage());
                    }
                    return response;
                }
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

    public ResponseEntity<ListDTO> listByModality(int modalityId) throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<ListDTO>();
        try {
            List<ListDTO> items = _repository.findByModalityIdAndIsDeleted(modalityId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar las modalidades.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
}
