package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_acta_super_info_adicional")
public class SRAdditionalInformation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_super_info_adicional_srl_id_seq", sequenceName="t_acta_super_info_adicional_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_supervision", foreignKey = @ForeignKey(name="fk_info_adicion_acta_supervisi"))
    private SupervisionRecord supervisionRecord;

    @Column (name= "txt_descripcion", columnDefinition = "TEXT")
    private String description;

    @Column (name= "txt_administrado_observacion", columnDefinition = "TEXT")
    private String administeredObservation;

    @Column (name= "txt_supervisor_observacion", columnDefinition = "TEXT")
    private String supervisorObservation;

    @Column (name= "bol_acta_suscrito")
    private Boolean flgRefused;
}
