package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ModalityRequirement;
import pe.sernanp.ws_api.repository.ModalityRequirementRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.ModalityRequirementService;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class ModalityRequirementServiceImpl extends BaseServiceImpl implements ModalityRequirementService {
    @Autowired
    ModalityRequirementRepository _repository;

    @Autowired
    DocumentService documentService;

    public ResponseEntity<ModalityRequirement> findById(int id) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<ModalityRequirement>();
        try {
            Optional<ModalityRequirement> item = _repository.findById(id);
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

    public ResponseEntity<ModalityRequirement> findByModalityId(int id) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<ModalityRequirement>();
        try {
            List<ModalityRequirement> items = _repository.findByModalityIdAndIsDeleted(id, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ModalityRequirement> save(ModalityRequirement item, MultipartFile documentFile, MultipartFile templateFile) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<ModalityRequirement>();
        try {
            ModalityRequirement _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");

            if (documentFile != null && documentFile.isEmpty() == false) {
                DocumentoDTO temp = documentService.saveFile(documentFile, true, null, item.getDocumentName());
                if (temp.getSuccess()) {
                    item.setDocumentRouteId(temp.getId());
                    _repository.updateDocumetId(item.getId(), temp.getId());
                }
                else response.setExtra(temp.getMessagge());
            }

            if (templateFile != null && templateFile.isEmpty() == false) {
                DocumentoDTO temp = documentService.saveFile(templateFile, true, null, item.getTemplateName());
                if (temp.getSuccess()) {
                    item.setTemplateRouteId(temp.getId());
                    _repository.updateTemplateId(item.getId(), temp.getId());
                }
                else response.setExtra(temp.getMessagge());
            }

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ModalityRequirement> update(ModalityRequirement item, MultipartFile documentFile, MultipartFile templateFile) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<ModalityRequirement>();
        try {
            Optional<ModalityRequirement> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item.setDocumentRouteId(_item.get().getDocumentRouteId());
                if (item.getTemplateVariables().size() > 0)
                    item.getTemplateVariables().forEach((u) -> u.setModalityRequirementId(item.getId()));

                ModalityRequirement _itemUpdate = _repository.save(item);
                response.setItem(_itemUpdate);
                response.setMessage("Se actualizó el registro correctamente");

                if (documentFile != null && documentFile.isEmpty() == false) {
                    DocumentoDTO temp = documentService.saveFile(documentFile, true, null, item.getDocumentName());
                    if (temp.getSuccess()) {
                        item.setDocumentRouteId(temp.getId());
                        _repository.updateDocumetId(item.getId(), temp.getId());
                    }
                    else response.setExtra(temp.getMessagge());
                }

                if (templateFile != null && templateFile.isEmpty() == false) {
                    DocumentoDTO temp = documentService.saveFile(templateFile, true, null, item.getTemplateName());
                    if (temp.getSuccess()) {
                        item.setTemplateRouteId(temp.getId());
                        _repository.updateTemplateId(item.getId(), temp.getId());
                    }
                    else response.setExtra(temp.getMessagge());
                }
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

    public ResponseEntity<ModalityRequirement> delete(int id) throws Exception {
        ResponseEntity<ModalityRequirement> response = new ResponseEntity<ModalityRequirement>();
        try {
            Optional<ModalityRequirement> item = _repository.findById(id);
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

    public ByteArrayInputStream export(int modalityId) throws Exception {
        String[] columns = { "PREREQUISITO", "DESCRIPCION", "OBLIGATORIO", "DOCUMENTO" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Modalidad_PreRequisitos");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<ModalityRequirement> items = _repository.findByModalityIdAndIsDeleted(modalityId, false);

        int initRow = 1;
        for (ModalityRequirement _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getCode());
            row.createCell(1).setCellValue(_item.getDescription());
            row.createCell(2).setCellValue(_item.getFlagMandatory() ? "Si" : "No");
            row.createCell(3).setCellValue(_item.getDocumentName());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
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
