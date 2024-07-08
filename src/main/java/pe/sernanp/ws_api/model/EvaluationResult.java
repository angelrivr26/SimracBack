package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_evaluacion_resultado")
public class EvaluationResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_evaluacion_resultado_srl_id_seq", sequenceName="t_evaluacion_resultado_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_evaluacion_resultado_tramite"))
    private ProcedureMPV procedure;

    @Column(name = "var_descripcion", columnDefinition = "text")
    private String description;

    @Column(name = "bol_flg")
    private Boolean hasData;

}
