package pe.sernanp.ws_api.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.dto.ODDTO;
import pe.sernanp.ws_api.entity.MdPFormsEntity;
import pe.sernanp.ws_api.entity.MdPOdEntity;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Modality;
import pe.sernanp.ws_api.model.OD;
import pe.sernanp.ws_api.model.ProcedureMPV;
import pe.sernanp.ws_api.repository.FormMPVRepository;
import pe.sernanp.ws_api.repository.ODRepository;
import pe.sernanp.ws_api.repository.PayFormMPVRepository;
import pe.sernanp.ws_api.repository.ProcedureRepository;
import pe.sernanp.ws_api.service.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

@Service
public class ODServiceImpl extends BaseServiceImpl implements ODService {
    @Autowired
    ODRepository _repository;

    @Autowired
    DocumentService documentService;

    @Autowired
    AnpMPVService anpMPVService;

    @Autowired
    FileMPVService fileMPVService;

    @Autowired
    BeneficiaryService beneficiaryService;

    @Autowired
    ProcedureServiceImpl procedureServiceImpl;

    @Autowired
    ProcedureRepository procedureRepository;

    @Autowired
    FormMPVRepository formMPVRepository;

    @Autowired
    PayFormMPVRepository payFormMPVRepository;

    private static int YEAR = 365;
    private static int MONTH = 30;

