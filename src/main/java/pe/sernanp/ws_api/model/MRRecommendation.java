package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pe.sernanp.ws_api.dto.ListDetailEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "t_acta_seguim_recomendacion")
public class MRRecommendation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_seguim_recomendacion_srl_id_seq", sequenceName="t_acta_seguim_recomendacion_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_seguimiento", foreignKey = @ForeignKey(name="fk_recomendacion_acta_seguimie"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MonitoringRecord monitoringRecord;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_seguim_cumplimiento", foreignKey = @ForeignKey(name="fk_recomendacion_cumplimiento"))
    private MRCompliance compliance;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_seguim_tipo_cumplimiento", foreignKey = @ForeignKey(name="fk_recomendacion_tipo_cumplimiento"))
    private ListDetail complianceType;

    @Column (name= "txt_recomendacion", length = 1000)
    private String recommendation;
    @Column (name= "txt_comentario", length = 1000)
    private String comments;
    @Column (name= "dte_fec_acordada", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate agreedDate;

    @Column (name= "txt_nom_documento_evaluacion", length = 100)
    private String evaluateDocumentName;
    @Column (name= "txt_cod_documento_evaluacion", length = 50)
    private String evaluateDocumentCode;
    @Column (name= "txt_size_documento_evaluacion")
    private Long evaluateDocumentSize;
}
