package pe.sernanp.ws_api.entity;

import lombok.Data;
import pe.sernanp.ws_api.model.AnpMPV;
import pe.sernanp.ws_api.model.FileMPV;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MdPOdEntity {
    private Integer srl_id_doc_recepcion;
    private String id_tipo_tramite;
    private Integer id_tipo_tramite_proced;
    private String id_tipo_doc;
    private String var_num_doc;
    private Integer int_num_folios;
    private String var_persona_firma_doc;
    private String var_remitente_doc;
    private String id_oficina_recepciona;
    private String var_persona_recepciona_doc;
    private String var_correo_noti;
    private String var_asunto;
    private String var_uuid;
    private String id_anp_cod;
    private String tcodi;
    private String srl_ticket;
    private String estado_ticket;
    private String des_est_ticket;
    private String id_medio;
    private String var_declaracion;
    private String anp_codigos;
    private String ind_noti_conf;
    private Boolean bol_tipo_otorgamiento;
    private Integer srl_id_usuario;
    private Integer srl_id_usuario_registra;
    private List<AnpMPV> anps;
    private List<FileMPV> listFiles;
    private MdPFormsEntity formularios;
    private String anps2;

    public String getAnps2() {
        String value = "";
        if (this.anps != null && this.anps.size() > 0)
            value = this.anps.stream().map(e -> e.getName()).collect(Collectors.joining( ", " ) );
        return value;
    }
}
