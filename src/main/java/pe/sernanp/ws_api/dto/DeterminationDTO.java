package pe.sernanp.ws_api.dto;

import lombok.Data;
import pe.sernanp.ws_api.model.ListDetail;
import java.util.List;

@Data
public class DeterminationDTO {

    private int id;

    private ListDetail type;

    private ListDetail modality;

    private Integer yearGranted;

    private Integer component;

    private String nameComponent;

    private String numberCriterion;

    private String criterion;

    private String numberParameter;

    private String nameParameter;

    private String weight;

    private String attribute1;

    private String attribute2;

    private String attribute3;

    private Double value1;

    private Double value2;

    private Double value3;

    private Double valueAnnual;

    public DeterminationDTO() {
    }
}
