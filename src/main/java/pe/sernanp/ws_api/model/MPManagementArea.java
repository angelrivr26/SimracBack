package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_pm_area_manejo")
public class MPManagementArea extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_pm_area_manejo_srl_id_seq", sequenceName="t_pm_area_manejo_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_plan_manejo", foreignKey = @ForeignKey(name="fk_area_plan_manejo"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ManagementPlan managementPlan;

    @ManyToOne
    @JoinColumn(name = "int_id_modalidad", foreignKey = @ForeignKey(name="fk_area_manejo_modalidad"))
    private Modality modality;

//    @Column (name= "txt_cod_anp", length = 70)
//    private String anpCode;
//
//    @Column (name= "txt_nom_anp", length = 250)
//    private String anpName;

    @Column (name= "txt_codigo", length = 50)
    private String code;

    @Column (name= "txt_nombre", length = 500)
    private String name;

    @Column (name= "num_herctareas", columnDefinition = "numeric (12,2)")
    private double hectares;

}
