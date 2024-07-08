package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.dto.SupervisionRecordDTO;
import pe.sernanp.ws_api.dto.SupervisionRecordRequestDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.SupervisionRecord;
import pe.sernanp.ws_api.repository.SRVerifiedFactRepository;
import pe.sernanp.ws_api.repository.SupervisionRecordRepository;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.SupervisionRecordService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SupervisionRecordServiceImpl implements SupervisionRecordService {
    final int APROBADO_VALUE = 84;
    final int PENDIENTE_VALUE = 99;
    @Autowired
    SupervisionRecordRepository _repository;
    @Autowired
    DocumentService documentService;

    @Autowired
    SRVerifiedFactRepository _repositorySRVerifiedFact;

    public ResponseEntity<SupervisionRecord> findAll() throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<SupervisionRecord>();
        try {
            List<SupervisionRecord> items = _repository.findAll();
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SupervisionRecord> findById(int id) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<SupervisionRecord>();
        try {
            Optional<SupervisionRecord> item = _repository.findById(id);
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

    public ResponseEntity<SupervisionRecord> listByType(int typeId) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<SupervisionRecord>();
        try {
            List<SupervisionRecord> items = _repository.findByOdTypeId(typeId);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SupervisionRecord> save(SupervisionRecord item) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<SupervisionRecord>();
        try {
            Optional<SupervisionRecord> _itemOptional = _repository.findById(item.getId());
            String _returnMenssage = getValidProperties(item);
            if(!_returnMenssage.equals("")) {
                response.setMessage(_returnMenssage);
                response.setWarning(true);
                response.setSuccess(false);
                return response;
            }
            if (_itemOptional.isPresent()) {
                SupervisionRecord _itemUpdate = _repository.save(item);
                response.setItem(_itemUpdate);
                response.setMessage("Se actualizó el registro correctamente");
            }
            else {
                SupervisionRecord _item = _repository.save(item);
                _repositorySRVerifiedFact.generateMassive(_item.getId(), _item.getOd().getId(), PENDIENTE_VALUE);
                response.setItem(_item);
                response.setMessage("Registro exitoso");
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<SupervisionRecord> update(SupervisionRecord item) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<SupervisionRecord>();
        try {
            Optional<SupervisionRecord> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                SupervisionRecord _itemUpdate = _repository.save(item);
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

    public ResponseEntity<SupervisionRecord> delete(int id) throws Exception {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<SupervisionRecord>();
        try {
            Optional<SupervisionRecord> item = _repository.findById(id);
            if (item.isPresent()){
                _repository.deleteById(id);
                response.setMessage("Se elimino el registro correctamente.");
            }
            else{
                response.setMessage("El registro no existe.");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            String menssage = "Ocurrio un error al eliminar";
            if(ex.getMessage().contains("fk_informe_sup_acta_supervisio")){
                menssage = "No se puede eliminar este acta por que tiene imformes relacionados.";
            }
            response.setMessage(menssage);
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<SupervisionRecordDTO> search(SupervisionRecordRequestDTO item, PaginatorEntity paginator) {
        ResponseEntity<SupervisionRecordDTO> response = new ResponseEntity<SupervisionRecordDTO>();
        int iStartDate = 0;
        int iEndDate = 0;
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit(), Sort.by(Sort.Direction.DESC, "srl_id"));
            if(item.getOpeningDate() == null){
                iStartDate = 1;
                item.setOpeningDate(new Date());
            }
            if(item.getClosingDate() == null){
                iEndDate = 1;
                item.setClosingDate(new Date());
            }
            Page<SupervisionRecordDTO> pag = this._repository.search(
                    item.getAnp(),
                    item.getTypeId(),
                    item.getOd(),
                    item.getSupervisionType(),
                    item.getOpeningDate(),
                    item.getClosingDate(),
                    item.getRecordNumber(),
                    item.getBeneficiary(),
                    iStartDate,
                    iEndDate,
                    page);
            List<SupervisionRecordDTO> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());
            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar las od");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public ResponseEntity<SupervisionRecord> finalizeRegister(int id) {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<SupervisionRecord>();
        SupervisionRecord item = new SupervisionRecord();
        ListDetail state = new ListDetail()
                .builder()
                .id(APROBADO_VALUE)
                .build();
        try {
            Optional<SupervisionRecord> _item = _repository.findById(id);
            if (_item.isPresent()) {
                item = _item.get();
                item.setState(state);
                SupervisionRecord _itemUpdate = _repository.save(item);
                response.setItem(_itemUpdate);
                response.setMessage("Se finalizo el registro correctamente");
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
    public ResponseEntity<SupervisionRecord> saveWithFile(SupervisionRecord item2, MultipartFile file, String folderId) {
        ResponseEntity<SupervisionRecord> response = new ResponseEntity<>();
        try{
            if (file != null && !file.isEmpty()) {
                DocumentoDTO temp = documentService.saveFile(file, false, folderId, file.getOriginalFilename());
                if (temp.getSuccess()){
                    item2.setDigitalFileCode(temp.getId());
                    item2.setDigitalFileSize(file.getSize());
                    item2.setDigitalFileName(file.getOriginalFilename());
                }
            }
            String _returnMenssage = getValidProperties(item2);
            if(!_returnMenssage.equals("")){
                response.setMessage(_returnMenssage);
                response.setWarning(true);
                response.setSuccess(false);
                return response;
            }
            SupervisionRecord itemSave = _repository.save(item2);
            response.setItem(itemSave);
            _repositorySRVerifiedFact.generateMassive(itemSave.getId(), itemSave.getOd().getId(), PENDIENTE_VALUE);
            response.setMessage("Se realizo correctamente la operación.");
        }catch (Exception ex){
            response.setMessage("Ocurrio un error al guardar el acta de supervision");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    private String getValidProperties (SupervisionRecord item){
        String _message = "";
        if(item.getRecordCode() == null || item.getRecordCode().equals("")){
            _message= "El numero de experiente no puede ser null ni vacio.";
            return _message;
        }
        if(item.getSupervisionRecordCode() == null || item.getSupervisionRecordCode().equals("")){
            _message= "El numero de acta no puede ser null ni vacio.";
            return _message;
        }
        /*if(item.getType() == null || item.getType().getId() == 0){
            _message= "se debe seleccionar un tipo de Otorgamiento.";
            return _message;
        }*/
        if(item.getOd() == null || item.getOd().getId() == 0){
            _message= "se debe ingresar un codigo de otorgamiento.";
            return _message;
        }
        /*if(item.getAnpName() == null || item.getAnpName().equals("")){
            _message= "el otorgamiento debe ";
            return _message;
        }*/
        /*if(item.getAnpCode() == null || item.getAnpCode().equals("")){
            _message= "El numero de experiente no puede ser null ni vacio";
            return _message;
        }*/
        if(item.getOpeningDate() == null){
            _message= "se debe seleccionar una fecha de apertura.";
            return _message;
        }
        if(item.getClosingDate() == null){
            _message= "se debe seleccionar una fecha de cierre";
            return _message;
        }
        return _message;
    }
}
