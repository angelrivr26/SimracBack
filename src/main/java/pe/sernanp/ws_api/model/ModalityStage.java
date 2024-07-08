package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "t_modalidad_etapa")
public class ModalityStage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_modalidad_etapa_srl_id_seq", sequenceName="t_modalidad_etapa_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_modalidad", nullable=false, foreignKey = @ForeignKey(name="FK_mod_etapa_modalidad"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Modality modality;

    @ManyToOne
    @JoinColumn(name = "int_id_estado", foreignKey = @ForeignKey(name="FK_modalidad_etapa_estado"))
    private ListDetail state;

    @Column (name= "int_orden_etapa", nullable=false)
    private int order;

    @Column (name= "txt_nom_etapa", nullable=false, length = 100)
    private String name;

    @Column (name= "bol_flg_obligatorio", nullable=false)
    private Boolean flagMandatory;

    @Column (name= "num_plazo")
    private int term;

    @Column (name= "bol_flg_administrado")
    private Boolean flagAdministered;

//    @Column (name= "txt_nom_estado", length = 50)
//    private String stateName;

    @JsonIgnore
    @Column (name= "dte_fec_inicio", nullable=false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
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
