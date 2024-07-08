package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "t_listado", schema = "ge")
public class ListHeader extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_listado_srl_id_seq", sequenceName="t_listado_srl_id_seq", allocationSize=1)
    private int id;

    @Column (name= "int_cod_num", nullable=false)
    private int code;

    @Column (name= "txt_nom_corto", nullable=false, length = 50)
    private String name;

    @Column(name= "txt_nom_largo", length = 150)
    private String description;

    @JsonIgnore
    @Column(name= "dte_fec_inicio")
    private Date startDate;

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
