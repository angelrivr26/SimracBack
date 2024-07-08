package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Addendum;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.OD;
import pe.sernanp.ws_api.repository.AddendumRepository;
import pe.sernanp.ws_api.repository.ODRepository;
import pe.sernanp.ws_api.service.AddendumService;

import java.net.URLEncoder;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AddendumServiceImpl extends BaseServiceImpl implements AddendumService {
    @Autowired
    AddendumRepository _repository;
    @Autowired
    ODRepository _repositoryOd;

    public ResponseEntity<Addendum> findById(int id) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<Addendum>();
        try {
            Optional<Addendum> item = _repository.findById(id);
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

    public ResponseEntity<Addendum> findByOdId(int odId) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<Addendum>();
        try {
            List<Addendum> items = _repository.findByOdIdAndIsDeletedOrderByOrderAsc(odId, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Addendum> save(Addendum item, MultipartFile file) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<Addendum>();
        try {
            item.setDuration("");
            if (item.getEmissionDate() != null && item.getEndDate() != null) {
                Period diff = Period.between(item.getEmissionDate(), item.getEndDate());
                String duration = (diff.getYears() > 0 ? diff.getYears() + " año(s) " : "") + (diff.getMonths() > 0 ? diff.getMonths()+ " mes(es) " : "") + (diff.getDays() > 0 ? diff.getDays() + " día(s)" : "");
                item.setDuration(duration);
            }

            Optional<Addendum> itemTemp = _repository.findByOdIdAndOrder(item.getOd().getId(), item.getOrder());
            if (itemTemp.isPresent()){
                response.setMessage("Ya se cuenta con una adenda con el orden ingresado.");
                response.setSuccess(false);
                response.setWarning(true);
                return response;
            }

            item = _repository.save(item);
            Optional<OD> itemOd = _repositoryOd.findById(item.getOd().getId());

            if (itemOd.isPresent() && !isNullOrEmpty(itemOd.get().getDigitalRouteId())) {
                if (file != null && !file.isEmpty() && !isNullOrEmpty(itemOd.get().getDigitalRouteId())) {
                    DocumentoDTO temp = documentService.saveFile(file, false, itemOd.get().getDigitalRouteId(), item.getDocumentName());
                    if (temp.getSuccess()) {
                        item.setDocumentId(temp.getId());
                        _repository.updateDocumentId(item.getId(), temp.getId());
                    }
                    else response.setExtra(temp.getMessagge());
                }
            }

            response.setItem(item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Addendum> update(Addendum item, MultipartFile file) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<Addendum>();
        try {
            Optional<Addendum> _item = _repository.findById(item.getId());
            Optional<OD> itemOd = _repositoryOd.findById(item.getOd().getId());
            if (_item.isPresent()) {
                item.setDuration("");
                if (item.getEmissionDate() != null && item.getEndDate() != null) {
                    Period diff = Period.between(item.getEmissionDate(), item.getEndDate());
                    String duration = (diff.getYears() > 0 ? diff.getYears() + " año(s) " : "") + (diff.getMonths() > 0 ? diff.getMonths()+ " mes(es) " : "") + (diff.getDays() > 0 ? diff.getDays() + " día(s)" : "");
                    item.setDuration(duration);
                }

                if (_item.get().getOrder() != item.getOrder()) {
                    Optional<Addendum> itemTemp = _repository.findByOdIdAndOrder(item.getOd().getId(), item.getOrder());
                    if (itemTemp.isPresent() ){
                        response.setMessage("Ya se cuenta con una adenda con el orden ingresado.");
                        response.setSuccess(false);
                        response.setWarning(true);
                        return response;
                    }
                }

                item = _repository.save(item);

                if (itemOd.isPresent() && !isNullOrEmpty(itemOd.get().getDigitalRouteId())) {
                    if (file != null && !file.isEmpty() && !isNullOrEmpty(itemOd.get().getDigitalRouteId())) {
                        DocumentoDTO temp = documentService.saveFile(file, false, itemOd.get().getDigitalRouteId(), item.getDocumentName());
                        if (temp.getSuccess()) {
                            item.setDocumentId(temp.getId());
                            _repository.updateDocumentId(item.getId(), temp.getId());
                        }
                        else response.setExtra(temp.getMessagge());
                    }
                }

                response.setItem(item);
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

    public ResponseEntity<Addendum> delete(int id) throws Exception {
        ResponseEntity<Addendum> response = new ResponseEntity<Addendum>();
        try {
            Optional<Addendum> item = _repository.findById(id);
            if (item.isPresent()){
                _repository.deleteById(id);
                //_repository.updateIsDeleted(id, true);
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

    public String getFileName (String fileId) throws Exception {
        try {
            String fileName = _repository.getFileNameByFileId(fileId);
            return URLEncoder.encode(fileName.trim(), "UTF-8").replace("+", "%20");
        }catch (Exception ex) {
            return "";
        }
    }
}
