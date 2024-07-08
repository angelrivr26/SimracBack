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
@Table(name = "t_recurso_anp_config")
public class ResourceAnpConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_recurso_anp_config_srl_id_seq", sequenceName="t_recurso_anp_config_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_configanp", nullable=false, foreignKey = @ForeignKey(name="FK_recurso_anp_configanp"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnpConfig anpConfig;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_recurso", nullable=false, foreignKey = @ForeignKey(name="FK_recurso_anp_tipo_recurso"))
    private ListDetail resourceType;

    @ManyToOne
    @JoinColumn(name = "int_id_recurso", nullable=false, foreignKey = @ForeignKey(name="FK_recurso_anp_recurso"))
    private ListDetail resource;

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
