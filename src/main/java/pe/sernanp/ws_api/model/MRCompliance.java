package pe.sernanp.ws_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_acta_seguim_cumplimiento")
public class MRCompliance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_seguim_cumplimiento_srl_id_seq", sequenceName="t_acta_seguim_cumplimiento_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_seguimiento", foreignKey = @ForeignKey(name="fk_acta_seguim_cumplimiento_acta_seguimiento"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MonitoringRecord monitoringRecord;

    @ManyToOne
    @JoinColumn(name = "int_id_obligacion_fiscal", foreignKey = @ForeignKey(name="fk_acta_seguim_cumplimiento_obligacion_fiscal"))
    private ODFiscalObligation odFiscalObligation;

    @Column (name= "bol_cumplimiento_seleccionado")
    private boolean complianceSelected;

    @ManyToOne
    @JoinColumn (name= "int_id_tipo_cumplimiento", foreignKey = @ForeignKey(name="fk_acta_seguim_cumplimiento_tipo_cumplimiento"))
    private ListDetail complianceType;

    @ManyToOne
    @JoinColumn (name= "int_id_tipo_etapa", foreignKey = @ForeignKey(name="fk_acta_seguim_cumplimiento_tipo_etapa"))
    private ListDetail stageType ;
    @Column (name= "txt_comentario", columnDefinition = "TEXT")
    private String comments;

    @Column (name= "txt_actividad", columnDefinition = "TEXT")
    private String activity;
    @Column (name= "txt_descripcion", columnDefinition = "TEXT")
    private String description;
}
