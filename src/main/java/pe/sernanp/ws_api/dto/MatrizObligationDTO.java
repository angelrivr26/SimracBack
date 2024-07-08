package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface MatrizObligationDTO {

    Integer getId();

    String getPeriod();

    String getFileNumber();

    default ListDetailEntity getType() {
        return new ListDetailEntity(getTypeId(), getTypeName());
    };

    String getAnpCodes();

    String getAnpNames();

    String getTitleEnables();

    String getResourceNames();

    default ListDetailEntity getBeneficiary() {
        return new ListDetailEntity(getBeneficiaryId(), getBeneficiaryName());
    };

    String getSignatureDate();

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
