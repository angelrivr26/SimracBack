package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table (name = "t_od_compromiso")
public class Compromise extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_od_compromiso_srl_id_seq", sequenceName="t_od_compromiso_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_od", nullable=false, foreignKey = @ForeignKey(name="FK_compromiso_od"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OD od;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_compromiso_monetario", foreignKey = @ForeignKey(name="FK_compr_tipo_compr_monetario"))
    private ListDetail compromiseMonetaryType;

    @ManyToOne
    @JoinColumn(name = "int_id_periodicidad", nullable = false, foreignKey = @ForeignKey(name="FK_compromiso_periodicidad"))
    private ListDetail periodicity;

    @ManyToOne
    @JoinColumn(name = "int_id_forma_pago", foreignKey = @ForeignKey(name="FK_compromiso_forma_pago"))
    private ListDetail paymentMethod;

    @ManyToOne
    @JoinColumn(name = "int_id_recurso", foreignKey = @ForeignKey(name="FK_compromiso_anp_recurso"))
    private ListDetail resource;

    @ManyToOne
    @JoinColumn(name = "int_id_moneda", foreignKey = @ForeignKey(name="FK_compromiso_anp_moneda"))
    private ListDetail currency;

    @Column (name= "num_importe", columnDefinition = "numeric (12,2)")
    private Double amount;

}
