package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "t_config_anp")
public class AnpConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_config_anp_srl_id_seq", sequenceName="t_config_anp_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo", nullable=false, foreignKey = @ForeignKey(name="fk_anp_config_tipo"))
    private ListDetail type;

    @Column (name= "txt_cod_anp", nullable=false, unique = true)
    private String code;

    @Column (name= "txt_nom", length = 250)
    private String name;

    @Column (name= "int_id_anp", length = 250)
    private String anpId;

    @Column (name= "var_cod_sector")
    private String sectorCode;

    @Column (name= "var_nom_sector")
    private String sectorName;

    @Column (name= "var_cod_poligono")
    private String polygonCode;

    @Column (name= "var_nom_poligono")
    private String polygonName;

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
