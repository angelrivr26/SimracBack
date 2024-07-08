package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_acta_super_participante")
public class SRCompetitor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_super_participante_srl_id_seq", sequenceName="t_acta_super_participante_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_supervision", foreignKey = @ForeignKey(name="fk_participante_acta_supervi"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SupervisionRecord supervisionRecord;

    @Column (name= "int_num_participante", length = 250)
    private String compiterNumber;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_participante", foreignKey = @ForeignKey(name="FK_participante_tipo_participante"))
    private ListDetail compiterType;

    @Column (name= "flg_personal")
    private boolean flagStaff;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_documento", foreignKey = @ForeignKey(name="FK_participante_tipo_documento"))
    private ListDetail documentType;

    @Column (name= "txt_num_documento", length = 20)
    private String documentNumber;

    @Column (name= "txt_nombre_completo", length = 250)
    private String fullName;
}
