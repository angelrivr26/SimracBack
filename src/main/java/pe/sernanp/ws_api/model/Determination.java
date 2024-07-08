package pe.sernanp.ws_api.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table (name = "t_determinacion")
public class Determination extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "t_determinacion_srl_id_seq", sequenceName = "t_determinacion_srl_id_seq", allocationSize = 1)
    @Column(name = "srl_id", unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo", foreignKey = @ForeignKey(name = "fk_determinacion_tipo"))
    private ListDetail type;

    @ManyToOne
    @JoinColumn(name = "int_id_modalidad", foreignKey = @ForeignKey(name = "fk_determinacion_modalidad"))
    private Modality modality;

    @Column(name = "int_anio_otorgado")
    private Integer yearGranted;

    @Column(name = "int_componente")
    private Integer component;

    @Column(name = "var_componente")
    private String componentName;

    @Column(name = "var_numero_criterio")
    private String numberCriterion;

    @Column(name = "var_criterio")
    private String criterion;

    @Column(name = "var_numero_parametro")
    private String numberParameter;

    @Column(name = "var_nombre_parametro")
    private String parameterName;

    @Column(name = "var_peso")
    private String weight;

    @Column(name = "var_atributo1")
    private String attribute1;

    @Column(name = "var_atributo2")
    private String attribute2;

    @Column(name = "var_atributo3")
    private String attribute3;

    @Column(name = "num_valor1", precision = 38, scale = 8)
    private Double value1;

    @Column(name = "num_valor2", precision = 38, scale = 8)
    private Double value2;

    @Column(name = "num_valor3", precision = 38, scale = 8)
    private Double value3;

}
