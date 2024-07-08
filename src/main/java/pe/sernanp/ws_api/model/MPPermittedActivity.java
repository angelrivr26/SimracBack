package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_pm_actividad_permitida")
public class MPPermittedActivity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_pm_actividad_permitida_srl_id_seq", sequenceName="t_pm_actividad_permitida_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_plan_manejo", foreignKey = @ForeignKey(name="fk_pm_actividad_plan_manejo"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ManagementPlan managementPlan;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_actividad", nullable = false, foreignKey = @ForeignKey(name="fk_pm_actividad_tipo_actividad"))
    private ListDetail activityType;

    @ManyToOne
    @JoinColumn(name = "int_id_actividad", nullable = false, foreignKey = @ForeignKey(name="fk_pm_actividad_actividad"))
    private ListDetail activity;

    @Column (name= "txt_descripcion", length = 500)
    private String description;
}
