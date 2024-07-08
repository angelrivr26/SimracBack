package pe.sernanp.ws_api.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.DeterminationDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Determination;
import pe.sernanp.ws_api.model.DeterminationResponse;
import pe.sernanp.ws_api.repository.DeterminationRepository;
import pe.sernanp.ws_api.repository.DeterminationResponseRepository;
import pe.sernanp.ws_api.service.DeterminationService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeterminationServiceImpl extends BaseServiceImpl implements DeterminationService {
    @Autowired
    DeterminationRepository _repository;

    @Autowired
    DeterminationResponseRepository _repositoryDetermination;

    public ResponseEntity<DeterminationDTO> findByType(int typeId, int modality, int procedureId) throws Exception {
        ResponseEntity<DeterminationDTO> response = new ResponseEntity<DeterminationDTO>();
        try {
            ModelMapper modelMapper = new ModelMapper();
            List<Determination> items = _repository.findByTypeAndModality(typeId, modality);
            List<DeterminationDTO> items3 = items.stream()
                    .map(user -> modelMapper.map(user, DeterminationDTO.class))
                    .collect(Collectors.toList());
            List<DeterminationResponse> items2 = this._repositoryDetermination.findBy(procedureId);
            if (items2.size()==0) {
                items3.forEach(t -> {
                    t.setValueAnnual(0.00);
                });
            }
            else {
                // Actualiza items3 con valores de items2 si los IDs coinciden
                items3.forEach(dto -> {
                    items2.stream()
                            .filter(response2 -> response2.getDetermination().getId() == dto.getId())
                            .findFirst()
                            .ifPresent(response2 -> updateDeterminationDTO(dto, response2));
                });
            }
            response.setItems(items3);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    private void updateDeterminationDTO(DeterminationDTO dto, DeterminationResponse response) {
        if (dto.getId() == response.getDetermination().getId()) {
            dto.setValueAnnual(response.getValueAnnual());
        }
    }

}
