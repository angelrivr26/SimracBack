package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table (name = "t_criterio")
public class Criterion extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_criterio_srl_id_seq", sequenceName="t_criterio_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo", nullable=false, foreignKey = @ForeignKey(name="fk_tipo"))
    private ListDetail type;

    @Column(name = "var_nombre_tipo")
    private String nameType;

    @ManyToOne
    @JoinColumn(name = "int_id_modalidad", nullable=false, foreignKey = @ForeignKey(name="fk_modalidad"))
    private ListDetail modality;

    @Column(name = "var_nombre_modalidad")
    private String nameModality;

    @Column(name = "var_code")
    private String code;

    @Column(name = "int_componente")
    private Integer component;

    @Column(name = "var_componente")
    private String nameComponent;

    @Column(name = "var_numero_criterio")
    private String numberCriterion;

    @Column(name = "var_nombre")
    private String name;

    @Column(name = "var_numero_parametro")
    private String numberParameter;

    @Column(name = "var_nombre_parametro")
    private String nameParameter;

}
