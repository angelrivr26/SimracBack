package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name = "t_actividad_anp_config", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_atividad_anp_config", columnNames = {"int_id_configanp",
                "int_id_tipo_actividad", "int_id_actividad"})})
public class ActivityAnpConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_actividadanpconfig_srl_id_seq", sequenceName="t_actividadanpconfig_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_configanp", nullable=false, foreignKey = @ForeignKey(name="FK_actividadanp_configanp"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnpConfig anpConfig;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_actividad", nullable = false, foreignKey = @ForeignKey(name="FK_actividadanp_tipo_actividad"))
    private ListDetail activityType;

    @ManyToOne
    @JoinColumn(name = "int_id_actividad", nullable = false, foreignKey = @ForeignKey(name="FK_actividadanp_actividad"))
    private ListDetail activity;

    @JsonIgnore
    @Column(name= "dte_fec_inicio", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date startDate = new java.sql.Date(System.currentTimeMillis());

    @JsonIgnore
    @Column (name= "dte_fec_fin")
    private Date endDate;

    @JsonIgnore
    @Column (name= "bol_flg_activo", nullable=false, columnDefinition = "boolean default true")
    private Boolean flagActive = true;
}
