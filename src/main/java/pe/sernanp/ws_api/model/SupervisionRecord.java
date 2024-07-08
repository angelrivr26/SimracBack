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
@Table(name = "t_acta_supervision")
public class SupervisionRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_supervision_srl_id_seq", sequenceName="t_acta_supervision_srl_id_seq", allocationSize=1)
    private int id;

    @Column (name= "txt_num_expe", nullable=false, length = 150)
    private String recordCode;

    @Column (name= "txt_num_acta", nullable=false, length = 150)
    private String supervisionRecordCode;
    @ManyToOne
    @JoinColumn(name = "int_id_od", nullable=false, foreignKey = @ForeignKey(name="fk_acta_supervision_od"))
    private OD od;

    /*@ManyToOne
    @JoinColumn(name = "int_id_tipo", foreignKey = @ForeignKey(name="fk_acta_supervision_tipo"))
    private ListDetail type;*/

    @Column (name= "txt_cod_anp", nullable=false)
    private String anpCode;

    @Column (name= "txt_nom_anp", nullable=false, length = 250)
    private String anpName;

    @Column (name= "txt_lugar", length = 250)
    private String place;

    @Column (name= "bol_flg_regular", nullable=false, columnDefinition = "boolean default true")
    private Boolean flagRegular;

    @ManyToOne
    @JoinColumn(name = "int_id_circunstancia", foreignKey = @ForeignKey(name="fk_acta_supervi_circunstancia"))
    private ListDetail circumstance;

    @ManyToOne
    @JoinColumn(name = "int_id_estado", foreignKey = @ForeignKey(name="fk_acta_supervi_estado"))
    private ListDetail state;

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

    @Column (name= "txt_descripcion")
    private String description;

    @Column (name= "txt_administrado_observacion")
    private String administeredObservation;

    @Column (name= "txt_supervisor_observacion")
    private String supervisorObservation;

    @Column (name= "bol_acta_suscrito")
    private Boolean flgRefused;



}
