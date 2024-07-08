package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.FiscalObligationDTO;
import pe.sernanp.ws_api.dto.ListDTO;
import pe.sernanp.ws_api.dto.MatrizObligationDTO;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.FiscalObligation;
import pe.sernanp.ws_api.model.MatrizObligation;
import pe.sernanp.ws_api.model.ODFiscalObligation;
import pe.sernanp.ws_api.repository.FiscalObligationRepository;
import pe.sernanp.ws_api.repository.MatrizObligationRepository;
import pe.sernanp.ws_api.repository.MatrizOdFiscalObligationRepository;
import pe.sernanp.ws_api.repository.ODFiscalObligationRepository;
import pe.sernanp.ws_api.service.MatrizObligationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class MatrizObligationServiceImpl extends BaseServiceImpl implements MatrizObligationService {
    @Autowired
    MatrizObligationRepository _repository;
    @Autowired
    ODFiscalObligationRepository _repositoryODFiscalObligation;
    @Autowired
    FiscalObligationRepository _repositoryFiscalObligation;

    @Autowired
    MatrizOdFiscalObligationRepository _repositoryMatrizOdFiscalObligacionRepository;

    public ResponseEntity<MatrizObligationDTO> search(MatrizObligation item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<MatrizObligationDTO> response = new ResponseEntity<MatrizObligationDTO>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            String anpCodes = isNullOrEmpty(item.getOd().getAnpConfigIds()) ? "" : item.getOd().getAnpConfigIds();
            int typeId = item.getOd().getType() == null ? 0 : item.getOd().getType().getId();
            int modalityId = item.getOd().getModality() == null ? 0 : item.getOd().getModality().getId();
            int beneficiaryId = item.getOd().getBeneficiary() == null ? 0 : item.getOd().getBeneficiary().getId();
            int stateId = item.getOd().getState() == null ? 0 : item.getOd().getState().getId();
            String resourceIds = isNullOrEmpty(item.getOd().getResourceAnpConfigIds()) ? "" : "{" +item.getOd().getResourceAnpConfigIds() + "}";
            int resourceTypeId = item.getResourceType() == null ? 0 : item.getResourceType().getId();
            boolean isDeleted = false;

            Page<MatrizObligationDTO> pag = this._repository.search(anpCodes, typeId, modalityId, beneficiaryId, resourceIds, stateId, item.getOd().getCode(), resourceTypeId, isDeleted, page);
            List<MatrizObligationDTO> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());
            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar las matrices.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MatrizObligation> findAll() throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<MatrizObligation>();
        try {
            List<MatrizObligation> items = _repository.findAll();
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ListDTO> listRecordCode() throws Exception {
        ResponseEntity<ListDTO> response = new ResponseEntity<ListDTO>();
        try {
            List<ListDTO> items = _repository.listRecordCode();
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MatrizObligation> findById(int id) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<MatrizObligation>();
        try {
            Optional<MatrizObligation> item = _repository.findById(id);
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

    public ResponseEntity<MatrizObligation> save(MatrizObligation item) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<MatrizObligation>();
        try {
            List<ODFiscalObligation> itemsOdFiscalObligation = _repositoryODFiscalObligation.findByOdId(item.getOd().getId());
            //item.setOdFiscalObligations(new HashSet<>(itemsOdFiscalObligation));
            item = _repository.save(item);

            for (ODFiscalObligation itemOdFiscalObligation :itemsOdFiscalObligation) {
                _repositoryMatrizOdFiscalObligacionRepository.save2(item.getId(), itemOdFiscalObligation.getId());
            }

            response.setItem(item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe una matriz de obligación parecida registrada.");
                response.setSuccess(false);
                response.setWarning(true);
                return response;
            } else {
                response.setMessage("Ocurrio un error al guardar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<MatrizObligation> update(MatrizObligation item) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<MatrizObligation>();
        try {
            Optional<MatrizObligation> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                item = _repository.save(item);
                response.setItem(item);
                response.setMessage("Se actualizó el registro correctamente");
            } else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            if (ex.getMessage().contains("uq_")){
                response.setMessage("Ya existe una matriz de obligación parecida registrada.");
                response.setSuccess(false);
                response.setWarning(true);
                return response;
            } else {
                response.setMessage("Ocurrio un error al actualizar.");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<MatrizObligation> delete(int id) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<MatrizObligation>();
        try {
            Optional<MatrizObligation> item = _repository.findById(id);
            if (item.isPresent()) {
                _repositoryMatrizOdFiscalObligacionRepository.deleteByMatrizObligation(id);
                _repository.deleteById(id);
                response.setMessage("Se elimino el registro correctamente.");
                //_repository.updateIsDeleted(id, false);
            } else {
                response.setMessage("El registro no existe.");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            if (ex.getMessage().contains("fk_informe")){
                response.setMessage("No se puede eliminar un plan mientras es utilizado por un informe.");
                response.setSuccess(false);
                response.setWarning(true);
            } else {
                response.setMessage("Ocurrio un error al eliminar");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        return response;
    }

    public ResponseEntity<MatrizObligation> saveFiscalObligation(MatrizObligation item) throws Exception {
        ResponseEntity<MatrizObligation> response = new ResponseEntity<MatrizObligation>();
        try {
            FiscalObligation tempFiscalObligation = _repositoryFiscalObligation.save(item.getFiscalObligation());
            ODFiscalObligation tempODFiscalObligation = new ODFiscalObligation();
            tempODFiscalObligation.setFiscalObligation(tempFiscalObligation);
            tempODFiscalObligation = _repositoryODFiscalObligation.save(tempODFiscalObligation);

            item.setFiscalObligation(tempFiscalObligation);
            _repository.insertMatriz(item.getId(), tempODFiscalObligation.getId());

            response.setItem(item);
            response.setMessage("Registro exitoso");

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<FiscalObligationDTO> listFiscalObligation(int id) throws Exception {
        ResponseEntity<FiscalObligationDTO> response = new ResponseEntity<FiscalObligationDTO>();
        try {
            List<FiscalObligationDTO> items = _repository.listForId(id);
            response.setItems(items);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error al buscar.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());

        }
        return response;
    }

    public ResponseEntity deleteFiscalObligation(MatrizObligation item) throws Exception {
        ResponseEntity response = new ResponseEntity<MatrizObligation>();
        try {
            Integer mFiscalObligationId = _repository.findMatrizFiscalObligationById(item.getId(), item.getFiscalObligation().getId());
            if (mFiscalObligationId != null && mFiscalObligationId > 0) {
                _repository.deleteMatrizFiscalObligation(item.getId(), item.getFiscalObligation().getId());
                response.setMessage("Se elimino el registro correctamente.");
                response.setSuccess(true);
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
            return response;
        }
        return response;
    }

    public ResponseEntity deleteMassiveFiscalObligation(List<MatrizObligation> items) throws Exception {
        ResponseEntity response = new ResponseEntity<MatrizObligation>();
        try {
            for (MatrizObligation item: items) {
                Integer mFiscalObligationId = _repository.findMatrizFiscalObligationById(item.getId(), item.getFiscalObligation().getId());
                if (mFiscalObligationId != null && mFiscalObligationId > 0) {
                    _repository.deleteMatrizFiscalObligation(item.getId(), item.getFiscalObligation().getId());
                }
            }
            response.setMessage("Registro(s) eliminado(s) correctamente.");
            response.setSuccess(true);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al eliminar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
            return response;
        }
        return response;
    }

    public ByteArrayInputStream export(MatrizObligation item) throws Exception {
        String[] columns = { "TIPO", "ANP", "TITULO", "RECURSO", "BENEFICIARIO", "FECHA EMISION", "ESTADO" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("matriz");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        String anpCodes = isNullOrEmpty(item.getOd().getAnpConfigIds()) ? "" : item.getOd().getAnpConfigIds();
        int typeId = item.getType() == null ? 0 : item.getType().getId();
        int modalityId = item.getOd().getModality() == null ? 0 : item.getOd().getModality().getId();
        int beneficiaryId = item.getOd().getBeneficiary() == null ? 0 : item.getOd().getBeneficiary().getId();
        int stateId = item.getOd().getState() == null ? 0 : item.getOd().getState().getId();
        String resourceIds = isNullOrEmpty(item.getOd().getResourceAnpConfigIds()) ? "" : item.getOd().getResourceAnpConfigIds();
        int resourceTypeId = item.getResourceType() == null ? 0 : item.getResourceType().getId();
        boolean isDeleted = false;

        List<MatrizObligationDTO> items = this._repository.search2(anpCodes, typeId, modalityId, beneficiaryId, resourceIds, stateId, item.getOd().getCode(), resourceTypeId, isDeleted);

        int initRow = 1;
        for (MatrizObligationDTO _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getTypeName());
            row.createCell(1).setCellValue(_item.getAnpNames());
            row.createCell(2).setCellValue(_item.getTitleEnables());
            row.createCell(3).setCellValue(_item.getResourceNames());
            row.createCell(4).setCellValue(_item.getBeneficiaryName());
            row.createCell(5).setCellValue(_item.getSignatureDate());
            row.createCell(6).setCellValue(_item.getStateName());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }

    public ByteArrayInputStream exportFiscalObligation(int id) throws Exception {
        String[] columns = { "FUENTE", "DESCRIPCIÓN", "CARACTERISTICA", "PLAZO" };

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Obligaciones_fiscales_");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<FiscalObligationDTO> items = _repository.listForId(id);

        int initRow = 1;
        for (FiscalObligationDTO _item : items) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(_item.getSourceName());
            row.createCell(1).setCellValue(_item.getDescription());
            row.createCell(2).setCellValue(_item.getCharacteristic());
            row.createCell(3).setCellValue(_item.getExecutionTerm());
            initRow++;
        }
        workbook.write(stream);
        workbook.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
