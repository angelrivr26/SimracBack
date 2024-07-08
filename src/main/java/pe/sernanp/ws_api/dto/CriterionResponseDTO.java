package pe.sernanp.ws_api.dto;

import lombok.Data;
import pe.sernanp.ws_api.model.Criterion;
import pe.sernanp.ws_api.model.ListDetail;

import java.util.List;

@Data
public class CriterionResponseDTO {

    private int id;

    private List<CriterionDTO> items;
}
