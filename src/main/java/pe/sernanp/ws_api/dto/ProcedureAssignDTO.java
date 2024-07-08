package pe.sernanp.ws_api.dto;

import lombok.Data;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.ProcedureMPV;

@Data
public class ProcedureAssignDTO {

    private int id;
    private ProcedureMPV procedure;

    private String responsible;

    private String nameResponsible;

    private ListDetail roleEvaluation;

    private String responsible2;

    private String nameResponsible2;

    private ListDetail roleEvaluation2;

    private String responsible3;

    private String nameResponsible3;

    private ListDetail roleEvaluation3;

    private Boolean isCompleted;

}
