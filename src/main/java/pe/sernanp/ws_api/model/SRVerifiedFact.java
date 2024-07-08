package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "t_acta_super_hecho_verificado")
public class SRVerifiedFact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_super_hecho_verificado_srl_id_seq", sequenceName="t_acta_super_hecho_verificado_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_supervision", foreignKey = @ForeignKey(name="fk_hecho_veridico_acta_supervi"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SupervisionRecord supervisionRecord;

    @ManyToOne
    @JoinColumn(name = "int_id_incumplimiento", foreignKey = @ForeignKey(name="fk_acta_seguimi_incumplimiento"))
    private ListDetail breach;

    @ManyToOne
    @JoinColumn(name = "int_id_od_obligacion_fiscal", foreignKey = @ForeignKey(name="fk_hecho_verific_od_obligacion"))
    private ODFiscalObligation odFiscalObligation;

    @Column (name= "txt_subsanacion_voluntaria", length = 250)
    private String voluntaryCorrection;

    @Column (name= "txt_medida_administrativa", length = 250)
    private String administrativeMeasure;

//    @Column (name= "txt_descripcion", length = 250)
//    @Transient
//    private String description;

    @Transient
    private List<SRVerifiedFactDocument> documents;
    public List<SRVerifiedFactDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<SRVerifiedFactDocument> documents) {
        this.documents = documents;
    }
}
