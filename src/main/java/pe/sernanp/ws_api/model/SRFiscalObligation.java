package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_is_obligacion_fiscal")
public class SRFiscalObligation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_is_obligacion_fiscal_srl_id_seq", sequenceName="t_is_obligacion_fiscal_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_informe_supervision", foreignKey = @ForeignKey(name="fk_is_obligacion_informe_sup"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SupervisionReport supervisionReport;

    @ManyToOne
    @JoinColumn(name = "int_id_incumplimiento", foreignKey = @ForeignKey(name="fk_is_obligacion_incumplimien"))
    private ListDetail breach;

    @ManyToOne
    @JoinColumn(name = "int_id_od_obligacion_fiscal", foreignKey = @ForeignKey(name="fk_informe_sup_od_obliga_fisca"))
    private ODFiscalObligation odFiscalObligation;

    @Column(name = "txt_plazo_ejecucion")
    private String executionTerm;
}
