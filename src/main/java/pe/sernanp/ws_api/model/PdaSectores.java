package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "t_pda_sector")
public class PdaSectores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_pda_sector_srl_id_seq", sequenceName="t_pda_sector_srl_id_seq", allocationSize=1)
    private int id;

    @Column (name= "int_id_anp")
    private int anpId;

    @Column(name = "var_anp_cod", columnDefinition = "text")
    private String anpCode;

    @Column(name = "var_nombre_anp", columnDefinition = "text")
    private String anpName;

    @ManyToOne
    @JoinColumn (name= "int_id_actividad", nullable=false, foreignKey = @ForeignKey(name="fk_pda_sector_actividad"))
    private ListDetail activity;

    @ManyToOne
    @JoinColumn(name = "int_id_sector", nullable=false, foreignKey = @ForeignKey(name="fk_pda_sector_sector"))
    private Sector sector;

    @Column (name= "int_anio")
    private Integer anio;

    @Column (name= "int_valor")
    private Integer value;

    @Transient
    List<PdaSectores> anios;

    @Transient
    @JsonIgnore
    Integer sectorId;
}
