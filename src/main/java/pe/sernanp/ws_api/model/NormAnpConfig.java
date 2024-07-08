package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "t_norma_anp_config")
public class  NormAnpConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_norma_anp_config_srl_id_seq", sequenceName="t_norma_anp_config_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_configanp", nullable=false, foreignKey = @ForeignKey(name="FK_norma_anp_config_config_anp"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnpConfig anpConfig;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_norma", nullable=false, foreignKey = @ForeignKey(name="FK_norma_anp_config_tipo_norma"))
    private ListDetail normType;

    @ManyToOne
    @JoinColumn(name = "int_id_public", nullable=false, foreignKey = @ForeignKey(name="fk_norma_anp_config_publico"))
    private ListDetail audience;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "int_id_fuente", foreignKey = @ForeignKey(name="fk_norma_anp_config_fuente"))
    private ListDetail source;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "int_id_responsable", foreignKey = @ForeignKey(name="fk_norma_anp_confi_responsable"))
    private ListDetail responsible;

    @Column (name= "txt_nom_corto", nullable=false, length = 250)
    private String shortName;

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
