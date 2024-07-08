package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ODDTO {

    Integer getId();
    String getCode();

    default ListDetailEntity getType() {
        return new ListDetailEntity(getTypeId(), getTypeName());
    };

    String getAnpCodes();

    String getSectorCodes();

    String getPolygonCodes();

    String getAnpConfigs();

    String getDescription();

    String getTitleNumber();

    String getResourceAnpConfigs();

    String getModalityDescription();

    String getModalityCode2();

    default ListDetailEntity getBeneficiary() {
        return new ListDetailEntity(getBeneficiaryId(), getBeneficiaryName());
    };

    String getSignatureDate();

    default ListDetailEntity getState() {
        return new ListDetailEntity(getStateId(), getStateName());
    };

    Boolean getFlagPlan();

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
