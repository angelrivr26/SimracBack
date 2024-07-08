package pe.sernanp.ws_api.dto;

import lombok.Data;
import pe.sernanp.ws_api.model.Criterion;
import pe.sernanp.ws_api.model.ListDetail;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class CriterionDTO {

    private int id;

    private ListDetail type;

    private String nameType;

    private ListDetail modality;

    private String nameModality;

    private String code;

    private Integer component;

    private String nameComponent;

    private String numberCriterion;

    private String name;

    private String numberParameter;

    private String nameParameter;

    private List<CriterionDTO> parameters;

    private String comment;

    private Integer result;

    private Boolean apply;

    public CriterionDTO() {
    }

    public CriterionDTO(Integer component, String nameComponent, List<CriterionDTO> parameters) {
        this.component = component;
        this.nameComponent = nameComponent;
        this.parameters = parameters;
    }
}
