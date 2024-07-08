package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_ps_actividad_permitida")
public class SPPermittedActivity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_ps_actividad_permitida_srl_id_seq", sequenceName="t_ps_actividad_permitida_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_plan_sitio", foreignKey = @ForeignKey(name="fk_ps_actividad_plan_sitio"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SitePlan sitePlan;

    @ManyToOne
    @JoinColumn(name = "int_id_poligono_otorgamiento", foreignKey = @ForeignKey(name="fk_ps_activi_poligono_otorgami"))
    private SPGrantingPolygon grantingPolygon;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_actividad", nullable = false, foreignKey = @ForeignKey(name="fk_ps_actividad_tipo_actividad"))
    private ListDetail activityType;

    @ManyToOne
    @JoinColumn(name = "int_id_actividad", nullable = false, foreignKey = @ForeignKey(name="fk_ps_actividad_actividad"))
    private ListDetail activity;

    @ManyToOne
    @JoinColumn(name = "int_id_modalidad", foreignKey = @ForeignKey(name="fk_ps_actividad_modalidad"))
    private Modality modality;

    @Column (name= "txt_descripcion", length = 1000)
    private String description;

    @Transient
    private String grantingPolygonIds;

    public SPPermittedActivity(SPPermittedActivity temp) {
        this.setSitePlan(temp.getSitePlan());
        this.setGrantingPolygon(temp.getGrantingPolygon());
        this.setActivityType(temp.getActivityType());
        this.setActivity(temp.getActivity());
        this.setModality(temp.getModality());
        this.setDescription(temp.getDescription());
        this.setGrantingPolygonIds(temp.getGrantingPolygonIds());
    }
    public SPPermittedActivity(){}
}
