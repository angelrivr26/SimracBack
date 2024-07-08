package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_ps_sector_turistico", uniqueConstraints = {
        @UniqueConstraint(name = "uq_ps_sector_turistico", columnNames = {"int_id_plan_sitio", "int_id_sector_area"})})
public class SPTouristSector extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_ps_sector_turistico_srl_id_seq", sequenceName="t_ps_sector_turistico_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_plan_sitio", foreignKey = @ForeignKey(name="fk_ps_sector_turist_plan_sitio"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SitePlan sitePlan;

    @ManyToOne
    @JoinColumn(name = "int_id_sector_area", foreignKey = @ForeignKey(name="fk_ps_sector_turis_sector_area"))
    private Sector sectorArea;

//    @ManyToOne
//    @JoinColumn(name = "int_id_modalidad", foreignKey = @ForeignKey(name="FK_sector_turistico_modalidad"))
//    private Modality modality;

//    @Column (name= "txt_codigo", nullable = false, length = 70)
    @Transient
    private String code;

//    @Column (name= "txt_nombre", nullable = false, length = 150)
//    private String name;
//
//    @Column (name= "num_herctareas", columnDefinition = "numeric (12,2)")
//    private Double hectares;

}
