package pe.sernanp.ws_api.dto;

import lombok.Data;
import pe.sernanp.ws_api.model.ListDetail;

import java.util.Date;

@Data
public class MonitoringRecordRequestDTO {
    private String od;
    private String anp;
    private int typeId;
    private int state;
    private String beneficiary;
    private String titleName;
    private Date openingDate;
    private Date closingDate;
}
