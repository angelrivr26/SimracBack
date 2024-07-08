package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Polygon;
import pe.sernanp.ws_api.repository.PolygonRepository;
import pe.sernanp.ws_api.service.PolygonService;

import java.util.List;
import java.util.Optional;

@Service
public class PolygonServiceImpl extends BaseServiceImpl implements PolygonService {
    @Autowired
    PolygonRepository _repository;

    public ResponseEntity<Polygon> search(Polygon item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<Polygon>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            int sectorId = 0;
            String anpCode = "";
            if (item.getSector() != null) {
                sectorId = item.getSector().getId();
                anpCode = isNullOrEmpty(item.getSector().getAnpCode()) ? "" : item.getSector().getAnpCode();
            }
            String code = isNullOrEmpty(item.getCode()) ? "" : item.getCode();
            String name = isNullOrEmpty(item.getName()) ? "" : item.getName();
            boolean isDeleted = false;//item.getIsDeleted()== null ? false : item.getIsDeleted();

            Page<Polygon> pag = this._repository.search(anpCode, code, name, sectorId,isDeleted, page);
            List<Polygon> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());
            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar los poligonos.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<Polygon> findAll() throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<Polygon>();
        try {
            List<Polygon> items = _repository.findAll();
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Polygon> findById(int id) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<Polygon>();
        try {
            Optional<Polygon> item = _repository.findById(id);
            if (item.isPresent())
                response.setItem(item.get());
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Polygon> findBySectorId(int sectorId) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<Polygon>();
        try {
            List<Polygon> items = _repository.findBySectorId(sectorId);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar por sector.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Polygon> findByAnpCode(String anpCodes) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<Polygon>();
        try {
            String stringAnpCodes = trimEnd(trimStart(anpCodes, ","), ",");
            String temp = stringAnpCodes.replace(",", "");
            String[] _anpCodes = !isNullOrEmpty(temp) ? anpCodes.split(",") : new String[] {""};

            List<Polygon> items = _repository.findByAnpCode(_anpCodes);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar por sector.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Polygon> save(Polygon item) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<Polygon>();
        try {
            if (item.getSector() == null || item.getSector().getId() == 0 ) {
                response.setMessage("No puede guardar un poligono sin relacionarlo a un sector.");
                response.setSuccess(false);
                response.setWarning(true);
                return response;
            }

            item = _repository.save(item);
            response.setItem(item);
            response.setMessage("Registro exitoso.");

        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe un registro parecido.");
                response.setSuccess(false);
                response.setWarning(true);
            } else {
                response.setMessage("Ocurrio un error al guardar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<Polygon> update(Polygon item) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<Polygon>();
        try {
            if (item.getSector() == null || item.getSector().getId() == 0) {
                response.setMessage("No puede guardar un poligono sin relacionarlo a un sector.");
                response.setSuccess(false);
                response.setWarning(true);
                return response;
            }

            Optional<Polygon> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item = _repository.save(item);

                response.setItem(item);
                response.setMessage("Se actualizó el registro correctamente");
            }
            else {
                response.setMessage("Registro no existe.");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe un registro parecido.");
                response.setSuccess(false);
                response.setWarning(true);
            } else {
                response.setMessage("Ocurrio un error al guardar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<Polygon> delete(int id) throws Exception {
        ResponseEntity<Polygon> response = new ResponseEntity<Polygon>();
        try {
            Optional<Polygon> item = _repository.findById(id);
            if (item.isPresent()) {
                _repository.deleteById(id);
                response.setMessage("Se elimino el registro correctamente.");
            }
            else {
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
}
