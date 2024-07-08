package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table (name = "t_criterio_respuesta")
public class CriterionResponse extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_criterio_respuesta_srl_id_seq", sequenceName="t_criterio_respuesta_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_criterio_respuesta_tramite"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProcedureMPV procedure;

    @ManyToOne
    @JoinColumn(name = "int_id_criterio", nullable=false, foreignKey = @ForeignKey(name="fk_criterio_respuesta_criterio"))
    private Criterion criterion;

    @Column(name = "var_comentario")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "int_id_resultado", nullable=true, foreignKey = @ForeignKey(name="fk_criterio_respuesta_resultado"))
    private ListDetail result;

    @Column(name = "bol_aplicar")
    private Boolean apply;

}
