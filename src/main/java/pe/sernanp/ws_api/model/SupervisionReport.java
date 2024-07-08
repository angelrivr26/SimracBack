package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_informe_supervision")
public class SupervisionReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_informe_supervision_srl_id_seq", sequenceName="t_informe_supervision_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_acta_supervision", foreignKey = @ForeignKey(name="fk_informe_sup_acta_supervisio"))
    private SupervisionRecord supervisionRecord;

//    @ManyToOne
//    @JoinColumn(name = "int_id_matriz_obligacion", foreignKey = @ForeignKey(name="fk_informe_sup_matriz_obligaci"))
    @JsonIgnore
    @Column (name= "int_id_matriz_obligacion")
    private Integer matrizObligationId;

    @Column (name= "txt_num_informe", length = 150)
    private String reportNumber;

    @Column (name= "txt_num_expediente", length = 150)
    private String recordCode;

    @Column (name= "int_obligacion_total")
    private int obligationsTotal;

    @Column (name= "int_obligacion_cumplida")
    private int obligationsFulfilled;

    @Column (name= "txt_ruta_documento", length = 1000)
    private String documentReportId;

    @Column (name= "txt_nom_documento", length = 1000)
    private String documentReportName;

    @Column (name= "var_url_documento", length = 4000)
    private String documentUrl;
}
