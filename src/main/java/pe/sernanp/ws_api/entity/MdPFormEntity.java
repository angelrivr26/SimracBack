package pe.sernanp.ws_api.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MdPFormEntity {
    private Integer srl_id;
    private String var_desc;
    private Integer int_departamento;
    private Integer int_provincia;
    private Integer int_distrito;
    private Integer int_anio;
    private Integer int_meses;
    private String var_num_res;
    private String var_num_res_jef;
    private String var_nombre;
    private String var_plan_sitio;
    private String var_inf_tec;
    private String var_titulo;
    private String var_objetivo;
    private String dte_inicio;
    private String dte_fin;
    private String var_proyecto;
    private String var_carta;
}
