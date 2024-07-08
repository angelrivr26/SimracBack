package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_variable_plantilla")
public class TemplateVariable extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_variable_plantilla_srl_id_seq", sequenceName="t_variable_plantilla_srl_id_seq", allocationSize=1)
    private int id;

    @JsonIgnore
    @Column (name= "int_id_modalidad_requisito")
    private Integer modalityRequirementId;

    @Column (name= "var_nom", nullable=false, length = 250)
    private String name;

    @Column (name= "var_codigo", nullable=false, length = 500)
    private String code;
}
