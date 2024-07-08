package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.*;
import pe.sernanp.ws_api.entity.MdPOdEntity;
import pe.sernanp.ws_api.entity.PaginatorEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.*;
import pe.sernanp.ws_api.repository.*;
import pe.sernanp.ws_api.service.ProcedureService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProcedureServiceImpl extends BaseServiceImpl implements ProcedureService {
    @Autowired
    ProcedureRepository _repository;
    @Autowired
    CriterionResponseRepository _repositoryCriterion;
    @Autowired
    DeterminationResponseRepository _repositoryDetermination;
    @Autowired
    ProcedureAssignRepository _repositoryAssign;
    @Autowired
    ProcedureStateRepository _repositoryProcedureState;

    @Override
    public ResponseEntity<ProcedureMPV> save(ProcedureMPV item) {
        ResponseEntity<ProcedureMPV> response = new ResponseEntity<ProcedureMPV>();
        try {
            ProcedureMPV _item = _repository.save(item);
            response.setItem(_item);
            response.setMessage("Registro exitoso");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ProcedureResponseDTO> search(ProcedureRequestDTO item, PaginatorEntity paginator) throws Exception {
        ResponseEntity<ProcedureResponseDTO> response = new ResponseEntity<ProcedureResponseDTO>();
        try {
            Pageable page = PageRequest.of(paginator.getOffset() - 1, paginator.getLimit());
            String anpCodes = "";//isNullOrEmpty(item.getAnpConfigIds()) ? "" : "{" + item.getAnpConfigIds() + "}";
            String code =  "";//isNullOrEmpty(item.getCode()) ? "" : item.getCode();
            String resourceIds =  "";//isNullOrEmpty(item.getResourceAnpConfigIds()) ? "" : "{" + item.getResourceAnpConfigIds() + "}";
            int stateId = item.getState() == null ? 0 : item.getState();
            int modalityId = item.getModality();
            int typeId = item.getType() == null ? 0 : item.getType();
            int beneficiaryId = 0;//item.getBeneficiary() == null ? 0 : item.getBeneficiary().getId();
            String cut = item.getCut();
            String odCode = item.getCode();

            Page<ProcedureResponseDTO> pag = this._repository.search(cut, odCode, modalityId, stateId, typeId, page);
            List<ProcedureResponseDTO> items = pag.getContent();
            paginator.setTotal((int) pag.getTotalElements());

            response.setItems(items);
            response.setPaginator(paginator);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar los tramites");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public ResponseEntity<?> findById(int id) throws Exception {
        ResponseEntity<ProcedureResponseDTO> response = new ResponseEntity<ProcedureResponseDTO>();
        try {
            ProcedureResponseDTO item = _repository.findById(id);
            if (item != null)
                response.setItem(item);
            else {
                response.setMessage("Registro no existe");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle de un Trámite");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ProcedureMPV parseToModel(MdPOdEntity entity) {
        ProcedureMPV item = new ProcedureMPV();

        item.setProcedure(entity.getSrl_id_doc_recepcion());
        item.setId_tipo_tramite(entity.getId_tipo_tramite());
        item.setId_tipo_doc(entity.getId_tipo_doc());
        item.setVar_num_doc(entity.getVar_num_doc());
        item.setInt_num_folios(entity.getInt_num_folios());
        item.setVar_persona_firma_doc(entity.getVar_persona_firma_doc());
        item.setVar_remitente_doc(entity.getVar_remitente_doc());
        item.setId_oficina_recepciona(entity.getId_oficina_recepciona());
        item.setVar_persona_recepciona_doc(entity.getVar_persona_recepciona_doc());
        item.setVar_correo_noti(entity.getVar_correo_noti());
        item.setVar_asunto(entity.getVar_asunto());
        item.setVar_uuid(entity.getVar_uuid());
        item.setId_anp_cod(entity.getId_anp_cod());
        item.setTcodi(entity.getTcodi());
        item.setSrl_ticket(entity.getSrl_ticket());
        item.setEstado_ticket(entity.getEstado_ticket());
        item.setDes_est_ticket(entity.getDes_est_ticket());
        item.setId_medio(entity.getId_medio());
        item.setVar_declaracion(entity.getVar_declaracion());
        item.setAnp_codigos(entity.getAnp_codigos());
        item.setInd_noti_conf(entity.getInd_noti_conf());
        item.setBol_tipo_otorgamiento(entity.getBol_tipo_otorgamiento());
        item.setSrl_id_usuario(entity.getSrl_id_usuario());
        item.setSrl_id_usuario_registra(entity.getSrl_id_usuario_registra());
        return item;
    }

    public MdPOdEntity parseToEntity(ProcedureMPV item) {
        MdPOdEntity entity = new MdPOdEntity();

        entity.setSrl_id_doc_recepcion(item.getProcedure());
        entity.setId_tipo_tramite(item.getId_tipo_tramite());
        entity.setId_tipo_doc(item.getId_tipo_doc());
        entity.setVar_num_doc(item.getVar_num_doc());
        entity.setInt_num_folios(item.getInt_num_folios());
        entity.setVar_persona_firma_doc(item.getVar_persona_firma_doc());
        entity.setVar_remitente_doc(item.getVar_remitente_doc());
        entity.setId_oficina_recepciona(item.getId_oficina_recepciona());
        entity.setVar_persona_recepciona_doc(item.getVar_persona_recepciona_doc());
        entity.setVar_correo_noti(item.getVar_correo_noti());
        entity.setVar_asunto(item.getVar_asunto());
        entity.setVar_uuid(item.getVar_uuid());
        entity.setId_anp_cod(item.getId_anp_cod());
        entity.setTcodi(item.getTcodi());
        entity.setSrl_ticket(item.getSrl_ticket());
        entity.setEstado_ticket(item.getEstado_ticket());
        entity.setDes_est_ticket(item.getDes_est_ticket());
        entity.setId_medio(item.getId_medio());
        entity.setVar_declaracion(item.getVar_declaracion());
        entity.setAnp_codigos(item.getAnp_codigos());
        entity.setInd_noti_conf(item.getInd_noti_conf());
        entity.setBol_tipo_otorgamiento(item.getBol_tipo_otorgamiento());
        entity.setSrl_id_usuario(item.getSrl_id_usuario());
        entity.setSrl_id_usuario_registra(item.getSrl_id_usuario_registra());

        return entity;
    }

    @Override
    public ResponseEntity<?> saveCriterion(CriterionResponseDTO item) {
        ResponseEntity<?> response = new ResponseEntity<ProcedureMPV>();
        try {
            _repositoryCriterion.deleteByProcedureId(item.getId());
            List<CriterionResponse> criterions = new ArrayList<>();
            for (CriterionDTO item2 : item.getItems()) {
                CriterionResponse itemCriterion = new CriterionResponse();
                itemCriterion.setComment(item2.getComment());
                if (item2.getResult()!=0) {
                    itemCriterion.setResult(new ListDetail());
                    itemCriterion.getResult().setId(item2.getResult());
                }
                else
                    itemCriterion.setResult(null);
                itemCriterion.setApply(item2.getApply());
                itemCriterion.setProcedure(new ProcedureMPV());
                itemCriterion.getProcedure().setId(item.getId());
                itemCriterion.setCriterion(new Criterion());
                itemCriterion.getCriterion().setId(item2.getId());
                criterions.add(itemCriterion);
            }
            _repositoryCriterion.saveAll(criterions);
            ProcedureAssign procedureAssign = this._repositoryAssign.findByProcedureIdAndRoleEvaluationId(item.getId(), 302);
            if (procedureAssign != null) {
                procedureAssign.setIsCompleted(true);
                this._repositoryAssign.save(procedureAssign);
            }
            //response.setItem(_item);
            response.setMessage("Registro exitoso");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ProcedureMPV> result(int id) throws Exception {
        ResponseEntity<ProcedureMPV> response = new ResponseEntity<ProcedureMPV>();
        try {
            Boolean success = true;
            List<CriterionResponse> items = _repositoryCriterion.findBy(id);
            String name = "";
            String viable = "";
            if (items.size()==0) {
                name = "No se ha realizado la evaluación técnica";
                viable = "Pendiente";
            }
            else {
                int countApply = 0;
                for (int i=0; i<items.size();i++){
                    if (items.get(i).getApply() && items.get(i).getResult() != null && items.get(i).getResult().getId()==296){
                        countApply++;
                    }
                }
                if (countApply == items.size()){
                    name = "Si cumple con los " + countApply + " criterios requeridos";
                    viable = "Viable";
                }
                else {
                    name = "No cumple con la totalidad de criterios requeridos";
                    viable = "Observado";
                }
            }

            ProcedureMPV item = new ProcedureMPV();
            item.setVar_declaracion(name);
            item.setTcodi(viable);
            response.setItem(item);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle de un Trámite");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<?> saveDetermination(DeterminationResponseDTO item) {
        ResponseEntity<?> response = new ResponseEntity<ProcedureMPV>();
        try {
            _repositoryDetermination.deleteByProcedureId(item.getId());
            List<DeterminationResponse> criterions = new ArrayList<>();
            for (DeterminationDTO item2 : item.getItems()) {
                DeterminationResponse itemCriterion = new DeterminationResponse();
                itemCriterion.setProcedure(new ProcedureMPV());
                itemCriterion.getProcedure().setId(item.getId());
                itemCriterion.setDetermination(new Determination());
                itemCriterion.getDetermination().setId(item2.getId());
                itemCriterion.setValueAnnual(item2.getValueAnnual());
                criterions.add(itemCriterion);
            }
            _repositoryDetermination.saveAll(criterions);
            //response.setItem(_item);
            response.setMessage("Registro exitoso");
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    @Override
    public ResponseEntity<?> saveAssign(ProcedureAssignDTO dto) {
        ResponseEntity<?> response = new ResponseEntity<ProcedureMPV>();
        try {
            _repositoryAssign.deleteByProcedureId(dto.getProcedure().getId());

            List<ProcedureAssign> assigns = new ArrayList<>();

            if (dto.getRoleEvaluation() != null && dto.getRoleEvaluation().getId() != 0) {
                ProcedureAssign assign1 = new ProcedureAssign();
                assign1.setId(0);
                assign1.setProcedure(dto.getProcedure());
                assign1.setResponsible(dto.getResponsible());
                assign1.setNameResponsible(dto.getNameResponsible());
                assign1.setRoleEvaluation(dto.getRoleEvaluation());
                assign1.setIsCompleted(false);
                assigns.add(assign1);
            }

            if (dto.getRoleEvaluation2() != null && dto.getRoleEvaluation2().getId() != 0) {
                ProcedureAssign assign2 = new ProcedureAssign();
                assign2.setId(0);
                assign2.setProcedure(dto.getProcedure());
                assign2.setResponsible(dto.getResponsible2());
                assign2.setNameResponsible(dto.getNameResponsible2());
                assign2.setRoleEvaluation(dto.getRoleEvaluation2());
                assign2.setIsCompleted(false);
                assigns.add(assign2);
            }

            if (dto.getRoleEvaluation3() != null && dto.getRoleEvaluation3().getId() != 0) {
                ProcedureAssign assign3 = new ProcedureAssign();
                assign3.setId(0);
                assign3.setProcedure(dto.getProcedure());
                assign3.setResponsible(dto.getResponsible3());
                assign3.setNameResponsible(dto.getNameResponsible3());
                assign3.setRoleEvaluation(dto.getRoleEvaluation3());
                assign3.setIsCompleted(false);
                assigns.add(assign3);
            }

            if (!assigns.isEmpty()) {
                _repositoryAssign.saveAll(assigns);
                ProcedureState assigndto = _repositoryProcedureState.findState(dto.getProcedure().getId(), 299);
                if (assigndto == null ) {
                    _repositoryProcedureState.update(dto.getProcedure().getId());
                    ProcedureState x = new ProcedureState();
                    x.setId(0);
                    ListDetail type = new ListDetail();
                    type.setId(299);
                    x.setState(type);
                    x.setDate(new Date());
                    x.setProcedure(dto.getProcedure());
                    x.setActive(true);
                    _repositoryProcedureState.save(x);
                }

                response.setMessage("Registro exitoso");
            } else {
                response.setMessage("No se han proporcionado asignaciones para guardar");
                response.setSuccess(false);
            }
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al guardar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ProcedureAssignDTO> getAssign(int id) throws Exception {
        ResponseEntity<ProcedureAssignDTO> response = new ResponseEntity<ProcedureAssignDTO>();
        try {
            List<ProcedureAssign> items = _repositoryAssign.findByProcedureId(id);

            if (!items.isEmpty()) {
                ProcedureAssignDTO dto = new ProcedureAssignDTO();

                ProcedureAssign assign1 = items.get(0);
                dto.setId(assign1.getId());
                dto.setProcedure(assign1.getProcedure());
                dto.setResponsible(assign1.getResponsible());
                dto.setNameResponsible(assign1.getNameResponsible());
                dto.setRoleEvaluation(assign1.getRoleEvaluation());

                if (items.size() > 1) {
                    ProcedureAssign assign2 = items.get(1);
                    dto.setResponsible2(assign2.getResponsible());
                    dto.setNameResponsible2(assign2.getNameResponsible());
                    dto.setRoleEvaluation2(assign2.getRoleEvaluation());
                }

                if (items.size() > 2) {
                    ProcedureAssign assign3 = items.get(2);
                    dto.setResponsible3(assign3.getResponsible());
                    dto.setNameResponsible3(assign3.getNameResponsible());
                    dto.setRoleEvaluation3(assign3.getRoleEvaluation());
                }
                response.setItem(dto);
            } else {
                response.setMessage("No hay detalle disponible para este procedimiento");
                response.setSuccess(false);
            }

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error en el detalle de un Trámite");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ProcedureAssign> getList(int id) throws Exception {
        ResponseEntity<ProcedureAssign> response = new ResponseEntity<ProcedureAssign>();
        try {
            List<ProcedureAssign> items = _repositoryAssign.findByProcedureId(id);
            response.setItems(items);
        } catch (Exception ex){
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<ProcedureAssign> updateCut(int procedure, int record, int period) throws Exception {
        ResponseEntity response = new ResponseEntity<ProcedureAssign>();
        try {
            ProcedureMPV item = _repository.findByProcedure(procedure);
            if (item != null && item.getId() != 0) {
                int bool = _repository.updateCut(procedure, (record + "-" + period));
                if (bool == 1) {
                    ProcedureState procedureState= new ProcedureState();
                    procedureState.setId(0);
                    procedureState.setProcedure(new ProcedureMPV());
                    procedureState.getProcedure().setId(item.getId());
                    procedureState.setState(new ListDetail());
                    procedureState.getState().setId(298);
                    procedureState.setDate(new Date());
                    procedureState.setActive(true);

                    _repositoryProcedureState.update(item.getId());
                    _repositoryProcedureState.save(procedureState);

                    response.setMessage("Se actualizo el estado del tramite.");
                    response.setSuccess(true);
                } else {
                    response.setMessage("No se logro actalizar el tramite.");
                    response.setSuccess(false);
                    response.setWarning(true);
                }
            }
            else {
                response.setMessage("Registro no existe.");
                response.setSuccess(false);
                response.setWarning(true);
            }
        } catch (Exception ex){
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
}
