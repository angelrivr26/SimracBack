package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table (name = "t_poligono",
        uniqueConstraints = { @UniqueConstraint(name = "UQ_poligono", columnNames = {"var_codigo", "int_id_sector"})})
public class Polygon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_poligono_srl_id_seq", sequenceName="t_poligono_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_sector", foreignKey = @ForeignKey(name="fk_poligono_sector"))
    private Sector sector;

    @Column (name= "var_codigo", nullable=false, length = 150)
    private String code;

    @Column (name= "var_nom", nullable=false, length = 250)
    private String name;

    @Column (name= "var_ruta", length = 250)
    private String route;

    @Column (name= "var_area", length = 500)
    private String area;

    @Column (name= "num_hectareas", nullable = false, columnDefinition = "numeric (12,4)")
    private Double hectares;
}
