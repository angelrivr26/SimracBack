package pe.sernanp.ws_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_acta_super_hecho_verificado_documento")
public class SRVerifiedFactDocument extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_acta_super_hecho_verificado_documento_srl_id_seq", sequenceName="t_acta_super_hecho_verificado_documento_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_super_hecho_verificado", foreignKey = @ForeignKey(name="fk_hecho_verificado_documento_hecho_verificado"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SRVerifiedFact verifiedFact;

    @Column (name= "txt_nom_documento", length = 150)
    private String DocumentName;
    @Column (name= "txt_size_documento")
    private Long DocumentSize;
    @Column (name= "txt_code_documento", length = 150)
    private String DocumentCode;
}
