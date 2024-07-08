package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface SRFiscalObligationDTO {
    int getId();
    String getDescription();
    default ListDetailEntity getSource(){ return new ListDetailEntity(getSourceId(), getSourceName()); };
    default ListDetailEntity getBreach(){ return new ListDetailEntity(getBreachId(), getBreachName()); };
    String getExecutionTerm();

    @JsonIgnore
    int getBreachId();
    @JsonIgnore
    String getBreachName();

    @JsonIgnore
    int getSourceId();
    @JsonIgnore
    String getSourceName();

}
