package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "t_acta_seguim_docu_requerida")
public class MRDocumentationRequested extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_seguim_docu_requerida_srl_id_seq", sequenceName="t_acta_seguim_docu_requerida_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_seguimiento", foreignKey = @ForeignKey(name="fk_seguimi_docu_requerida_acta_seguimi"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MonitoringRecord monitoringRecord;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_seguim_cumplimiento", foreignKey = @ForeignKey(name="fk_seguimi_docu_requerida_cumplimiento"))
    private MRCompliance compliance;

    @Column (name= "txt_documento_relacionado", columnDefinition = "TEXT")
    private String descriptionDocument;

    @Column (name= "txt_comentario", columnDefinition = "TEXT")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "int_id_estado", foreignKey = @ForeignKey(name="fk_seguimi_docu_requerida_estado"))
    private ListDetail state;

    @Column (name= "dte_fec_acordada", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate agreedDate;

    @Column (name= "txt_nom_documento", length = 100)
    private String documentName;
    @Column (name= "txt_cod_documento", length = 50)
    private String documentCode;
    @Column (name= "txt_size_documento")
    private Long documentSize;
}
