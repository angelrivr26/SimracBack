package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_evaluacion_economica")
public class EvaluationEconomic extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_evaluacion_economica_srl_id_seq", sequenceName="t_evaluacion_economica_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_evaluacion_economica_tramite"))
    private ProcedureMPV procedure;

    @Column(name = "var_descripcion", columnDefinition = "text")
    private String description;

    @Column(name = "bol_flg")
    private Boolean hasData;

    @Column (name= "num_monetario", columnDefinition = "numeric (10,2)")
    private Double monetary;

    @Column (name= "num_no_monetario", columnDefinition = "numeric (10,2)")
    private Double noMonetary;

    @Column (name= "var_nom_documento", length = 500)
    private String documentName;

    @Column (name= "var_id_documento_ruta", length = 1000)
    private String documentRouteId;
}
