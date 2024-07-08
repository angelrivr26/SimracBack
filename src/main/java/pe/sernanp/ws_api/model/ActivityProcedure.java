package pe.sernanp.ws_api.model;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table (name = "t_tramite_actividad")
public class ActivityProcedure extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_actividad_tramite_srl_id_seq", sequenceName="t_actividad_tramite_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_actividad_tramite_tramite"))
    private ProcedureMPV procedure;

    @ManyToOne
    @JoinColumn(name = "int_actividad", nullable=false, foreignKey = @ForeignKey(name="fk_actividad_tramite_actividad"))
    private ListDetail activity;

    @ManyToOne
    @JoinColumn(name = "int_sector", nullable=false, foreignKey = @ForeignKey(name="fk_actividad_tramite_sector"))
    private Sector sector;

}
