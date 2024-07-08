package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.EvaluationEconomic;
import pe.sernanp.ws_api.model.ProcedureAssign;
import pe.sernanp.ws_api.repository.EvaluationEconomicRepository;
import pe.sernanp.ws_api.repository.ProcedureAssignRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.EvaluationEconomicService;

import java.net.URLEncoder;
import java.util.Optional;

@Service
public class EvaluationEconomicServiceImpl extends BaseServiceImpl implements EvaluationEconomicService {

    @Autowired
    EvaluationEconomicRepository _repository;
    @Autowired
    DocumentService documentService;
    @Autowired
    ProcedureAssignRepository _repositoryAssign;

    public ResponseEntity<EvaluationEconomic> save(EvaluationEconomic item, MultipartFile file) throws Exception {
        ResponseEntity<EvaluationEconomic> response = new ResponseEntity<EvaluationEconomic>();
        try {
            EvaluationEconomic _item = _repository.save(item);

            if (file != null && file.isEmpty() == false) {
                DocumentoDTO temp = documentService.saveFile(file, true, null, item.getDocumentName());
                if (temp.getSuccess()) {
                    _item.setDocumentRouteId(temp.getId());
                    _repository.updateDocumetId(_item.getId(), temp.getId());
                }
                else response.setExtra(temp.getMessagge());
            }
            ProcedureAssign procedureAssign = this._repositoryAssign.findByProcedureIdAndRoleEvaluationId(item.getProcedure().getId(), 303);
            if (procedureAssign != null) {
                procedureAssign.setIsCompleted(true);
                this._repositoryAssign.save(procedureAssign);
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

    public ResponseEntity<EvaluationEconomic> update(EvaluationEconomic item, MultipartFile file) throws Exception {
        ResponseEntity<EvaluationEconomic> response = new ResponseEntity<EvaluationEconomic>();
        try {
            Optional<EvaluationEconomic> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                EvaluationEconomic _itemUpdate = _repository.save(item);

                if (file != null && file.isEmpty() == false) {
                    DocumentoDTO temp = documentService.saveFile(file, true, null, item.getDocumentName());
                    if (temp.getSuccess()) {
                        _itemUpdate.setDocumentRouteId(temp.getId());
                        _repository.updateDocumetId(_itemUpdate.getId(), temp.getId());
                    }
                    else response.setExtra(temp.getMessagge());
                }

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

    @Override
    public ResponseEntity<EvaluationEconomic> listByTramite(int id) throws Exception {
        ResponseEntity<EvaluationEconomic> response = new ResponseEntity<EvaluationEconomic>();
        try {
            EvaluationEconomic item = _repository.listbytramite(id);
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
