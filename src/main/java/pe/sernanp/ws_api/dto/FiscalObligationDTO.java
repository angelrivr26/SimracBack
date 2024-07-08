package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface FiscalObligationDTO {
    Integer getId();
    String getDescription();
    String getCharacteristic();
    Integer getExecutionTerm();

    default ListDetailEntity getSource() {
        return new ListDetailEntity(getSourceId(), getSourceName());
    };

    default ListDetailEntity getResponsible() {
        return new ListDetailEntity(getResponsibleId(), getResponsibleName());
    };

    default ListDetailEntity getCompromiseNoMonetaryType() {
        return new ListDetailEntity(getCompromiseNoMonetaryTypeId(), getCompromiseNoMonetaryTypeName());
    };

    default boolean getIsUpdateable() { return this.getUpdateable() == null ? getId() != 0 : this.getUpdateable();}

    @JsonIgnore
    Integer getSourceId();
    @JsonIgnore
    String getSourceName();

    @JsonIgnore
    Integer getResponsibleId();
    @JsonIgnore
    String getResponsibleName();

    @JsonIgnore
    Integer getCompromiseNoMonetaryTypeId();
    @JsonIgnore
    String getCompromiseNoMonetaryTypeName();

    @JsonIgnore
    Boolean getUpdateable();
}
