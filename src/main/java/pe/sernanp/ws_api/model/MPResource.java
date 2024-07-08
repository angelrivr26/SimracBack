package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_pm_recurso")
public class MPResource extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_pm_recurso_srl_id_seq", sequenceName="t_pm_recurso_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_plan_manejo", foreignKey = @ForeignKey(name="fk_recurso_plan_manejo"))
    private ManagementPlan managementPlan;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_recurso", foreignKey = @ForeignKey(name="fk_pmrecurso_tipo_recurso"))
    private ListDetail resourceType;

    @ManyToOne
    @JoinColumn(name = "int_id_recurso", nullable=false, foreignKey = @ForeignKey(name="fk_pmrecurso_recurso"))
    private ListDetail resource;

    @ManyToOne
    @JoinColumn(name = "int_id_producto", nullable=false, foreignKey = @ForeignKey(name="fk_pmrecurso_producto"))
    private ListDetail product;

    @ManyToOne
    @JoinColumn(name = "int_id_unidad_medida", nullable=false, foreignKey = @ForeignKey(name="fk_pmrecurso_unidad_medida"))
    private ListDetail measurementUnit;

    @Column (name= "num_cuota", columnDefinition = "numeric (10,0)")
    private Integer quota = 0;

    @Column (name= "num_temporada_aprovechamiento", columnDefinition = "numeric (5,0)")
    private Integer exploitationSeason = 0;
}
