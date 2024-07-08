package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.SitePlanDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Sector;
import pe.sernanp.ws_api.model.SitePlan;
import pe.sernanp.ws_api.repository.SectorRepository;
import pe.sernanp.ws_api.service.SectorService;

import java.util.List;
import java.util.Optional;

@Service
public class SectorServiceImpl extends BaseServiceImpl implements SectorService {
    @Autowired
    SectorRepository _repository;

    public ResponseEntity<Sector> search(Sector item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            String anpCode = isNullOrEmpty(item.getAnpCode()) ? "" : item.getAnpCode();
            String code = isNullOrEmpty(item.getCode()) ? "" : item.getCode();
            String name = isNullOrEmpty(item.getName()) ? "" : item.getName();
            int sectorId = item.getSector() == null ? 0 : item.getSector().getId();
            boolean isDeleted = false;//item.getIsDeleted()== null ? false : item.getIsDeleted();
            Page<Sector> pag;

            if (sectorId == 0)
                pag = this._repository.search(anpCode, code, name, isDeleted, page);
            else
                pag = this._repository.searchArea(code, name, sectorId,isDeleted, page);

            List<Sector> items = pag.getContent();
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

    @Override
    public ResponseEntity<Sector> findAll() throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            List<Sector> items = _repository.findAll();
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Sector> findById(int id) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            Optional<Sector> item = _repository.findById(id);
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

    public ResponseEntity<Sector> findBySectorId(int id) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            List<Sector> items = _repository.findBySectorId(id);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar por sector.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Sector> findByAreaCode(String areaCode) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            List<Sector> items = _repository.findByAreaCode(areaCode);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar por sector.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Sector> findAreaByPolygonCode(String polygonCode) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            List<Sector> items = _repository.findAreaByPolygonCode(polygonCode);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar por sector.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Sector> findByAnpCode(String anpCode) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            List<Sector> items = _repository.findByAnpCode(anpCode);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar por anp.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
    
    public ResponseEntity<Sector> save(Sector item) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            item.setSector((item.getSector() == null || item.getSector().getId() == 0) ? null : item.getSector());
            item.setAnpCode(isNullOrEmpty(item.getAnpCode()) ? "" : item.getAnpCode());

            item = _repository.save(item);
            if (item.getSector() != null && item.getSector().getId() > 0)
                _repository.updateTotalArea(item.getSector().getId());

            response.setItem(item);
            response.setMessage("Registro exitoso");

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

    public ResponseEntity<Sector> update(Sector item) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            if (item.getSector() != null && item.getId() == item.getSector().getId()) {
                response.setMessage("El sector no puede estar asociado asimismo.");
                response.setSuccess(false);
                return  response;
            }

            Optional<Sector> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item.setAnpCode(isNullOrEmpty(item.getAnpCode()) ? "" : item.getAnpCode());
                item = _repository.save(item);
                if (item.getSector() != null && item.getSector().getId() > 0)
                    _repository.updateTotalArea(item.getSector().getId());

                response.setItem(item);
                response.setMessage("Se actualizó el registro correctamente");
            }
            else {
                response.setMessage("Registro no existe");
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

    public ResponseEntity<Sector> delete(int id) throws Exception {
        ResponseEntity<Sector> response = new ResponseEntity<Sector>();
        try {
            Optional<Sector> item = _repository.findById(id);
            if (item.isPresent()) {
                _repository.deleteById(id);
                if (item.get().getSector() != null && item.get().getSector().getId() > 0)
                    _repository.updateTotalArea(item.get().getSector().getId());

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
