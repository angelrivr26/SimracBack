package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "t_tramite_estado")
public class ProcedureState extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_tramite_estado_srl_id_seq", sequenceName="t_tramite_estado_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_tramite_estado_tramite"))
    private ProcedureMPV procedure;

    @Column(name= "dte_fecha")
    private Date date;

    @ManyToOne
    @JoinColumn (name= "int_id_estado", nullable=false, foreignKey = @ForeignKey(name="fk_tramite_estado_estado"))
    private ListDetail state;

    @Column(name = "bol_activo")
    private Boolean active;

}
