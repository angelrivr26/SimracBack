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
@Table(name = "t_acta_super_docu_requerida")
public class SRDocumentationRequested extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_super_docu_requerida_srl_id_seq", sequenceName="t_acta_super_docu_requerida_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_supervision", foreignKey = @ForeignKey(name="fk_docu_requerida_acta_supervi"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SupervisionRecord supervisionRecord;

    @ManyToOne
    @JoinColumn(name = "int_id_hecho_verificado", foreignKey = @ForeignKey(name="fk_docu_requerid_hecho_verific"))
    private SRVerifiedFact verifiedFact;

    @ManyToOne
    @JoinColumn(name = "int_id_medio_entrega", foreignKey = @ForeignKey(name="fk_docu_requerid_medio_entrega"))
    private ListDetail deliveryMeans;

    @Column (name= "bol_informacion_adicional")
    private Boolean flgAdditionalInformation;

    @Column (name= "txt_descripcion", length = 1000)
    private String description;

    @Column (name= "dte_fec_entrega", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate deliveryDate;
}
