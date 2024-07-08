package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_form_pago")
public class PayFormMPV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_form_pago_srl_id_seq", sequenceName="t_form_pago_srl_id_seq", allocationSize=1)
    private int srl_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_form_pago_tramite"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProcedureMPV procedure;

    @Column (name= "int_forma_pago", length = 10)
    private String int_forma_pago;

    @Column (name= "num_comprobante", length = 20)
    private String num_comprobante;

    @Column (name= "dte_comprobante", length = 20)
    private String dte_comprobante;

    @Column (name= "int_importe", columnDefinition = "numeric(12,4)")
    private Double int_importe;
}
