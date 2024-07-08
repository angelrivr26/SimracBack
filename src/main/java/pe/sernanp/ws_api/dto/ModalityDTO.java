package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ModalityDTO {
    Integer getId();

    String getCode();

    String getDescription();

    String getSustentationDocumentName();

    String getShortName();

    String getTitleEnables();

    java.sql.Date getValidFrom();

    java.sql.Date getValidUntil();

    Boolean getFlagValidity();

    Boolean getFlagDraft();

    Integer getRequirementCount();

    default ListDetailEntity getType() {
        return new ListDetailEntity(getTypeId(), getTypeName());
    };

    default ListDetailEntity getState() {
        return new ListDetailEntity(getStateId(), getStateName());
    };

    @JsonIgnore
    Integer getTypeId();
    @JsonIgnore
    String getTypeName();

    @JsonIgnore
    Integer getStateId();
    @JsonIgnore
    String getStateName();
}
