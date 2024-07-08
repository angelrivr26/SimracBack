package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table (name = "t_sector",
        uniqueConstraints = { @UniqueConstraint(name = "UQ_sector", columnNames = {"var_cod_anp", "var_codigo", "int_id_sector"})})
public class Sector extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_sector_srl_id_seq", sequenceName="t_sector_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_sector", foreignKey = @ForeignKey(name="fk_sector_sector"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Sector sector;

    @Column (name= "var_cod_anp", nullable=false, length = 250)
    private String anpCode;

    @Column (name= "var_codigo", nullable=false, length = 250)
    private String code;

    @Column (name= "var_nom", nullable=false, length = 250)
    private String name;

    @Column (name= "num_area", nullable = false, columnDefinition = "numeric (22,15)")
    private Double area;

}
