package pe.sernanp.ws_api.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table (name = "t_tramite_asignacion")
public class ProcedureAssign extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_tramite_asignacion_srl_id_seq", sequenceName="t_tramite_asignacion_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_tramite_asignacion_tramite"))
    private ProcedureMPV procedure;

    @Column(name = "var_nombre")
    private String responsible;

    @Column(name = "var_responsable")
    private String nameResponsible;

    @Column(name = "bol_completado")
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "int_id_rol_evaluacion", nullable=false, foreignKey = @ForeignKey(name="fk_tramite_asignacion_rol"))
    private ListDetail roleEvaluation;
}
