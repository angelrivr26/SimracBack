package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "t_acta_seguimiento")
public class MonitoringRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_seguimiento_srl_id_seq", sequenceName="t_acta_seguimiento_srl_id_seq", allocationSize=1)
    private int id;

    @Column (name= "txt_num_acta", nullable=false, length = 150)
    private String supervisionRecordCode;

    /*@ManyToOne
    @JoinColumn(name = "int_id_tipo", foreignKey = @ForeignKey(name="fk_acta_seguimiento_tipo"))
    private ListDetail type;*/

    @ManyToOne
    @JoinColumn(name = "int_id_od", nullable=false, foreignKey = @ForeignKey(name="fk_acta_seguimiento_od"))
    private OD od;

    @Column (name= "txt_cod_anp", nullable=false)
    private String anpCode;

    @Column (name= "txt_nom_anp", length = 250)
    private String anpName;

    @Column (name= "txt_comentario", columnDefinition = "TEXT")
    private String comments;

    @Column (name= "txt_num_documento", nullable=false)
    private String specialDocumentNumber;

    @Column (name= "txt_nom_especialista", nullable=false)
    private String specialName ;

    @Column (name= "txt_cargo_especialista")
    private String specialPosition;

    @Column (name= "dte_apertura", nullable=false, columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate openingDate;

    @Column (name= "txt_hora_apertura")
    private String openingTime;

    @Column (name= "dte_cierre", nullable=false, columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate closingDate;

    @Column (name= "txt_hora_cierre")
    private String closingTime;

    @Column (name= "txt_nom_archivo_digital")
    private String digitalFileName;
    @Column (name= "txt_cod_archivo_digital")
    private String digitalFileCode;
    @Column (name= "txt_tamaño_archivo_digital")
    private Long digitalFileSize;
    @Column (name= "txt_descripcion", columnDefinition = "TEXT")
    private String description;

    @Column (name= "txt_administrado_observacion", columnDefinition = "TEXT")
    private String administeredObservation;

    @Column (name= "txt_supervisor_observacion", columnDefinition = "TEXT")
    private String supervisorObservation;

    @Column (name= "bol_acta_suscrito")
    private Boolean flgRefused;

    @ManyToOne
    @JoinColumn(name = "int_id_estado", foreignKey = @ForeignKey(name="fk_acta_seguimien_estado"))
    private ListDetail state;

    @Transient
    private int type;
//    @Column (name= "txt_cargo_especial", length = 150)
//    private String specialCharge;

    //@Column (name= "dte_fec_acta", columnDefinition = "DATE")
    //@JsonSerialize(using = LocalDateSerializer.class)
    //@JsonDeserialize(using = LocalDateDeserializer.class)
    //private LocalDate monitoringDate;

}
