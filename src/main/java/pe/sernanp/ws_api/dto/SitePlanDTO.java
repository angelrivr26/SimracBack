package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface SitePlanDTO {
    Integer getId();

    String getAnpCode();

    String getAnpName();

    default ListDetailEntity getInstrumentType() {
        return new ListDetailEntity(getInstrumentTypeId(), getInstrumentTypeName());
    };

    String getName();

    String getResolution();

    Boolean getFlagValidity();

    default String getPeriod() {
        return (this.getStartDate() != "" && this.getEndDate() != "") ? this.getStartDate() + " - " + this.getEndDate() : "-";
    };

    @JsonIgnore
    Integer getInstrumentTypeId();
    @JsonIgnore
    String getInstrumentTypeName();
    @JsonIgnore
    String getStartDate();
    @JsonIgnore
    String getEndDate();
}
