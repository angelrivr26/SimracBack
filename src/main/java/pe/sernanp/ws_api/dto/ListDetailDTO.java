package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ListDetailDTO {
    int getId();
    String getName();
    String getDescription();
}
