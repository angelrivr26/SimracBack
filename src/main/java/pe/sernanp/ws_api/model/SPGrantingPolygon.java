package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_ps_poligono_otorgamiento", uniqueConstraints = {
        @UniqueConstraint(name = "uq_ps_poligono_otorgamiento", columnNames = {"int_id_plan_sitio", "int_id_sector_turistico", "int_id_poligono"})})
public class SPGrantingPolygon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_ps_poligono_otorgamiento_srl_id_seq", sequenceName="t_ps_poligono_otorgamiento_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_plan_sitio", foreignKey = @ForeignKey(name="fk_ps_poligo_otorga_plan_sitio"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SitePlan sitePlan;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_uso", nullable = false, foreignKey = @ForeignKey(name="fk_ps_poligo_otorga_tipo_uso"))
    private ListDetail useType;

    @ManyToOne
    @JoinColumn(name = "int_id_sector_turistico", foreignKey = @ForeignKey(name="fk_ps_poligo_otorga_sector_turistico"))
    private SPTouristSector spTouristSector;

    @ManyToOne
    @JoinColumn(name = "int_id_poligono", foreignKey = @ForeignKey(name="fk_ps_sector_turis_poligono"))
    private Polygon polygon;

//    @ManyToOne
//    @JoinColumn(name = "int_id_modalidad", foreignKey = @ForeignKey(name="FK_poligono_otorgami_modalidad"))
    @Column (name= "var_ids_modalidad", nullable = false, length = 50)
    private String modalityIds;

//    @Column (name= "txt_codigo", nullable = false, length = 70)
    @Transient
    private String code;

//    @Column (name= "txt_area", nullable = false, length = 100)
//    private String area;
//
//    @Column (name= "txt_ruta", length = 1000)
//    private String route;
//
//    @Column (name= "num_herctareas", columnDefinition = "numeric (12,2)")
//    private Double hectares;
}
