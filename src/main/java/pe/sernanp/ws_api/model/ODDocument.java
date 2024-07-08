package pe.sernanp.ws_api.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table (name = "t_od_documento")
public class ODDocument extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_od_documento_srl_id_seq", sequenceName="t_od_documento_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_od", nullable=false, foreignKey = @ForeignKey(name="FK_od_documento_od"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OD od;

    @ManyToOne
    @JoinColumn(name = "int_id_modalidad_etapa", nullable=false, foreignKey = @ForeignKey(name="FK_od_document_modalidad_etapa"))
    private ModalityStage modalityStage;

    @Column (name= "var_cod_cut", nullable=false, length = 10)
    private String cut;

//    @Column (name= "int_id_tipo_documento", nullable=false)
//    private int documentType;

    @Column (name= "var_num_documento", nullable=false, length = 10)
    private String documentName;

    @Column (name= "dte_fec_documento", nullable=false)
    private Date documentDate;

    @Column (name= "dte_fec_recepcion", nullable=false)
    private Date receptionDate;

    @Column (name= "dte_fec_derivacion")
    private Date derivationDate;

    @Column (name= "txt_nom_derivacion", length = 50)
    private String forwardsName;

    @Column (name= "txt_nom_recibe", length = 150)
    private String receivesName;

    @Column (name= "num_dia_atencion")
    private int attentionDay;
}
