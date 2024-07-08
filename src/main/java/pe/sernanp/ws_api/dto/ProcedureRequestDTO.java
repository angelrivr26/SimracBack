package pe.sernanp.ws_api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProcedureRequestDTO {
    private Integer state;
    private Integer type;
    private Integer modality;
    private Integer anpConfigIds;
    private String code;
    private String titleDerecho;
    private String cut;
    private Date startDate;
    private Date endDate;
}
