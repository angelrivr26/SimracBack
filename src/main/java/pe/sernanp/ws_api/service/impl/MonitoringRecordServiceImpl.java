package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.*;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.*;
import pe.sernanp.ws_api.repository.*;
import pe.sernanp.ws_api.service.DocumentService;
import pe.sernanp.ws_api.service.MonitoringRecordService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonitoringRecordServiceImpl implements MonitoringRecordService {
    @Autowired
    MonitoringRecordRepository _repository;
    final int APROBADO_VALUE = 84;
    @Autowired
    DocumentService documentService;
    @Autowired
    ODFiscalObligationRepository _fiscalObligationRepository;
    @Autowired
    MRComplianceRepository mrComplianceRepository;
    @Autowired
    ODRepository odRepository;
    public ResponseEntity<MonitoringRecord> findAll() throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<MonitoringRecord>();
        try {
            List<MonitoringRecord> items = _repository.findAll();
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MonitoringRecord> findById(int id) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<MonitoringRecord>();
        try {
            Optional<MonitoringRecord> item = _repository.findById(id);
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

    public ResponseEntity<MonitoringRecord> listByType(int typeId) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<MonitoringRecord>();
        try {
            List<MonitoringRecord> items = _repository.findByOdTypeId(typeId);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MonitoringRecord> listByResource(int resourceId) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<MonitoringRecord>();
        try {
            List<MonitoringRecord> items = _repository.findByResouceId(resourceId);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MonitoringRecord> save(MonitoringRecord item) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<MonitoringRecord>();

        try {
            Optional<MonitoringRecord> _itemOptional = _repository.findById(item.getId());
            String _returnMenssage = getValidProperties(item);
            OD od = odRepository.findById(item.getOd().getId()).get();
            if(!_returnMenssage.equals("")){
                response.setMessage(_returnMenssage);
                response.setWarning(true);
                response.setSuccess(false);
                return response;
            }
            if (_itemOptional.isPresent()) {
                MonitoringRecord _itemUpdate = _repository.save(item);
                response.setItem(_itemUpdate);
                response.setMessage("Se actualizó el registro correctamente");
                if(od.getType().getId() == 19){
                    setCompromise(_itemUpdate);
                }
            }
            else {
                MonitoringRecord _item = _repository.save(item);
                response.setItem(_item);
                response.setMessage("Registro exitoso");
                if(od.getType().getId() == 19){
                    setCompromise(_item);
                }
            }

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MonitoringRecord> update(MonitoringRecord item) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<MonitoringRecord>();
        try {
            Optional<MonitoringRecord> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                MonitoringRecord _itemUpdate = _repository.save(item);
                OD od = odRepository.findById(item.getOd().getId()).get();
                if(od.getType().getId() == 19){
                    setCompromise(_itemUpdate);
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

    public ResponseEntity<MonitoringRecord> delete(int id) throws Exception {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<MonitoringRecord>();
        try {
            Optional<MonitoringRecord> item = _repository.findById(id);
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
            if(ex.getMessage().equals("fk_informe_sup_acta_supervisio")){
                menssage = "No se puede eliminar este acta por que tiene imformes relacionados.";
            }
            response.setMessage(menssage);
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<MonitoringRecord> finalizeRegister(int id) {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<MonitoringRecord>();
        MonitoringRecord item = new MonitoringRecord();
        ListDetail state = new ListDetail()
                .builder()
                .id(APROBADO_VALUE)
                .build();
        try {
            Optional<MonitoringRecord> _item = _repository.findById(id);
            if (_item.isPresent()) {
                item = _item.get();
                item.setState(state);
                MonitoringRecord _itemUpdate = _repository.save(item);
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
    public ResponseEntity<MonitoringRecordDTO> search(MonitoringRecordRequestDTO item, PaginatorEntity paginator) {
        ResponseEntity<MonitoringRecordDTO> response = new ResponseEntity<MonitoringRecordDTO>();
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
            Page<MonitoringRecordDTO> pag = this._repository.search(
                    item.getOd(),
                    item.getAnp(),
                    item.getTypeId(),
                    item.getState(),
                    item.getBeneficiary(),
                    item.getTitleName(),
                    item.getOpeningDate(),
                    item.getClosingDate(),
                    iStartDate,
                    iEndDate,
                    page);
            List<MonitoringRecordDTO> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());
            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar los seguimientos");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public ResponseEntity<MonitoringRecord> saveWithFile(MonitoringRecord item2, MultipartFile file, String folderId) {
        ResponseEntity<MonitoringRecord> response = new ResponseEntity<>();
        try{
            OD od = odRepository.findById(item2.getOd().getId()).get();
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
            MonitoringRecord itemSave = _repository.save(item2);
            if(od.getType().getId() == 19){
                setCompromise(itemSave);
            }
            response.setItem(itemSave);
            response.setMessage("Se realizo correctamente la operación.");
        }catch (Exception ex){
            response.setMessage("Ocurrio un error al guardar el acta de seguimiento");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    private void setCompromise(MonitoringRecord item){
        try{
            List<FiscalObligationDTO> items = _fiscalObligationRepository.listByODId(item.getOd().getId(), false);
            items
                    .stream()
                    .forEach(t->{
                        Optional<MRCompliance> itemFount = mrComplianceRepository.findByMonitoringRecordIdAndOdFiscalObligationId(item.getId(),t.getId()).stream().findFirst();
                        if(!itemFount.isPresent()) {
                            ODFiscalObligation odFiscalObligation = new ODFiscalObligation()
                                    .builder()
                                    .id(t.getId())
                                    .build();
                            MRCompliance mrCompliance = new MRCompliance()
                                    .builder()
                                    .monitoringRecord(item)
                                    .odFiscalObligation(odFiscalObligation).build();
                            mrComplianceRepository.save(mrCompliance);
                        }
                    });
        }catch (Exception ex){

        }
    }
    private String getValidProperties (MonitoringRecord item){
        String _message = "";
        if(item.getSupervisionRecordCode() == null || item.getSupervisionRecordCode().equals("")){
            _message= "El numero de acta no puede ser null ni vacio.";
            return _message;
        }
        if(item.getOd() == null || item.getOd().getId() == 0){
            _message= "se debe ingresar un codigo de otorgamiento.";
            return _message;
        }
        if(item.getSpecialDocumentNumber() == null || item.getSpecialDocumentNumber().equals("")){
            _message= "se debe ingresar un numero de dni para buscar al especialista.";
            return _message;
        }
        if(item.getSpecialName() == null || item.getSpecialName().equals("")){
            _message= "se debe realizar la busqueda por numero de documento para encontrar al especialista.";
            return _message;
        }
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
