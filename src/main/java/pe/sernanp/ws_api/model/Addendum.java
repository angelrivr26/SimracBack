package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
@Table (name = "t_od_adenda")
public class Addendum extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_od_adenda_srl_id_seq", sequenceName="t_od_adenda_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_od", nullable=false, foreignKey = @ForeignKey(name="FK_adenda_od"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OD od;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_adenda", foreignKey = @ForeignKey(name="fk_adenda_tipo_adenda"))
    private ListDetail addendumType;

    @Column (name= "int_num_orden", nullable=false)
    private int order;

    @Column (name= "txt_nom_adenda", nullable=false, length = 250)
    private String name;

    @Column (name= "txt_des_adenda", columnDefinition = "TEXT")
    private String description;

    @Column (name= "dte_fec_emision", nullable = false, columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate emissionDate;

    @Column (name= "dte_fec_fin", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @Column (name= "dte_fec_suscripcion", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate subscriptionDate;

    @Column (name= "txt_duration", length = 250)
    private String duration;

    @Column (name= "bol_flg_firmado", columnDefinition = "boolean default false")
    private Boolean flagSignature = false;

    @Column (name= "txt_nom_documento", length = 150)
    private String documentName;

    @Column (name= "txt_documento_ruta", length = 100)
    private String documentId;
}
