package pe.sernanp.ws_api.dto;

import java.time.LocalDate;

public interface AnpDTO {
    String getId();

    String getName();

    public Integer getObjectid();

    public Integer getAnp_id();

    public String getAnp_codi();

    public String getAnp_nomb();

    public String getAnp_uicn();

    public LocalDate getAnp_felec();

    public String getAnp_balem();

    public LocalDate getAnp_felem();

    public String getAnp_obs();

    public Integer getPanp_id();

    public Integer getC_id();

    public String getAnp_ubpo();

    public Integer getAnp_actu();

    public Integer getAnp_orden();

    public String getAnp_docreg();

    public LocalDate getAnp_fecreg();

    public Integer getMet_id();

    public String getC_nomb();
}
