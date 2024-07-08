package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface SupervisionReportDTO {
    int getId();

    String getReportNumber();

    default ListDetailEntity getType() {
        return new ListDetailEntity(getTypeId(), getTypeName());
    };

    String getAnpCodes();

    String getAnpNames();

    String getOdCode();

    String getTitleEnables();

    default ListDetailEntity getBeneficiary() {
        return new ListDetailEntity(getBeneficiaryId(), getBeneficiaryName());
    };

    String getOpeningDate();
    int getObligationCount();

    default ListDetailEntity getState() {
        return new ListDetailEntity(getStateId(), getStateName());
    };

    @JsonIgnore
    Integer getTypeId();
    @JsonIgnore
    String getTypeName();
    @JsonIgnore
    Integer getBeneficiaryId();
    @JsonIgnore
    String getBeneficiaryName();
    @JsonIgnore
    Integer getStateId();
    @JsonIgnore
    String getStateName();
}
