package pe.sernanp.ws_api.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.CriterionDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Criterion;
import pe.sernanp.ws_api.model.CriterionResponse;
import pe.sernanp.ws_api.repository.CriterionRepository;
import pe.sernanp.ws_api.repository.CriterionResponseRepository;
import pe.sernanp.ws_api.service.CriterionService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CriterionServiceImpl extends BaseServiceImpl implements CriterionService {
    @Autowired
    CriterionRepository _repository;

    @Autowired
    CriterionResponseRepository _repositoryCriterion;

    public ResponseEntity<Criterion> findAll(int typeId, int component) throws Exception {
        ResponseEntity<Criterion> response = new ResponseEntity<Criterion>();
        try {
            List<Criterion> items = _repository.findBy(typeId, component);
            response.setItems(items);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<CriterionDTO> findByType(int typeId, int modality, int procedureId) throws Exception {
        ResponseEntity<CriterionDTO> response = new ResponseEntity<CriterionDTO>();
        try {
            ModelMapper modelMapper = new ModelMapper();

            List<Criterion> items = _repository.findByTypeAndModality(typeId, modality);
            List<CriterionDTO> items3 = items.stream()
                    .map(user -> modelMapper.map(user, CriterionDTO.class))
                    .collect(Collectors.toList());
            List<CriterionResponse> items2 = this._repositoryCriterion.findBy(procedureId);
            if (items2.size()==0) {
                items3.forEach(t -> {
                    t.setApply(true);
                    t.setResult(0);
                });
            }
            else {
                // Actualiza items3 con valores de items2 si los IDs coinciden
                items3.forEach(dto -> {
                    items2.stream()
                            .filter(response2 -> response2.getCriterion().getId() == dto.getId())
                            .findFirst()
                            .ifPresent(response2 -> updateCriterionDTO(dto, response2));
                });
            }
            List<CriterionDTO> groupedCriteria = this.groupByComponent(items3);
            response.setItems(groupedCriteria);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    private void updateCriterionDTO(CriterionDTO dto, CriterionResponse response) {
        if (dto.getId() == response.getCriterion().getId()) {
            dto.setComment(response.getComment());
            dto.setResult(response.getResult() != null ? response.getResult().getId() : 0);
            dto.setApply(response.getApply());
        }
    }

    public List<CriterionDTO> groupByComponent(List<CriterionDTO> items) {
        Map<Integer, List<CriterionDTO>> groupedByComponent = items.stream()
                .collect(Collectors.groupingBy(CriterionDTO::getComponent));
        List<CriterionDTO> criterionDTOList = groupedByComponent.entrySet().stream()
                .map(entry -> new CriterionDTO(entry.getKey(), entry.getValue().get(0).getNameComponent(), entry.getValue()))
                .collect(Collectors.toList());
        return criterionDTOList;
    }
}
