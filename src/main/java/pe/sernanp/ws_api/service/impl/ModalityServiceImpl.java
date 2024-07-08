package pe.sernanp.ws_api.service.impl;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.dto.ModalityDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.Modality;
import pe.sernanp.ws_api.repository.ModalityRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.ModalityService;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class ModalityServiceImpl extends BaseServiceImpl implements ModalityService {
    @Autowired
    ModalityRepository _repository;

    @Autowired
    DocumentService documentService;

    public ResponseEntity<ModalityDTO> search(Modality item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<ModalityDTO> response = new ResponseEntity<ModalityDTO>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            int stateId = item.getState() == null ? 0 : item.getState().getId();
            int typeId = item.getType() == null ? 0 : item.getType().getId();
            int flagValidity = item.getFlagValidity() == null ? 2 : (item.getFlagValidity() ? 1 : 0);
            int flagDraft = item.getFlagDraft() == null ? 2 : (item.getFlagDraft() ? 1 : 0);
            boolean isDeleted = item.getIsDeleted()== null ? false : item.getIsDeleted();
            Page<ModalityDTO> pag = this._repository.search(typeId, stateId, flagValidity, flagDraft, isDeleted, page);
            List<ModalityDTO> items = pag.getContent();
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

    public ResponseEntity<Modality> findAll() throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<Modality>();
        try {
            List<Modality> items = _repository.findByFlagDraftAndIsDeleted(false, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar las modalidades");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Modality> findByTupa(boolean isTupa) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<Modality>();
        try {
            List<Modality> items = _repository.findByFlagTupaAndFlagDraftAndIsDeleted(isTupa,false, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar las modalidades");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Modality> findById(int id) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<Modality>();
        try {
            Optional<Modality> item = _repository.findById(id);
            if (item.isPresent())
                response.setItem(item.get());
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle de la modalidad");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Modality> save(Modality item, MultipartFile file) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<Modality>();
        try {
            item.setFlagDraft(item.getFlagDraft() == null ? false : item.getFlagDraft());
            if (item.getFlagDraft()){
                item.setState(new ListDetail());
                item.getState().setId(20);
                item.setType((item.getType() == null || item.getType().getId() == 0) ? null : item.getType());
                item.setDescription(item.getDescription() == null ? "" : item.getDescription());
                item.setSustentationDocumentName(item.getSustentationDocumentName() == null ? "" : item.getSustentationDocumentName());
                item.setShortName(item.getShortName() == null ? "" : item.getShortName());
                item.setTitleEnables(item.getTitleEnables() == null ? "" : item.getTitleEnables());
            } else {
                boolean validate = (item.getType() == null || item.getType().getId() == 0
                    || isNullOrEmpty(item.getDescription()) || isNullOrEmpty(item.getSustentationDocumentName())
                    || isNullOrEmpty(item.getTitleEnables()) || isNullOrEmpty(item.getAcronym()));
                if (validate) {
                    response.setMessage("Se debe completar lo campos obligatorios de la modalidad.");
                    response.setSuccess(false);
                    return response;
                }
                item.setState(new ListDetail());
                item.getState().setId(21);
            }

            int _id = _repository.getCurrentValSequence();
            item.setCode(String.format("MD-%0" + 3 + "d", _id));
            item = _repository.save(item);

            if (file.isEmpty() == false) {
                DocumentoDTO temp = documentService.saveFile(file, true, null, item.getSustentationDocumentName());
                if (temp.getSuccess()){
                    item.setSustentationDocumentId(temp.getId());
                    _repository.updateDocumetId(item.getId(), temp.getId());
                }
                else {
                    response.setExtra(temp.getMessagge());
                }
            }
            response.setItem(item);
            response.setMessage("Registro exitoso");
        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe una modalidad con las siglas " + item.getAcronym());
                response.setSuccess(false);
                response.setWarning(true);
                response.setExtra(ex.getMessage());
            } else {
                response.setMessage("Ocurrio un error al guardar la modalidad");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<Modality> update(Modality item, MultipartFile file) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<Modality>();
        try {
            item.setFlagDraft(item.getFlagDraft() == null ? false : item.getFlagDraft());
            Optional<Modality> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item.setState(new ListDetail());
                item.getState().setId(20);
                if (item.getFlagDraft()) {
                    item.setType((item.getType() == null || item.getType().getId() == 0) ? null : item.getType());
                    item.setDescription(item.getDescription() == null ? "" : item.getDescription());
                    item.setSustentationDocumentName(item.getSustentationDocumentName() == null ? "" : item.getSustentationDocumentName());
                    item.setShortName(item.getShortName() == null ? "" : item.getShortName());
                    item.setTitleEnables(item.getTitleEnables() == null ? "" : item.getTitleEnables());
                } else {
                    boolean validate = (item.getType() == null || item.getType().getId() == 0
                            || isNullOrEmpty(item.getDescription()) || isNullOrEmpty(item.getSustentationDocumentName())
                            || isNullOrEmpty(item.getTitleEnables()) || isNullOrEmpty(item.getAcronym()));
                    if (validate) {
                        response.setMessage("Se debe completar lo campos obligatorios de la modalidad.");
                        response.setSuccess(false);
                        return response;
                    }
                    item.setState(new ListDetail());
                    item.getState().setId(21);
                }
                item.setSustentationDocumentId(_item.get().getSustentationDocumentId());
                item.setCode(_item.get().getCode());
                item = _repository.save(item);

                if (file != null && file.isEmpty() == false) {
                    DocumentoDTO temp = documentService.saveFile(file, true, null, item.getSustentationDocumentName());
                    if (temp.getSuccess()){
                        item.setSustentationDocumentId(temp.getId());
                        _repository.updateDocumetId(item.getId(), temp.getId());
                    }
                    else {
                        response.setExtra(temp.getMessagge());
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
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe una modalidad con las siglas " + item.getAcronym());
                response.setSuccess(false);
                response.setWarning(true);
                response.setExtra(ex.getMessage());
            } else {
                response.setMessage("Ocurrio un error al guardar la modalidad");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<Modality> delete(int id) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<Modality>();
        try {
            Optional<Modality> item = _repository.findById(id);
            if (item.isPresent()){
                try {
                    _repository.deleteById(id);
                    response.setMessage("Se elimino el registro correctamente.");
                    //_repository.updateIsDeleted(id, false);
                }
                catch (Exception ex) {
                    if (((ConstraintViolationException) ex.getCause()).getSQLState().equals("23503")) {
                        response.setMessage("No se puede eliminar una modalidad mientras es utilizada.");
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
            }
            else{
                response.setMessage("El registro no existe.");
                response.setSuccess(false);
                response.setWarning(true);
            }
        }
        catch (Exception ex) {
            if (ex.getMessage().contains("ConstraintViolationException")) {
            //if (ex.getErrorCode() == 23503) {
                _repository.updateIsDeleted(id, true);
                response.setMessage("Modalidad no se puede eliminar por vinculación en Configuración ANP, se desactivo la modalidad.");
                response.setSuccess(true);
            } else {
                response.setMessage("Ocurrio un error al eliminar la modalidad");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<Modality> listByAnpConfig(int typeId) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<Modality>();
        try {
            List<Modality> items = _repository.listByTypeIdAndAnpConfig(typeId,false, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar las modalidades");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Modality> listByType(int typeId) throws Exception {
        ResponseEntity<Modality> response = new ResponseEntity<Modality>();
        try {
            List<Modality> items = _repository.listByTypeId(typeId,false, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar las modalidades");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ByteArrayInputStream export(Modality item) throws Exception {
        String[] columns = { "CODIGO", "TIPO DE APROVECHAMIENTO", "NOMBRE DE LA MODALIDAD", "NOMBRE CORTO", "TITULO HABILITANTE", "VIGENTE", "ESTADO", "PREQUISITOS" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Modalidad");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int stateId = item.getState() == null ? 0 : item.getState().getId();
        int typeId = item.getType() == null ? 0 : item.getType().getId();
        int flagValidity = item.getFlagValidity() == null ? 2 : (item.getFlagValidity() ? 1 : 0);
        int flagDraft = item.getFlagDraft() == null ? 2 : (item.getFlagDraft() ? 1 : 0);
        boolean isDeleted = item.getIsDeleted()== null ? false : item.getIsDeleted();

        List<ModalityDTO> items = this._repository.search2(typeId, stateId, flagValidity, flagDraft, isDeleted);

        int initRow = 1;
        for (ModalityDTO _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getCode());
            row.createCell(1).setCellValue(_item.getType().getName());
            row.createCell(2).setCellValue(_item.getDescription());
            row.createCell(3).setCellValue(_item.getShortName());
            row.createCell(4).setCellValue(_item.getTitleEnables());
            row.createCell(5).setCellValue(_item.getFlagValidity() ? "Vigente" : "No vigente");
            row.createCell(6).setCellValue(_item.getFlagDraft() ? "Pendiente" : "Aprobado");
            row.createCell(7).setCellValue(_item.getRequirementCount());
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
