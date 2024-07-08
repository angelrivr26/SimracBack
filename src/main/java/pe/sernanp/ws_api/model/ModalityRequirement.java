package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name = "t_modalidad_requisito")
public class ModalityRequirement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_modalidad_requisito_srl_id_seq", sequenceName="t_modalidad_requisito_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_modalidad", nullable=false, foreignKey = @ForeignKey(name="FK_mod_requisito_modalidad"))
    private Modality modality;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "int_id_modalidad_requisito", foreignKey = @ForeignKey(name="FK_var_planti_modali_requisito"))
    private List<TemplateVariable> templateVariables;

    @Column (name= "var_codigo", length = 250)
    private String code;

    @Column (name= "txt_desc_requisito", nullable=false, columnDefinition = "TEXT")
    private String description;

    @Column (name= "bol_flg_obligatorio", nullable=false)
    private Boolean flagMandatory;

    @Column (name= "txt_nom_documento", length = 500)
    private String documentName;

    @Column (name= "txt_requisito_ruta", length = 1000)
    private String documentRouteId;

    @Column (name= "var_nom_plantilla", length = 500)
    private String templateName;

    @Column (name= "var_ruta_plantilla", length = 1000)
    private String templateRouteId;

    @Column (name= "bol_flg_pago", nullable=false, columnDefinition = "boolean default false")
    private Boolean hasPayment = false;

    @Column (name= "bol_flg_formulario", nullable=false, columnDefinition = "boolean default false")
    private Boolean hasForm = false;

    @Column (name= "var_ruta_formulario", length = 1000)
    private String formPath;

    @JsonIgnore
    @Column (name= "dte_fec_inicio", nullable=false)
    private Date startDate = new java.sql.Date(System.currentTimeMillis());

    @JsonIgnore
    @Column (name= "dte_fec_fin")
    private Date endDate;

    @JsonIgnore
    @Column (name= "var_cod_version", length = 10)
    private String versionCode;

    @JsonIgnore
    @Column (name= "bol_flg_activo", nullable=false, columnDefinition = "boolean default true")
    private Boolean flagActive = true;
}
