package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface MonitoringReportDTO {
    int getId();

    String getReportNumber();

    default ListDetailEntity getType() {
        return new ListDetailEntity(getTypeId(), getTypeName());
    };

    int getRecordsTotal();

    int getOdsTotal();

    int getObligationsFulfilled();

    int getObligationsTotal();

    @JsonIgnore
    Integer getTypeId();
    @JsonIgnore
    String getTypeName();
}
