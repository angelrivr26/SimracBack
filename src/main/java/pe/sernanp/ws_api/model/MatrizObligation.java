package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "t_matriz_obligacion", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_matriz_obligacion", columnNames = {"int_id_tipo",
                "int_id_od", "var_periodo", "txt_num_expediente"})})
public class MatrizObligation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_matriz_obligacion_srl_id_seq", sequenceName="t_matriz_obligacion_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo", foreignKey = @ForeignKey(name="fk_matriz_obligacion_tipo"))
    private ListDetail type;

    @ManyToOne
    @JoinColumn(name = "int_id_od", foreignKey = @ForeignKey(name="fk_matriz_obligacion_od"))
    private OD od;

    @Column (name= "var_periodo", length = 100)
    private String period;

    @Column (name= "txt_num_expediente", length = 250)
    private String fileNumber;

    @Transient
    private FiscalObligation fiscalObligation;

    @Transient
    private ListDetail resourceType;
}
