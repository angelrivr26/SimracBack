package pe.sernanp.ws_api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SupervisionRecordRequestDTO {
    private String anp;
    private int typeId;
    private String od;
    private int supervisionType;
    private Date openingDate;
    private Date closingDate;
    private String recordNumber;
    private String beneficiary;
}
