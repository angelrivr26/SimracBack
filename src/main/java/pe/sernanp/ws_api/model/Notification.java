package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "t_notificacion")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_notificacion_srl_id_seq", sequenceName="t_notificacion_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_notificacion_tramite"))
    private ProcedureMPV procedure;

    @Column(name= "dte_fecha")
    private Date date;

    @Column (name= "var_notificacion")
    private String notificacion; //

    @Column(name = "var_comentario")
    private String comentario;

    @Column (name= "var_nom_documento", length = 500)
    private String documentName;

    @Column (name= "var_documento_ruta", length = 1000)
    private String documentRouteId;

}
