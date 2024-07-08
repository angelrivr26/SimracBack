package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_od_actividad")
public class ODActivity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_od_actividad_srl_id_seq", sequenceName="t_od_actividad_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_od", nullable=false, foreignKey = @ForeignKey(name="FK_od_actividad_od"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OD od;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_actividad", nullable=false, foreignKey = @ForeignKey(name="FK_od_actividad_tipo_actividad"))
    private ListDetail activityType;

    @ManyToOne
    @JoinColumn(name = "int_id_actividad_config", foreignKey = @ForeignKey(name="FK_od_activid_actividad"))
    //private ActivityAnpConfig activity;
    private ListDetail activity;

    @Column (name= "var_observation", length = 250)
    private String observation;

}
