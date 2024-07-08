package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "tbt_anp", schema = "ge")
public class Anp {
    @Id
    @Column (name= "anp_id")
    private Integer anp_id;

    @Column (name= "objectid")
    private Integer objectid;

    @Column (name= "anp_codi", length = 250)
    private String anp_codi;

    @Column (name= "anp_nomb", length = 250)
    private String anp_nomb;

    @Column (name= "anp_uicn", length = 250)
    private String anp_uicn;

    @Column (name= "anp_felec")
    private Date anp_felec;

    @Column (name= "anp_balem", length = 250)
    private String anp_balem;

    @Column (name= "anp_felem")
    private Date anp_felem;

    @Column (name= "anp_obs", length = 250)
    private String anp_obs;

    @Column (name= "panp_id")
    private Integer panp_id;

    @Column (name= "c_id")
    private Integer c_id;

    @Column (name= "anp_ubpo", length = 250)
    private String anp_ubpo;

    @Column (name= "anp_actu")
    private Integer anp_actu;

    @Column (name= "anp_orden")
    private Integer anp_orden;

    @Column (name= "anp_docreg", length = 250)
    private String anp_docreg;

    @Column (name= "anp_fecreg")
    private Date anp_fecreg;

    @Column (name= "met_id")
    private Integer met_id;

    @Transient
    private String c_nomb;

    @Transient
    private int id;
    @Transient
    private String name;
    @Transient
    private String code;

    public int getId() {
        return this.anp_id;
    }
    public void setId(int id) {
        this.id = this.anp_id;
    }

    public String getName() {
        return this.anp_nomb;
    }
    public void setName(String name) {
        this.name = this.anp_nomb;
    }

    public String getCode() {
        return this.anp_codi;
    }
    public void setCode(String code) {
        this.code = this.anp_codi;
    }
}
