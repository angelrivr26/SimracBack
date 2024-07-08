package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table (name = "t_obligacion_fiscal")
public class FiscalObligation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_obligacion_fiscal_srl_id_seq", sequenceName="t_obligacion_fiscal_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_plan_sitio", foreignKey = @ForeignKey(name="fk_obligacion_fiscal_plan_siti"))
    private SitePlan sitePlan;

    @ManyToOne
    @JoinColumn(name = "int_id_plan_manejo", foreignKey = @ForeignKey(name="fk_obligacion_fiscal_plan_mane"))
    private ManagementPlan managementPlan;

    @ManyToOne
    @JoinColumn(name = "int_id_od", foreignKey = @ForeignKey(name="fk_obligacion_fiscal_otorgami"))
    private OD od;

    @ManyToOne
    @JoinColumn(name = "int_id_fuente", foreignKey = @ForeignKey(name="fk_obligacion_fiscal_fuente"))
    private ListDetail source;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_compromiso_no_monetario", foreignKey = @ForeignKey(name="fk_oblig_fisc_compr_no_monetar"))
    private ListDetail compromiseNoMonetaryType;

    @ManyToOne
    @JoinColumn(name = "int_id_responsable", foreignKey = @ForeignKey(name="fk_obligacion_fisc_responsable"))
    private ListDetail responsible;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_norma", foreignKey = @ForeignKey(name="fk_obligacion_fisca_tipo_norma"))
    private ListDetail normType;

    @ManyToOne
    @JoinColumn(name = "int_id_publico", foreignKey = @ForeignKey(name="fk_obligacion_fiscal_publico"))
    private ListDetail audience;

    @Column (name= "txt_descripcion", columnDefinition = "TEXT")
    private String description;

    @Column (name= "txt_caracteristica", columnDefinition = "TEXT")
    private String characteristic;

    @Column (name= "num_plazo_ejecucion", columnDefinition = "numeric (5,0)")
    private int executionTerm;
}
