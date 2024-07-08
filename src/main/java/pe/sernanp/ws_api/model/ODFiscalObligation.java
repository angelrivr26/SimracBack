package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_od_obligacion_fiscal")
public class ODFiscalObligation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_od_obligacion_fiscal_srl_id_seq", sequenceName="t_od_obligacion_fiscal_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_od", foreignKey = @ForeignKey(name="fk_od_obligacion_fizcal_od"))
    private OD od;

    @ManyToOne
    @JoinColumn(name = "int_id_obligacion_fiscal", foreignKey = @ForeignKey(name="fk_od_obliga_obligacion_fiscal"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FiscalObligation fiscalObligation;

    @ManyToOne
    @JoinColumn(name = "int_id_norma_anp_config", foreignKey = @ForeignKey(name="fk_od_obligac_norma_anp_config"))
    private NormAnpConfig normAnpConfig;
}
