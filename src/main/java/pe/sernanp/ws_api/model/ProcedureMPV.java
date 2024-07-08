package pe.sernanp.ws_api.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table (name = "t_tramite")
public class ProcedureMPV extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_tramite_srl_id_seq", sequenceName="t_tramite_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_modalidad", foreignKey = @ForeignKey(name="fk_tramite_modalidad"))
    private Modality modality;

    @ManyToOne
    @JoinColumn(name = "int_id_od", foreignKey = @ForeignKey(name="fk_tramite_od"))
    private OD od;

    @Column(name = "int_id_tramite", columnDefinition = "numeric (8,0)")
    private Integer procedure;

    @Column(name = "id_tipo_tramite", length = 255)
    private String id_tipo_tramite;

    @Column(name = "id_tipo_doc", length = 255)
    private String id_tipo_doc;

    @Column(name = "var_num_doc", length = 255)
    private String var_num_doc;

    @Column(name = "int_num_folios", columnDefinition = "numeric (8,0)")
    private Integer int_num_folios;

    @Column(name = "var_persona_firma_doc", length = 255)
    private String var_persona_firma_doc;

    @Column(name = "var_remitente_doc", length = 255)
    private String var_remitente_doc;

    @Column(name = "id_oficina_recepciona", length = 255)
    private String id_oficina_recepciona;

    @Column(name = "var_persona_recepciona_doc", length = 255)
    private String var_persona_recepciona_doc;

    @Column(name = "var_correo_noti", length = 255)
    private String var_correo_noti;

    @Column(name = "var_asunto", length = 255)
    private String var_asunto;

    @Column(name = "var_uuid", length = 255)
    private String var_uuid;

    @Column(name = "id_anp_cod", length = 255)
    private String id_anp_cod;

    @Column(name = "tcodi", length = 255)
    private String tcodi;

    @Column(name = "srl_ticket", length = 255)
    private String srl_ticket;

    @Column(name = "estado_ticket", length = 255)
    private String estado_ticket;

    @Column(name = "des_est_ticket", length = 255)
    private String des_est_ticket;

    @Column(name = "id_medio", length = 255)
    private String id_medio;

    @Column(name = "var_declaracion", length = 255)
    private String var_declaracion;

    @Column(name = "anp_codigos", length = 255)
    private String anp_codigos;

    @Column(name = "ind_noti_conf", length = 255)
    private String ind_noti_conf;

    @Column(name = "bol_tipo_otorgamiento", columnDefinition = "boolean")
    private Boolean bol_tipo_otorgamiento;

    @Column(name = "srl_id_usuario", columnDefinition = "numeric (8,0)")
    private Integer srl_id_usuario;

    @Column(name = "srl_id_usuario_registra", columnDefinition = "numeric (8,0)")
    private Integer srl_id_usuario_registra;

    @Column(name = "var_cut", length = 255)
    private String var_cut;

    @Column(name="dte_fec_admision")
    private Date dateAdmission;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_eva", nullable=true, foreignKey = @ForeignKey(name="fk_tramite_tipo_evaluacion"))
    private ListDetail typeEvaluation;
}
