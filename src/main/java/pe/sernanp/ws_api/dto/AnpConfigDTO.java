package pe.sernanp.ws_api.dto;

public interface AnpConfigDTO {
    Integer getId();
    String getAnpId();
    String getName();
    String getCode();
    int getActivityAnpCount();
    int getModalityAnpCount();
    int getNormAnpCount();
    int getResourceAnpCount();
}
