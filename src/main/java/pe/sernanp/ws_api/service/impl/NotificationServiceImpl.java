package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;

import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.Notification;
import pe.sernanp.ws_api.model.ProcedureState;
import pe.sernanp.ws_api.repository.NotificationRepository;
import pe.sernanp.ws_api.repository.ProcedureStateRepository;
import pe.sernanp.ws_api.service.NotificationService;

import java.net.URLEncoder;
import java.util.Date;

@Service
public class NotificationServiceImpl extends BaseServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository _repository;

    @Autowired
    ProcedureStateRepository _repositoryProcedureState;

    @Override
    public ResponseEntity<Notification> save(Notification item, MultipartFile file) throws Exception {
        ResponseEntity<Notification> response = new ResponseEntity<Notification>();
        try {
            Notification _item = _repository.save(item);
            _repositoryProcedureState.update(_item.getProcedure().getId());
            ProcedureState x = new ProcedureState();
            x.setId(0);
            ListDetail type = new ListDetail();
            type.setId(305);
            x.setState(type);
            x.setDate(new Date());
            x.setProcedure(_item.getProcedure());
            x.setActive(true);
            _repositoryProcedureState.save(x);

            if (file != null && file.isEmpty() == false) {
                DocumentoDTO temp = documentService.saveFile(file, true, null, _item.getDocumentName());
                if (temp.getSuccess()) {
                    _item.setDocumentRouteId(temp.getId());
                    _repository.updateDocumetId(_item.getId(), temp.getId());
                }
                else response.setExtra(temp.getMessagge());
            }

            response.setItem(_item);
            response.setMessage("Registro exitoso");
            response.setSuccess(true);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<Notification> listByTramite(int id) throws Exception {
        ResponseEntity<Notification> response = new ResponseEntity<Notification>();
        try {
            Notification item = _repository.listbytramite(id);
            response.setItem(item);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public String getFileName(String fileId) throws Exception {
        try {
            String fileName = _repository.getFileNameByFileId(fileId);
            return URLEncoder.encode(fileName.trim(), "UTF-8").replace("+", "%20");
        } catch (Exception ex) {
            return "";
        }
    }

}