    public ResponseEntity<ODDTO> search(OD item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<ODDTO> response = new ResponseEntity<ODDTO>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            String anpCodes = isNullOrEmpty(item.getAnpConfigIds()) ? "" : "{" + item.getAnpConfigIds() + "}";
            String code = isNullOrEmpty(item.getCode()) ? "" : item.getCode();
            String resourceIds = isNullOrEmpty(item.getResourceAnpConfigIds()) ? "" : "{" + item.getResourceAnpConfigIds() + "}";
            int stateId = item.getState() == null ? 0 : item.getState().getId();
            int modalityId = item.getModality() == null ? 0 : item.getModality().getId();
            int typeId = item.getType() == null ? 0 : item.getType().getId();
            int beneficiaryId = item.getBeneficiary() == null ? 0 : item.getBeneficiary().getId();

            Page<ODDTO> pag = this._repository.search(code, stateId, typeId, anpCodes, modalityId, resourceIds, beneficiaryId, page);
            List<ODDTO> items = pag.getContent();
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

    public ResponseEntity<ODDTO> search(OD item) throws Exception {
        ResponseEntity<ODDTO> response = new ResponseEntity<ODDTO>();
        try {
            String anpCodes = isNullOrEmpty(item.getAnpConfigIds()) ? "" : "{" + item.getAnpConfigIds() + "}";
            String code = isNullOrEmpty(item.getCode()) ? "" : item.getCode();
            String resourceIds = isNullOrEmpty(item.getResourceAnpConfigIds()) ? "" : "{" + item.getResourceAnpConfigIds() + "}";
            int stateId = item.getState() == null ? 0 : item.getState().getId();
            int modalityId = item.getModality() == null ? 0 : item.getModality().getId();
            int typeId = item.getType() == null ? 0 : item.getType().getId();
            int beneficiaryId = item.getBeneficiary() == null ? 0 : item.getBeneficiary().getId();
            List<ODDTO> items = this._repository.search2(code, stateId, typeId, anpCodes, modalityId, resourceIds, beneficiaryId);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar las od");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }


    public ResponseEntity<OD> findAll() throws Exception {
        ResponseEntity<OD> response = new ResponseEntity<OD>();
        try {
            List<OD> items = _repository.findByFlagDraftAndIsDeleted(false, false);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar las od");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<OD> findById(int id) throws Exception {
        ResponseEntity<OD> response = new ResponseEntity<OD>();
        try {
            Optional<OD> item = _repository.findById(id);
            if (item.isPresent())
                response.setItem(item.get());
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle de la od");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<OD> findByType(int typeId) throws Exception {
        ResponseEntity<OD> response = new ResponseEntity<OD>();
        try {
            List<OD> items;
            if (typeId == 0)
                items = _repository.findByFlagDraftAndIsDeleted(false, false);
            else
                items = _repository.findByFlagDraftAndIsDeletedAndTypeId(false, false, typeId);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle de la od");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<OD> save(OD item, MultipartFile resolutionFile, MultipartFile titleRouteFile) throws Exception {
        ResponseEntity<OD> response = new ResponseEntity<OD>();
        try {
            item.setState((item.getState() == null || item.getState().getId() == 0) ? null : item.getState());
            item.setModality((item.getModality() == null || item.getModality().getId() == 0) ? null : item.getModality());
            item.setBeneficiary((item.getBeneficiary() == null || item.getBeneficiary().getId() == 0) ? null : item.getBeneficiary());
            item.setManagementPlan((item.getManagementPlan() == null || item.getManagementPlan().getId() == 0) ? null : item.getManagementPlan());
            item.setSitePlan((item.getSitePlan() != null && item.getSitePlan().getId() == 0) ? null : item.getSitePlan());

            int _id = _repository.getCurrentValSequence();
            item.setCode(String.format("OD-%0" + 3 + "d", _id));

            if (!isNullOrEmpty(item.getAnpConfigIds())) {
                String anpNames = _repository.getAnpNames(item.getAnpConfigIds());
                item.setAnpNames(anpNames);
            }

            if (!isNullOrEmpty(item.getResourceAnpConfigIds())) {
//                String stringResourceNames = trimEnd(trimStart(item.getResourceAnpConfigIds(), ","), ",");
//                int[] _resourcesIds = Arrays.stream(stringResourceNames.split(",")).mapToInt(Integer::parseInt).toArray();
                String resourceNames = _repository.getResourceNames(item.getResourceAnpConfigIds());
                item.setResourceNames(resourceNames);
            }

            if (item.getTitleStartDate() != null && item.getTitleEndDate() != null) {
                Period diff = Period.between(item.getTitleStartDate(), item.getTitleEndDate());
                String duration = (diff.getYears() > 0 ? diff.getYears() + " año(s) " : "") + (diff.getMonths() > 0 ? diff.getMonths() + " mes(es) " : "") + (diff.getDays() > 0 ? diff.getDays() + " día(s)" : "");
                item.setDuration(duration);
            }

            if (item.getBeneficiary() != null && item.getBeneficiary().getId() == 0 && !isNullOrEmpty(item.getBeneficiary().getName()) && !isNullOrEmpty(item.getBeneficiary().getDocumentNumber()))
                item.setBeneficiary(beneficiaryService.findByDocumentNumber(item.getBeneficiary()).getItem());
            item = _repository.save(item);
            generateMatriz(item);

            if (resolutionFile != null && !resolutionFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                DocumentoDTO temp = documentService.saveFile(resolutionFile, false, item.getDigitalRouteId(), item.getResolutionDocumentName());
                if (temp.getSuccess()) {
                    item.setResolutionRouteId(temp.getId());
                    _repository.updateResolutionDocumentId(item.getId(), temp.getId());
                } else response.setExtra(temp.getMessagge());
            }
            if (titleRouteFile != null && !titleRouteFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                DocumentoDTO temp2 = documentService.saveFile(titleRouteFile, false, item.getDigitalRouteId(), item.getTitleDocumentName());
                if (temp2.getSuccess()) {
                    item.setTitleRouteId(temp2.getId());
                    _repository.updateTitleRouteDocumetId(item.getId(), temp2.getId());
                } else response.setExtra(temp2.getMessagge());
            }

            response.setItem(item);
            response.setMessage("Registro exitoso");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar la od");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<OD> update(OD item, MultipartFile resolutionFile, MultipartFile titleRouteFile) throws Exception {
        ResponseEntity<OD> response = new ResponseEntity<OD>();
        try {
            Optional<OD> _item = _repository.findById(item.getId());
            if (_item.isPresent()) {
                if (!item.getFlagDraft()) {
                    boolean validate = (item.getType() == null || item.getType().getId() == 0 || item.getModality() == null
                            || item.getModality().getId() == 0 || isNullOrEmpty(item.getResourceAnpConfigIds())
                            || item.getBeneficiary() == null || (item.getBeneficiary().getId() == 0 ? (isNullOrEmpty(item.getBeneficiary().getName()) || isNullOrEmpty(item.getBeneficiary().getDocumentNumber())) : false)
                            || isNullOrEmpty(item.getAnpConfigIds()));
                    validate = validate ? true : item.getBeneficiary() == null ? true : isNullOrEmpty(item.getBeneficiary().getName()) && isNullOrEmpty(item.getBeneficiary().getDocumentNumber()) ? true : false;
                    if (validate) {
                        response.setMessage("Se debe completar lo campos obligatorios del otorgamiento de derecho.");
                        response.setSuccess(false);
                        response.setWarning(true);
                        return response;
                    }
                }
                OD itemTemp = _item.get();
                String codeOD = itemTemp.getCode();
                String anpConfigs = "";

                if (isNullOrEmpty(item.getAnpConfigIds()) == false) {
                    String anpNames = _repository.getAnpNames(item.getAnpConfigIds());
                    item.setAnpNames(anpNames);

                    item.setFlagMigrate(itemTemp.getFlagMigrate());
                    if (!item.getFlagDraft() && itemTemp.getType() != null && itemTemp.getModality() != null && !itemTemp.getFlagMigrate()) {
                        anpConfigs = item.getAnpConfigIds().replace(",", "-");
                        String year = item.getSignatureDate() != null ? item.getSignatureDate().getYear() + "-" : "";

                        codeOD = //itemTemp.getType().getName().substring(0,1) + "-" +
                                itemTemp.getModality().getAcronym() + "-" +
                                        year + anpConfigs + "-" + String.format("%0" + 3 + "d", itemTemp.getId());
                    }
                }

                if (isNullOrEmpty(item.getResourceAnpConfigIds()) == false) {
//                    String stringResourceNames = trimEnd(trimStart(item.getResourceAnpConfigIds(), ","), ",");
//                    int[] _resourcesIds = Arrays.stream(stringResourceNames.split(",")).mapToInt(Integer::parseInt).toArray();
                    String resourceNames = _repository.getResourceNames(item.getResourceAnpConfigIds());
                    item.setResourceNames(resourceNames);
                }

                if (item.getTitleStartDate() != null && item.getTitleEndDate() != null) {
                    Period diff = Period.between(item.getTitleStartDate(), item.getTitleEndDate());
                    String duration = (diff.getYears() > 0 ? diff.getYears() + " año(s) " : "") + (diff.getMonths() > 0 ? diff.getMonths() + " mes(es) " : "") + (diff.getDays() > 0 ? diff.getDays() + " día(s)" : "");
                    item.setDuration(duration);
                }

                item.setCode(codeOD);
                item.setResolutionRouteId(itemTemp.getResolutionRouteId());
                item.setTitleRouteId(itemTemp.getTitleRouteId());
                item.setState((item.getState() == null || item.getState().getId() == 0) ? null : item.getState());
                item.setModality((item.getModality() == null || item.getModality().getId() == 0) ? null : item.getModality());
                item.setBeneficiary((item.getBeneficiary() == null || item.getBeneficiary().getId() == 0) ? null : item.getBeneficiary());
                item.setManagementPlan((item.getManagementPlan() == null || item.getManagementPlan().getId() == 0) ? null : item.getManagementPlan());
                item.setSitePlan((item.getSitePlan() != null && item.getSitePlan().getId() == 0) ? null : item.getSitePlan());
                item = _repository.save(item);
                generateMatriz(item);

                if (resolutionFile != null && !resolutionFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                    DocumentoDTO temp = documentService.saveFile(resolutionFile, false, item.getDigitalRouteId(), item.getResolutionDocumentName());
                    if (temp.getSuccess()) {
                        item.setResolutionRouteId(temp.getId());
                        _repository.updateResolutionDocumentId(item.getId(), temp.getId());
                    } else response.setExtra(temp.getMessagge());
                }
                if (titleRouteFile != null && !titleRouteFile.isEmpty() && !isNullOrEmpty(item.getDigitalRouteId())) {
                    DocumentoDTO temp2 = documentService.saveFile(titleRouteFile, false, item.getDigitalRouteId(), item.getTitleDocumentName());
                    if (temp2.getSuccess()) {
                        item.setTitleRouteId(temp2.getId());
                        _repository.updateTitleRouteDocumetId(item.getId(), temp2.getId());
                    } else response.setExtra(temp2.getMessagge());
                }

                if (item.getBeneficiary() != null && item.getBeneficiary().getId() == 0 && !isNullOrEmpty(item.getBeneficiary().getName()) && !isNullOrEmpty(item.getBeneficiary().getDocumentNumber()))
                    item.setBeneficiary(beneficiaryService.findByDocumentNumber(item.getBeneficiary()).getItem());

                response.setItem(item);
                response.setMessage("Se actualizó el registro correctamente");
            } else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar la od");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<OD> delete(int id) throws Exception {
        ResponseEntity<OD> response = new ResponseEntity<OD>();
        try {
            Optional<OD> item = _repository.findById(id);
            if (item.isPresent()) {
                _repository.deleteById(id);
                //_repository.updateIsDeleted(id, false);
                response.setMessage("Se elimino el registro correctamente.");
            } else {
                response.setMessage("El registro no existe.");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            if (((ConstraintViolationException) ex.getCause()).getSQLState().equals("23503")) {
                response.setMessage("No se puede eliminar un otorgamiento mientras es utilizado.");
                response.setSuccess(false);
                response.setWarning(true);
                response.setExtra(ex.getMessage());
            } else {
                response.setMessage("Ocurrio un error al eliminar");
                response.setSuccess(false);
                response.setExtra(ex.getMessage());
            }
        }
        //    } catch (ConstraintViolationException ex) {
        //        if (ex.getSQLState().equals("23503")) {
        //            response.setMessage("No se puede eliminar un otorgamiento mientras es utilizado.");
        //            response.setSuccess(false);
        //            response.setWarning(true);
        //            response.setExtra(ex.getMessage());
        //        }
        //    } catch (Exception ex) {
        //        response.setMessage("Ocurrio un error al eliminar");
        //        response.setSuccess(false);
        //        response.setExtra(ex.getMessage());
        //    }

        return response;
    }


    public ByteArrayInputStream export(OD item) throws Exception {
        String[] columns = {"TIPO", "ANP", "TITULO", "RECURSO", "BENEFICIARIO", "FECHA EMI.", "ESTADO"};

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("OtorgamientoDerecho");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }
        try {

            String anpCodes = isNullOrEmpty(item.getAnpConfigIds()) ? "" : "{" + item.getAnpConfigIds() + "}";
            String code = isNullOrEmpty(item.getCode()) ? "" : item.getCode();
            String resourceIds = isNullOrEmpty(item.getResourceAnpConfigIds()) ? "" : "{" + item.getResourceAnpConfigIds() + "}";
            int stateId = item.getState() == null ? 0 : item.getState().getId();
            int modalityId = item.getModality() == null ? 0 : item.getModality().getId();
            int typeId = item.getType() == null ? 0 : item.getType().getId();
            int beneficiaryId = item.getBeneficiary() == null ? 0 : item.getBeneficiary().getId();
            List<ODDTO> items = this._repository.search(code, stateId, typeId, anpCodes, modalityId, resourceIds, beneficiaryId);

            int initRow = 1;
            for (ODDTO _item : items) {
                row = sheet.createRow(initRow);
                row.createCell(0).setCellValue(_item.getTypeName());
                row.createCell(1).setCellValue(_item.getAnpConfigs());
                row.createCell(2).setCellValue(_item.getDescription());
                row.createCell(3).setCellValue(_item.getResourceAnpConfigs());
                row.createCell(4).setCellValue(_item.getBeneficiaryName());
                if (_item.getSignatureDate() == null)
                    row.createCell(5).setCellValue("");
                else
                    row.createCell(5).setCellValue(_item.getSignatureDate());
                row.createCell(6).setCellValue(_item.getStateName());
                initRow++;
            }
            workbook.write(stream);
            workbook.close();
            return new ByteArrayInputStream(stream.toByteArray());
        } catch (Exception ex) {
            return null;
        }
    }

    public String generateMatriz(OD item) throws Exception {
        try {
            // Eliminar relacionados
            _repository.deleteMatrizRelated(item.getId());

            // Agregar relacionados
            if ((item.getSitePlan() != null && item.getSitePlan().getId() != 0) || (item.getManagementPlan() != null && item.getManagementPlan().getId() != 0)) {
                _repository.generateMatrizForPlan(item.getId(), item.getSitePlan() == null ? 0 : item.getSitePlan().getId(), item.getManagementPlan() == null ? 0 : item.getManagementPlan().getId());
            } else {
                _repository.generateMatrizForNormAnpConfig(item.getId(), item.getAnpConfigIds(), item.getSectorIds() == null ? "" : item.getSectorIds(), item.getPolygonIds() == null ? "" : item.getPolygonIds());
            }

        } catch (Exception ex) {
            return "Ocurrio un error al eliminar relacionados";
        }
        return null;
    }

    public String getFileName(String fileId) throws Exception {
        try {
            String fileName = _repository.getFileNameByFileId(fileId);
            return URLEncoder.encode(fileName.trim(), "UTF-8").replace("+", "%20");
        } catch (Exception ex) {
            return "";
        }
    }

    public ResponseEntity<MdPOdEntity> saveForExternal(MdPOdEntity item) throws Exception {
        ResponseEntity<MdPOdEntity> response = new ResponseEntity<MdPOdEntity>();
        try {
            ProcedureMPV i = this.procedureRepository.findByProcedure(item.getSrl_id_doc_recepcion());
            if ( i != null && i.getId() != 0) {
                this.payFormMPVRepository.deleteByProcedureId(i.getId());
                this.formMPVRepository.deleteByProcedure(i.getId());
                this.procedureRepository.deleteById(i.getId());
                this._repository.deleteById(i.getOd().getId());
            }

            OD itemOd = new OD();
            Modality mod = new Modality();
            mod.setId(item.getId_tipo_tramite_proced());
            itemOd.setModality(mod);
            itemOd.setFlagDraft(true);
            itemOd.setStartDate(LocalDate.now());
            itemOd = this.save(itemOd, null, null).getItem();

            ProcedureMPV pro = this.procedureServiceImpl.parseToModel(item);
            pro.setModality(mod);
            pro.setOd(itemOd);
            ProcedureMPV procd = this.procedureRepository.save(pro);

            if (item.getAnps() != null && item.getAnps().size() > 0) {
                this.anpMPVService.save2(item.getAnps(), procd);
            }

            if (item.getListFiles() != null && item.getListFiles().size() > 0) {
                this.fileMPVService.save2(item.getListFiles(), procd);
            }

            if (item.getFormularios() != null) {
                if (item.getFormularios().getForm1() != null) {
                    item.getFormularios().getForm1().setProcedure(procd);
                    this.formMPVRepository.save(item.getFormularios().getForm1());
                }
                if (item.getFormularios().getForm2() != null) {
                    item.getFormularios().getForm2().setProcedure(procd);
                    this.formMPVRepository.save(item.getFormularios().getForm2());
                }
                if (item.getFormularios().getForm3() != null) {
                    item.getFormularios().getForm3().setProcedure(procd);
                    this.formMPVRepository.save(item.getFormularios().getForm3());
                }
                if (item.getFormularios().getForm5() != null) {
                    item.getFormularios().getForm5().setProcedure(procd);
                    this.formMPVRepository.save(item.getFormularios().getForm5());
                }
                if (item.getFormularios().getForm7() != null) {
                    item.getFormularios().getForm7().setProcedure(procd);
                    this.formMPVRepository.save(item.getFormularios().getForm7());
                }
                if (item.getFormularios().getForm8() != null) {
                    item.getFormularios().getForm8().setProcedure(procd);
                    this.formMPVRepository.save(item.getFormularios().getForm8());
                }
                if (item.getFormularios().getForm9() != null) {
                    item.getFormularios().getForm9().setProcedure(procd);
                    this.formMPVRepository.save(item.getFormularios().getForm9());
                }
                if (item.getFormularios().getForm10() != null) {
                    item.getFormularios().getForm10().setProcedure(procd);
                    this.formMPVRepository.save(item.getFormularios().getForm10());
                }
                if (item.getFormularios().getFormPago() != null){
                    item.getFormularios().getFormPago().setProcedure(procd);
                    this.payFormMPVRepository.save(item.getFormularios().getFormPago());
                }
            }
            response.setItem(item);
            response.setMessage("Registro exitoso");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar la od");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<MdPOdEntity> getForExternal(int procedure) throws Exception {
        ResponseEntity<MdPOdEntity> response = new ResponseEntity<MdPOdEntity>();
        try {
            ProcedureMPV procdTemp = this.procedureRepository.findByProcedure(procedure);
            if (procdTemp.getId() > 0) {
                ProcedureMPV procd = procdTemp;

                MdPOdEntity item = this.procedureServiceImpl.parseToEntity(procdTemp);
                item.setId_tipo_tramite_proced(procd.getModality().getId());

                item.setAnps(anpMPVService.listByProcedure(procd.getId()));
                item.setListFiles(fileMPVService.listByProcedure(procd.getId()).getItems());

                item.setFormularios(new MdPFormsEntity());
                item.getFormularios().setForm1(formMPVRepository.findByProcedureIdAndForm(procd.getId(), "form1"));
                item.getFormularios().setForm2(formMPVRepository.findByProcedureIdAndForm(procd.getId(), "form2"));
                item.getFormularios().setForm3(formMPVRepository.findByProcedureIdAndForm(procd.getId(), "form3"));
                item.getFormularios().setForm5(formMPVRepository.findByProcedureIdAndForm(procd.getId(), "form5"));
                item.getFormularios().setForm7(formMPVRepository.findByProcedureIdAndForm(procd.getId(), "form7"));
                item.getFormularios().setForm8(formMPVRepository.findByProcedureIdAndForm(procd.getId(), "form8"));
                item.getFormularios().setForm9(formMPVRepository.findByProcedureIdAndForm(procd.getId(), "form9"));
                item.getFormularios().setForm10(formMPVRepository.findByProcedureIdAndForm(procd.getId(), "form10"));
                item.getFormularios().setFormPago(payFormMPVRepository.findByProcedureId(procd.getId()));
                response.setItem(item);
            } else {
                response.setMessage("El tramite no existe.");
                response.setSuccess(false);
            }
        } catch(Exception ex) {
            response.setMessage("Ocurrio un error al recuperar la información.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

}