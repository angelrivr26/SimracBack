package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_reporte_seguimiento")
public class MonitoringReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_reporte_seguimiento_srl_id_seq", sequenceName="t_reporte_seguimiento_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo", nullable=false, foreignKey = @ForeignKey(name="FK_reporte_seguimiento_tipo"))
    private ListDetail type;

    // Para turismo
    @ManyToOne
    @JoinColumn(name = "int_id_od", foreignKey = @ForeignKey(name="fk_reporte_seguimiento_od"))
    private OD od;

    // Para Recursos
    @ManyToOne
    @JoinColumn(name = "int_id_tipo_recurso", foreignKey = @ForeignKey(name="FK_reporte_segui_tipo_recurso"))
    private ListDetail resourceType;

    @ManyToOne
    @JoinColumn(name = "int_id_recurso", foreignKey = @ForeignKey(name="FK_reporte_seguimiento_recurso"))
    private ListDetail resource;

    @Column (name= "var_ids_acta_seguimiento", length = 1000)
    private String monitoringRecordIds;

    @Column (name= "txt_num_informe", length = 250)
    private String reportNumber;

    @Column (name= "txt_recomendaciones", columnDefinition = "TEXT")
    private String recommendations;

    @Column (name= "txt_recomendaciones_adicionales", columnDefinition = "TEXT")
    private String additionalRecommendations;

    @Column (name= "txt_conclusiones", columnDefinition = "TEXT")
    private String conclusions;

    @Column (name= "int_obligacion_total")
    private int obligationsTotal;

    @Column (name= "int_obligacion_cumplida")
    private int obligationsFulfilled;

    @Column (name= "txt_ruta_documento", length = 1000)
    private String documentReportId;

    @Column (name= "txt_nom_documento", length = 1000)
    private String documentReportName;
}
