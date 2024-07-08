package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table (name = "t_determinacion_respuesta")
public class DeterminationResponse extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_determinacion_respuesta_srl_id_seq", sequenceName="t_determinacion_respuesta_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_determinacion_respuesta_tramite"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProcedureMPV procedure;

    @ManyToOne
    @JoinColumn(name = "int_id_determinacion", nullable=false, foreignKey = @ForeignKey(name="fk_determinacionrespuesta_determinacion"))
    private Determination determination;

    @Column(name = "num_value")
    private Double valueAnnual;

}
